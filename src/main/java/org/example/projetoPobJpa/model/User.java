package org.example.projetoPobJpa.model;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_20182370004")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoSql(dataFormat= DataFormatType.MAPPED)
public class User {

  @Id
  @GeneratedValue
  @Column(name = "_id")
  private String id;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime birthdate;

  @Transient
  private int age;

  private String name;
  private String email;
  private String password;
  private double wallet;

  @ManyToMany(
          cascade = { CascadeType.PERSIST, CascadeType.MERGE },
          fetch = FetchType.LAZY
  )
  private final List<Game> games = new ArrayList<>();

  public User() {}

  public User(String name, String email, String pass, LocalDateTime birthdate) {
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

  public LocalDateTime getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDateTime birthdate) {
    this.birthdate = birthdate;
  }

//  @PostLoad
//  @PrePersist
//  @PostUpdate
//  private void calculateAge() {
//    LocalDate today = LocalDate.now();
//    Period period = Period.between(birthdate, today);
//    age = period.getYears();
//  }
//
//  @Override
//  public String toString() {
//    return getName() + " age: " + getAge();
//  }
}