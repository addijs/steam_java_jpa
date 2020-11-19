package org.example.projetoPobJpa.dao;

import org.example.projetoPobJpa.model.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class DAOUser extends DAO<User> {
  public User read(Object key) throws Exception {
    String email = (String) key;

    try {
      TypedQuery<User> q = manager.createQuery("select u from User u where u.email = :e", User.class);
      q.setParameter("e", email);

      return q.getSingleResult();
    } catch (Exception e) {
      throw new Exception("Internal Server Error");
    }
  }

  public User returnIfExists(String email) throws Exception {
    try {
      TypedQuery<User> q = manager.createQuery("select u from User u where u.email = :e", User.class);
      q.setParameter("e", email);

      return q.getSingleResult();
    } catch (Exception e) {
      if (e instanceof NoResultException) {
        return null;
      }

      throw new Exception("Internal Server Error");
    }
  }
}
