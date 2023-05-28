package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Daos.LogDao;
import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private TourDao tourDao;
    private LogDao logDao;

    public DatabaseService() {
        // Create an EntityManagerFactory when you start the application.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaHelps");
        // Create the DAO object
        this.tourDao = new TourDao(entityManagerFactory);
        this.logDao = new LogDao(entityManagerFactory);
    }

    public void saveTour(Tour newTour) {
        tourDao.create(newTour);
    }

    public List<Tour> getTours() {
        return tourDao.findAll();
    }
}
