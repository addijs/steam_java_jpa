package org.example.projetoPobJpa.dao;

import org.example.projetoPobJpa.model.Genre;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class DAOGenre extends DAO<Genre>{
  public Genre read(Object key) throws Exception {
    String genreName = (String) key;

    try {
      TypedQuery<Genre> q = manager.createQuery("select g from Genre g where g.name = :n", Genre.class);
      q.setParameter("n", genreName);

      return q.getSingleResult();
    } catch (Exception e) {
      throw new Exception("Internal Server Error");
    }
  }

  public Genre returnIfExists(String genreName) throws Exception {
    try {
      TypedQuery<Genre> q = manager.createQuery("select g from Genre g where g.name = :n", Genre.class);
      q.setParameter("n", genreName);

      return q.getSingleResult();
    } catch (Exception e) {
      if (e instanceof NoResultException) {
        return null;
      }

      throw new Exception("Internal Server Error");
    }
  }
}
