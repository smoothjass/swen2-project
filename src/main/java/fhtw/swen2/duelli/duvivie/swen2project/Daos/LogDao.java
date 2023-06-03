package fhtw.swen2.duelli.duvivie.swen2project.Daos;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;

import java.util.List;

public class LogDao {
    private final EntityManagerFactory entityManagerFactory;

    public LogDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Log> findAll() {
         // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        List<Log> logs;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Log" entity not the "tours" table
            logs = manager.createQuery("SELECT log FROM Log log", Log.class)
                    .getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return logs;
    }

    public List<Log> getAllByTourId(int tourId) {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        List<Log> logs;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Log" entity not the "tours" table
            logs = manager.createQuery("SELECT log FROM Log log WHERE log.tour_id = :tourId", Log.class)
                    .setParameter("tourId", tourId)
                    .getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return logs;
    }
}
