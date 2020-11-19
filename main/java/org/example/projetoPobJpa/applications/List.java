package org.example.projetoPobJpa.applications;

import org.example.projetoPobJpa.facade.Facade;

public class List {

  public List() {
    Facade.start();
    try {
      System.out.println(Facade.listAllGames());
      System.out.println(Facade.listAllUsers());
      System.out.println(Facade.listAllGenres());
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }

    Facade.finish();
  }

  public static void main(String[] args) {
    new List();
  }
}