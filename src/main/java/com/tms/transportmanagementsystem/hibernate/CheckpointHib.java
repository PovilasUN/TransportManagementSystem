package com.tms.transportmanagementsystem.hibernate;

import com.tms.transportmanagementsystem.model.Checkpoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CheckpointHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CheckpointHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createCheckpoint(Checkpoint checkpoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(checkpoint);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void updateCheckpoint(Checkpoint checkpoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(checkpoint);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteCheckpoint(Checkpoint checkpoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(checkpoint));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Checkpoint> getAllCheckpoints() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Checkpoint.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }

    public Checkpoint getCheckpointById(int id) {
        entityManager = emf.createEntityManager();
        Checkpoint checkpoint = null;
        try {
            entityManager.getTransaction().begin();
            checkpoint = entityManager.find(Checkpoint.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such checkpoint by given Id");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return checkpoint;
    }
}
