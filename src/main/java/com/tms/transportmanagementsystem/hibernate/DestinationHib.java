package com.tms.transportmanagementsystem.hibernate;

import com.tms.transportmanagementsystem.model.Destination;
import com.tms.transportmanagementsystem.model.Driver;
import com.tms.transportmanagementsystem.model.Manager;
import com.tms.transportmanagementsystem.model.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DestinationHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public DestinationHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createDestination(Destination destination) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(destination);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void updateDestination(Destination destination) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(destination);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void deleteDestination(Destination destination) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(destination));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Destination> getAllDestinations() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Destination.class));
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

    public Destination getDestinationById(int id) {
        entityManager = emf.createEntityManager();
        Destination destination = null;
        try {
            entityManager.getTransaction().begin();
            destination = entityManager.find(Destination.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such destination by given Id");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return destination;
    }
}
