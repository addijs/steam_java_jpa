package org.example.projetoPobJpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  String name;

  @ManyToMany(mappedBy = "genres")
  private final List<Game> games = new ArrayList<>();

  public Genre() {}

  public Genre(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Game> getGames() {
    return games;
  }

  public void addGame(Game game) {
    this.games.add(game);
  }

  @Override
  public String toString() {
    return name;
  }
}
