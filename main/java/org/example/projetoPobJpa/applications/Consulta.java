package org.example.projetoPobJpa.applications;

import org.example.projetoPobJpa.facade.Facade;
import org.example.projetoPobJpa.model.Game;
import org.example.projetoPobJpa.model.Genre;

import java.util.List;

public class Consulta {

  public Consulta() {
    Facade.start();

    try {
//      consultarJogosComPrecoMaiorQue50();

      // Consulta que envolve todas as classes
      consultarGenerosDosJogosDoUsuario();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    Facade.finish();
  }

  //Nome de jogos que tem preço acima de 50
  public void consultarJogosComPrecoMaiorQue50() throws Exception {
    List<Game> games = Facade.pricesAboveFifty();

    for(Game game: games) {
      String string = String.format("\n%s - Preço: %s", game.toString(), game.getPrice());
      System.out.println(string);
    }

  }

  //Gêneros dos jogos de um usuário
  public void consultarGenerosDosJogosDoUsuario() throws Exception {
    List<Genre> genres = Facade.listUserGamesGenres("Adilson");
    System.out.println(genres);
  }

  public static void main(String[] args) {
    new Consulta();
  }
}
