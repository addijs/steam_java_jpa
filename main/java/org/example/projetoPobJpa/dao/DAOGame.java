package org.example.projetoPobJpa.dao;

import org.example.projetoPobJpa.model.Game;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class DAOGame extends DAO<Game> {

  public Game read (Object key) throws Exception {
    String title = (String) key;
    try {
      TypedQuery<Game> q = manager.createQuery("select g from Game g where g.title = :t", Game.class);
      q.setParameter("t", title);

      return q.getSingleResult();
    } catch (Exception e) {
      throw new Exception("Internal Server Error");
    }
  }

  public Game returnIfExists(String title) throws Exception {
    try {
      TypedQuery<Game> q = manager.createQuery("select g from Game g where g.title = :t", Game.class);
      q.setParameter("t", title);

      return q.getSingleResult();
    } catch (Exception e) {
      if (e instanceof NoResultException) {
        return null;
      }

      throw new Exception("Internal Server Error");
    }
  }

  public List<Game> priceAboveFifty() throws Exception {
    try {
      TypedQuery<Game> q = manager.createQuery("select g from Game g where g.price > 50", Game.class);
      return q.getResultList();
    } catch (Exception e) {
      throw new Exception("Internal Server Error");
    }
  }
}
