package org.example.projetoPobJpa.applications;

import org.example.projetoPobJpa.facade.Facade;

public class Update {
  public Update() {
    Facade.start();

    try {
      Facade.updateGame("GTA V");
      System.out.println("Registro atualizado com sucesso!");

    } catch(Exception e) {
      System.out.println(e.getMessage());
    }

    Facade.finish();
  }

  public static void main(String[] args) {
    new Update();
  }
}