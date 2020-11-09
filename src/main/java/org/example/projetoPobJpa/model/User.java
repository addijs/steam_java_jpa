package org.example.projetoPobJpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private String email;
  private String password;
  private double wallet;

  @ManyToMany(
          cascade = { CascadeType.PERSIST, CascadeType.MERGE },
          fetch = FetchType.EAGER
  )
  private final List<Game> games = new ArrayList<>();

  public User() {}

  public User(String name, String email, String pass) {
    this.name = name;
    this.email = email;
    this.password = pass;
    this.wallet = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public double getWallet() {
    return wallet;
  }

  public void addWalletMoney(double value) {
    this.wallet+=value;
  }

  public void debitWalletMoney(double gamePrice) {
    this.wallet-=gamePrice;
  }

  public List<Game> getGames() {
    return games;
  }

  @Override
  public String toString() {
    return getName();
  }
}