package fhtw.swen2.duelli.duvivie.swen2project;

import fhtw.swen2.duelli.duvivie.swen2project.Controller.ControllerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public void testFunction() {
        // Create an EntityManagerFactory when you start the application.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaHelps");
        // Create the DAO object
        TourDao tourDao = new TourDao(entityManagerFactory);

        //These transportypes need to be added to every new or updated tour
        TransportType pedestrian = new TransportType();
        pedestrian.setTransport_type_id(1);
        pedestrian.setType("pedestrian");

        TransportType car = new TransportType();
        pedestrian.setTransport_type_id(2);
        car.setType("car");

        TransportType bike = new TransportType();
        pedestrian.setTransport_type_id(3);
        bike.setType("bike");

        // Create a new tour
        Tour tour = new Tour();
        tour.setName("TestTour");
        tour.setDescription("TestDescription");
        tour.setFrom("TestFrom");
        tour.setTo("TestTo");
        tour.setDistance(100F);
        tour.setDuration(100);
        tour.setTransportType(pedestrian);

        // Persist the tour object
        tourDao.create(tour);

        //tourDao.deleteAll();

        // Never forget to close the entityManagerFactory
        entityManagerFactory.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        testFunction();
        ControllerFactory factory = new ControllerFactory();
        FXMLLoader fxmlLoader = getFxmlLoader(factory);
        System.out.println(fxmlLoader);
        Scene scene = new Scene(fxmlLoader.load(), 620, 580);
        stage.setMaximized(true);
        stage.setTitle("TourPlanner");
        stage.setScene(scene);
        stage.show();
    }

    private FXMLLoader getFxmlLoader(ControllerFactory factory) {
        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        Application.class.getResource("main-view.fxml"),
                        null,
                        new JavaFXBuilderFactory(),
                        controller -> {
                            try {
                                return factory.create(controller);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }
}