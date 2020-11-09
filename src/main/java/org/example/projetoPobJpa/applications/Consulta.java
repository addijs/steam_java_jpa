package org.example.projetoPobJpa.applications;

import org.example.projetoPobJpa.facade.Facade;
import org.example.projetoPobJpa.model.Game;

import java.util.List;

public class Consulta {

  public Consulta() {
    Facade.start();

    try {
      consultarJogosComPrecoMaiorQue50();
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

  public static void main(String[] args) {
    new Consulta();
  }
}
