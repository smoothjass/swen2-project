package fhtw.swen2.duelli.duvivie.swen2project.Daos;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class LogDao {
    private final EntityManagerFactory entityManagerFactory;

    public LogDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Log create(Log log) {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Save the tour object
            manager.persist(log);
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
        return log;
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

  public void delete(int id) {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student
            Log log = manager.find(Log.class, id);
            if (log != null) {
                // Remove the student
                manager.remove(log);
            }
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
    }

    public Log getById(Integer log_id){
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        Log log;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Note that the SQL is selecting from "Log" entity not the "tours" table
            log = manager.createQuery("SELECT log FROM Log log WHERE log.log_id = :logId", Log.class)
                    .setParameter("logId", log_id)
                    .getSingleResult();

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
        return log;
    }

    public void update(Log newLogWithSameId) {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student to update the object
            // You cannot insert a new student with the same id since it will be treated as a duplicate entry
            Log log = manager.find(Log.class, newLogWithSameId.getLog_id());
            if (log != null) {
                // Note that the id cannot be changed
                    log.setStarting_time(newLogWithSameId.getStarting_time());
                    log.setComment(newLogWithSameId.getComment());
                    log.setDifficulty(newLogWithSameId.getDifficulty());
                    log.setTotal_time(newLogWithSameId.getTotal_time());
                    log.setRating(newLogWithSameId.getRating());
                // Save the changes
                manager.persist(log);
            }
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
    }
}
