package org.example.projetoPobJpa.model;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre_20182370004")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoSql(dataFormat= DataFormatType.MAPPED)
public class Genre {

  @Id
  @GeneratedValue
  @Column(name = "_id")
  private String id;

  String name;

  @ManyToMany()
  private final List<Game> games = new ArrayList<>();

  public Genre() {}

  public Genre(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
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
