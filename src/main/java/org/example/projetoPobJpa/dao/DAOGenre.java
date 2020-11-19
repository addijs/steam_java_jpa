package org.example.projetoPobJpa.dao;

import org.example.projetoPobJpa.model.Genre;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

  public List<Genre> userGamesGenres(String username) throws Exception {
    try {
      TypedQuery<Genre> q = manager.createQuery(
              "select g from Genre g JOIN g.games ga JOIN ga.users u where u.name = :n",
              Genre.class
      );
      q.setParameter("n", username);

      Stream<Genre> result = q.getResultStream();
      List<Genre> genresList = new ArrayList<>();

      result.forEach(genre -> {
        if(!genresList.contains(genre)) {
          genresList.add(genre);
        }
      });

      return genresList;
    } catch (Exception e) {
      throw new Exception("Internal Server Error");
    }
  }
}
