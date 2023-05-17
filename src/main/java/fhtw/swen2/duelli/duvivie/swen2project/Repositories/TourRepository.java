package fhtw.swen2.duelli.duvivie.swen2project.Repositories;


import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TourRepository {
    private final EntityManagerFactory entityManagerFactory;
    private final TourDao tourDao;

    public TourRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("JavaHelps");
        this.tourDao = new TourDao(this.entityManagerFactory);
    }

    public Tour getTourById(int id) {
        return this.tourDao.getTourById(id);
    }

    public void saveTour(Tour tour) {
        this.tourDao.create(tour);
    }

    public void deleteTourByID(int id) {
        this.tourDao.delete(id);
    }
}
