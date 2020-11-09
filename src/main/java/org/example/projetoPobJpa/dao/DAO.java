package org.example.projetoPobJpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;

public abstract class DAO<T> implements DAOInterface<T> {
  protected static EntityManager manager;
  protected static EntityManagerFactory factory;

  public DAO() {}

  public static void open() {
    // Removes the hibernate red lines logger!
    // java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

    factory = Persistence.createEntityManagerFactory("eclipselink-postgres");
    manager = factory.createEntityManager();
  }

  public static void close(){
    if(manager != null) {
      manager.close();
      factory.close();
    }
  }

  public void create(T obj) {
    manager.persist(obj);
  }

  public abstract T read(Object key) throws Exception;

  public T update(T obj){
    return manager.merge(obj);
  }

  public void delete(T obj) {
    manager.remove(obj);
  }


  @SuppressWarnings("unchecked")
  public List<T> readAll(){
    Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    TypedQuery<T> query = manager.createQuery("select x from " + type.getSimpleName() + " x",type);
    return  query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<T> readAllPagination(int firstResult, int maxResults) {
    Class<T> type = (Class<T>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];

    return manager.createQuery("select x from " + type.getSimpleName() + " x",type)
            .setFirstResult(firstResult - 1)
            .setMaxResults(maxResults)
            .getResultList();
  }

  //----------------------- TRANSACTION   ----------------------
  public static class Transaction {
    public static void begin(){
      if(!manager.getTransaction().isActive())
        manager.getTransaction().begin();
    }
    public static void commit(){
      if(manager.getTransaction().isActive()){
        manager.getTransaction().commit();
        manager.clear();		// ---- clear the objects of the cache ----
      }
    }
    public static void rollback(){
      if(manager.getTransaction().isActive())
        manager.getTransaction().rollback();
    }
  }
}
