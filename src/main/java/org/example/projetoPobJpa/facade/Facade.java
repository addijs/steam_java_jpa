package org.example.projetoPobJpa.facade;

import org.example.projetoPobJpa.dao.DAO;
import org.example.projetoPobJpa.dao.DAOGame;
import org.example.projetoPobJpa.dao.DAOGenre;
import org.example.projetoPobJpa.dao.DAOUser;
import org.example.projetoPobJpa.model.Game;
import org.example.projetoPobJpa.model.Genre;
import org.example.projetoPobJpa.model.User;

import javax.persistence.NoResultException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Facade {
  private static final DAOGame daoGame = new DAOGame();
  private static final DAOUser daoUser = new DAOUser();
  private static final DAOGenre daoGenre = new DAOGenre();
  private static User user_logged;

  public static void start() {
    DAO.open();
  }

  public static void finish() {
    DAO.close();
  }

  public static List<Genre> listUserGamesGenres(User user) {
    List<Game> userGames = user.getGames();
    List<Genre> genresList = new ArrayList<>();

    for(Game game: userGames) {
      for(Genre genre: game.getGenres()) {
        if(!genresList.contains(genre)) {
          genresList.add(genre);
        }
      }
    }

    return genresList;
  }

  public static User findUser(String email) throws Exception {
    User user = daoUser.read(email);

    if(user == null) {
      throw new Exception("Este usuário não existe.");
    }

    return user;
  }

  public static void registerGame(Game obj, String[] genresArray) throws Exception {
    DAO.Transaction.begin();
    String gameTitle = obj.getTitle();

    try {
      Game game = daoGame.returnIfExists(gameTitle);

      if (game != null) {
        DAO.Transaction.rollback();
        throw new Exception(String.format("This game has already been registered: %s", gameTitle));
      }

      for (String genreName: genresArray) {
        Genre storedGenre = daoGenre.returnIfExists(genreName);

        if (storedGenre == null) {
          DAO.Transaction.rollback();
          throw new Exception(
                  String.format("The genre %s does not exists. %s wasn't registered.", genreName, gameTitle)
          );
        }

        obj.addGenre(storedGenre);
      }

      daoGame.create(obj);
      DAO.Transaction.commit();
      System.out.println(String.format("The game was registered successfully. %s", gameTitle));

    } catch (Exception e) {
      System.out.println(e.getMessage());
      DAO.Transaction.rollback();
    }
  }

  public static void registerUser(String name, String email, String password) throws Exception {
    DAO.Transaction.begin();

    try {
      User user = daoUser.returnIfExists(email);

      if (user != null) {
        DAO.Transaction.rollback();
        throw new Exception("This user already exists.");
      }

      User newUser = new User(name, email, password);

      daoUser.create(newUser);
      DAO.Transaction.commit();
      System.out.println("Usuário registrado com sucesso.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      DAO.Transaction.rollback();
    }
  }

  public static void registerGenre(Genre obj) throws Exception {
    DAO.Transaction.begin();
    String genreName = obj.getName();

    try {
      Genre genre = daoGenre.returnIfExists(genreName);

      if(genre != null) {
        DAO.Transaction.rollback();
        throw new Exception(String.format("This genre already exists: %s", genreName));
      }

      daoGenre.create(obj);
      DAO.Transaction.commit();
      System.out.println("The genre was registered successfully.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      DAO.Transaction.rollback();
    }

  }

  public static void updateGame(String name) throws Exception {
    DAO.Transaction.begin();

    Game game = daoGame.read(name);

    if(game == null) {
      throw new Exception(String.format("This game does not exist"));
    }

    game.setTitle("Teste de update");
    daoGame.update(game);
    DAO.Transaction.commit();
  }

  public static void deleteGame(String name) throws Exception{
    DAO.Transaction.begin();

    Game game = daoGame.read(name);

    if(game == null) {
      throw new Exception(String.format("This game does not exist"));
    }

    daoGame.delete(game);
    DAO.Transaction.commit();
  }

  public static void updateGenre(String name) throws Exception {
    // TODO
  }

  public static User getLoggedUser() {
    return user_logged;
  }

  public static List<Game> listAllGames() {
    return daoGame.readAll();
  }

  public static List<User> listAllUsers() {
    return daoUser.readAll();
  }

  public static List<Genre> listAllGenres() {
    return daoGenre.readAll();
  }

  public static void buyGame(String gameTitle, User user) throws Exception {
    DAO.Transaction.begin();

    Game game = daoGame.returnIfExists(gameTitle);

    if(game == null) {
      throw new Exception("Esse jogo não está no nosso catálogo.");
    }

    double userWallet = user.getWallet();
    double gamePrice = game.getPrice();

    if(userWallet < gamePrice) {
      throw new Exception("Você não tem dinheiro suficiente para comprar esse jogo. :(");
    }

    user.debitWalletMoney(gamePrice);
    user.getGames().add(game);

    daoUser.update(user);

    DAO.Transaction.commit();
  }

  public static void addMoney(double value, User user) throws Exception {
    DAO.Transaction.begin();

    if(user == null) {
      throw new Exception("You need to be logged to execute this action.");
    }

    user.addWalletMoney(value);
    daoUser.update(user);
    DAO.Transaction.commit();
  }

  public static void login(String email, String password) throws Exception {
    User user = findUser(email);
    String userPassword = user.getPassword();
//		System.out.println(String.format("'%s' '%s'", password, userPassword));

    if(userPassword.compareTo(password) != 0) {
      throw new Exception("Senha incorreta. Tente novamente.");
    }

    System.out.println("\nLogando usuário...");
    user_logged = user;
  }

  public static void logout() {
    user_logged = null;
  }

  public static String formatMoney(double amount) {
    DecimalFormat df = new DecimalFormat("0.##");
    return df.format(amount);
  }

  public static List<Game> pricesAboveFifty() throws Exception {
    return daoGame.priceAboveFifty();
  }
}

