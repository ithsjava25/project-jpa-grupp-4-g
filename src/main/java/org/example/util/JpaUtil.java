package org.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory EMF =
        Persistence.createEntityManagerFactory("gamePU");

    public static EntityManager createEntityManager() {
        return EMF.createEntityManager();
    }

    public static void close() {
        EMF.close();
    }
}

