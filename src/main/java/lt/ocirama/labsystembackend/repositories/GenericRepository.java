package lt.ocirama.labsystembackend.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GenericRepository<T> {

    private final Class<T> tClass;
    private final EntityManagerFactory entityManagerFactory;

    public GenericRepository(Class<T> tClass, EntityManagerFactory entityManagerFactory) {
        this.tClass = tClass;
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void update(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void delete(T entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void delete(List<T> entities) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            entities.forEach(em::remove);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public T findOne(Integer id) {
        return entityManagerFactory.createEntityManager().find(tClass, id);
    }

    public List<T> findAll() {
        return entityManagerFactory.createEntityManager().createQuery("SELECT entity from " + tClass.getName() + " entity", tClass).getResultList();
    }
}
