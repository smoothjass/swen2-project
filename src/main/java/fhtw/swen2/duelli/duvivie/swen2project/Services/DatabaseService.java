package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Daos.LogDao;
import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;

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

    public Tour saveTour(Tour newTour) throws RollbackException {
        return tourDao.create(newTour);
    }

    public List<Tour> getTours() throws RollbackException {
        return tourDao.findAll();
    }

    public List<Log> getTourLogs() throws RollbackException {
        return logDao.findAll();
    }

    public List<Log> getLogsForTour(Integer tourId) throws RollbackException {
        return logDao.getAllByTourId(tourId);
    }

    public List<Log> getAllLogsForTour(Tour currentlySelectedTour) throws RollbackException {
        return logDao.getAllByTourId(currentlySelectedTour.tour_id);
    }

    public void deleteTour(Tour currentlySelectedTour) throws RollbackException {
        logDao.deleteAllLogsByTourId(currentlySelectedTour.tour_id);
        tourDao.deleteTourById(currentlySelectedTour.tour_id);
    }

    public Log saveLog(Log log) throws RollbackException{
       return logDao.create(log);
    }

    public Log updateLog(Log log) throws RuntimeException {
        logDao.update(log);
        return logDao.getById(log.log_id);
    }

    public void deleteLog(Integer logId) throws RollbackException {
        logDao.delete(logId);
    }

    public Tour updateTour(Tour tour) {
        tourDao.update(tour);
        return tourDao.getTourById(tour.getTour_id());
    }

    public Tour getTourById(int tour_id) {
        return tourDao.getTourById(tour_id);
    }
}
