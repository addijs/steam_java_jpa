package org.example.projetoPobJpa.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_20182370004")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Transient
  private int age;

  private String name;
  private LocalDate birthdate;
  private String email;
  private String password;
  private double wallet;

  @ManyToMany(
          cascade = { CascadeType.PERSIST, CascadeType.MERGE },
          fetch = FetchType.EAGER
  )
  private final List<Game> games = new ArrayList<>();

  public User() {}

  public User(String name, String email, String pass, LocalDate birthdate) {
    this.name = name;
    this.email = email;
    this.password = pass;
    this.birthdate = birthdate;
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

  public int getAge() {
    return age;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  @PostLoad
  @PrePersist
  @PostUpdate
  private void calculateAge() {
    LocalDate today = LocalDate.now();
    Period period = Period.between(birthdate, today);
    age = period.getYears();
  }

  @Override
  public String toString() {
    return getName() + " age: " + getAge();
  }
}