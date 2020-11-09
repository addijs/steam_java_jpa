package org.example.projetoPobJpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;
  private double price;

  @ManyToMany(mappedBy = "games")
  private final List<User> users = new ArrayList<>();

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//  @JoinTable(
//          name = "game_genres",
//          joinColumns = @JoinColumn(name = "game_id"),
//          inverseJoinColumns = @JoinColumn(name = "genre_id")
//  )
  private final List<Genre> genres = new ArrayList<>();

  public Game() {}

  public Game(String title, double price) {
    this.title = title;
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<User> getUsers() {
    return users;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public void addUser(User user) { this.users.add(user); }

  public void addGenre(Genre genre) {
    this.genres.add(genre);
  }

  @Override
  public String toString() {
    return title;
  }
}

