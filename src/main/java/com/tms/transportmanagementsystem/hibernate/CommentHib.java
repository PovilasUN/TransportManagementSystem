package com.tms.transportmanagementsystem.hibernate;

import com.tms.transportmanagementsystem.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CommentHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createComment(Comment comment) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
