package org.example.projetoPobJpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;

public abstract class DAO<T> implements DAOInterface<T> {
  protected static EntityManager manager;
  protected static EntityManagerFactory factory;

  public DAO(){}

  public static void open(){
    if(manager==null){
      //propriedades do persistence.xml  que podem ser sobrescritas
      HashMap<String,String> properties = new HashMap<String,String>();
      //	properties.put(PersistenceUnitProperties.LOGGING_LEVEL, "fine");
      //	properties.put(PersistenceUnitProperties.LOGGING_FILE, "log.txt");
      factory = Persistence.createEntityManagerFactory("eclipselink-mongodb", properties);
      manager = factory.createEntityManager();
    }
  }
  public static void close(){
    if(manager != null && manager.isOpen()) {
      manager.close();
      factory.close();
      manager=null;
    }
  }
  public void create(T obj){
    manager.persist(obj);
  }

  public abstract T read(Object chave) throws Exception;

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

  //----------------------- TRANSAÇÃO   ----------------------

  public static class Transaction {
    public static void begin(){
      if(!manager.getTransaction().isActive())
        manager.getTransaction().begin();
    }
    public static void commit(){
      if(manager.getTransaction().isActive()){
        manager.getTransaction().commit();
        manager.clear();		// ---- esvaziar o cache de objetos
      }
    }
    public static void rollback(){
      if(manager.getTransaction().isActive())
        manager.getTransaction().rollback();
    }
  }

  /********************************************************************
   *
   *    EVENTOS (TRIGGERS)
   *
   ********************************************************************/
  //	@PrePersist
  //	public void exibirmsg1(Object obj) throws Exception {
  //		System.out.println(" @PrePersist... " + obj);
  //	}
  //	@PostPersist
  //	public void exibirmsg2(Object obj) {
  //		System.out.println(" @PostPersist... " + obj);
  //	}

}