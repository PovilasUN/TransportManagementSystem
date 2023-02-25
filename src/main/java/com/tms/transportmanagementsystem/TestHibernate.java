package com.tms.transportmanagementsystem;

import com.tms.transportmanagementsystem.hibernate.UserHib;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        UserHib userHib = new UserHib(entityManagerFactory);
    }
}
