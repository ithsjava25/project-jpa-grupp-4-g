package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class BootstrapService {
    private EntityManager em;

    public BootstrapService(EntityManager em) {
        this.em = em;
    }

    public void initialize() {
        if (dataBaseAlreadySeeded()){
            System.out.println("Database is already seeded");
            return;
        }
        System.out.println("Bootstrap: inserting base data...");
        runSqlFile();
    }

    private boolean dataBaseAlreadySeeded() {
        Long count = em.createQuery("select count(c) from Continent c", Long.class).getSingleResult();
        return count > 0;
    }

    private void runSqlFile() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/travelGame-data.sql");
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + "travelGame-data.sql");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
               line = line.trim();
               if (line.isEmpty() || line.startsWith("--")) {continue;}
               stringBuilder.append(line);

               if (line.endsWith(";")) {
                   em.createNativeQuery(stringBuilder.toString()).executeUpdate();
                   stringBuilder.setLength(0);
               }

            }
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("failed to bootstrap database", e);
        }
    }
}
