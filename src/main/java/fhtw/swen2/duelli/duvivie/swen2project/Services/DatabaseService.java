package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseService {
    private TourDao tourDao;

    public DatabaseService() {
        // Create an EntityManagerFactory when you start the application.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaHelps");
        // Create the DAO object
        this.tourDao = new TourDao(entityManagerFactory);
        // TODO other DAOS
    }

    public Tour saveTour(Tour newTour) {
        return tourDao.create(newTour);
    }
}
