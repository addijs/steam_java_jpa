package org.example.projetoPobJpa.model;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game_20182370004")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoSql(dataFormat= DataFormatType.MAPPED)
public class Game {

  @Id
  @GeneratedValue
  @Column(name = "_id")
  private String id;

  private String title;
  private double price;

  @ManyToMany()
  private final List<User> users = new ArrayList<>();

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

