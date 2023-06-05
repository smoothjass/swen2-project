package fhtw.swen2.duelli.duvivie.swen2project.Daos;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;

import java.util.List;

public class TourDao {
    private final EntityManagerFactory entityManagerFactory;

    public TourDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Tour> findAll() throws RollbackException {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        List<Tour> tours;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Tour" entity not the "tours" table
            tours = manager.createQuery("SELECT tour FROM Tour tour", Tour.class)
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
        return tours;
    }

    public Tour create(Tour tour) throws RollbackException {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Save the tour object
            manager.persist(tour);
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
        return tour;
    }

    public void update(Tour newTourWithSameId) throws RollbackException {
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
            Tour tour = manager.find(Tour.class, newTourWithSameId.getTour_id());
            if (tour != null) {
                // Note that the id cannot be changed
                tour.setName(newTourWithSameId.getName());
                tour.setDescription(newTourWithSameId.getDescription());
                tour.setFrom(newTourWithSameId.getFrom());
                tour.setTo(newTourWithSameId.getTo());
                tour.setTransportType(newTourWithSameId.getTransportType());
                tour.setDistance(newTourWithSameId.getDistance());
                tour.setDuration(newTourWithSameId.getDuration());
                // Save the changes
                manager.persist(tour);
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

    public void delete(int id) throws RollbackException {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student
            Tour tour = manager.find(Tour.class, id);
            if (tour != null) {
                // Remove the student
                manager.remove(tour);
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

    public void deleteAll() throws RollbackException {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Delete all students
            manager.createQuery("DELETE FROM Tour")
                    .executeUpdate();
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

    public Tour getTourById(int id) throws RollbackException {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        Tour tour;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Tour" entity not the "tours" table
            tour = manager.createQuery("SELECT tour FROM Tour tour WHERE tour.tour_id = :id", Tour.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            tour = null;
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return tour;
    }

    public void deleteTourById(int tourId) throws RollbackException {
        // Create a new EntityManager
        EntityManager manager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student
            Tour tour = manager.find(Tour.class, tourId);
            if (tour != null) {
                // Remove the student
                manager.remove(tour);
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
