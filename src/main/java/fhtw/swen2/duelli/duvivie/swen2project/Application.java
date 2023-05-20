package fhtw.swen2.duelli.duvivie.swen2project;

import fhtw.swen2.duelli.duvivie.swen2project.Controller.ControllerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Services.ReportService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {

    public void testFunctionSingleReport(){
        TransportType pedestrian = new TransportType();
        pedestrian.setTransport_type_id(1);
        pedestrian.setType("pedestrian");

        // Create a new tour
        Tour tour = new Tour();
        tour.setName("TestTour");
        tour.setDescription("TestDescription");
        tour.setFrom("TestFrom");
        tour.setTo("TestTo");
        tour.setDistance(100F);
        tour.setDuration(100);
        tour.setTransportType(pedestrian);

        Log logA = new Log();

        logA.setLog_id(1);
        logA.setDifficulty(1);
        logA.setComment("vghjkl");
        logA.setRating(1);
        logA.setPicture_path("vbnjklö");
        logA.setStarting_time(new Timestamp(12));
        logA.setTotal_time(12);

        Log logB = new Log();

        logB.setLog_id(2);
        logB.setComment("vghjkl");
        logB.setDifficulty(100);
        logB.setPicture_path("vbnjklö");
        logB.setStarting_time(new Timestamp(12));
        logB.setTotal_time(12);

        ArrayList<Log> logList = new ArrayList<Log>();

        logList.add(logA);
        logList.add(logB);

        ReportService reportService = new ReportService();

        try {
            System.out.println(reportService.genereateSingleTourPdf(tour, logList, "google_maps.PNG"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testFunctionDatabase() {
        // Create an EntityManagerFactory when you start the application.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaHelps");
        // Create the DAO object
        TourDao tourDao = new TourDao(entityManagerFactory);

        //These transportypes need to be added to every new or updated tour
        TransportType pedestrian = new TransportType();
        pedestrian.setTransport_type_id(1);
        pedestrian.setType("pedestrian");

        TransportType car = new TransportType();
        car.setTransport_type_id(2);
        car.setType("car");

        TransportType bike = new TransportType();
        bike.setTransport_type_id(3);
        bike.setType("bike");

        TransportType secondPedestrian = new TransportType();
        secondPedestrian.setTransport_type_id(1);
        secondPedestrian.setType("pedestrian");

        // Create a new tour
        Tour tour = new Tour();
        tour.setName("TestTour");
        tour.setDescription("TestDescription");
        tour.setFrom("TestFrom");
        tour.setTo("TestTo");
        tour.setDistance(100F);
        tour.setDuration(100);
        tour.setTransportType(pedestrian);

        Tour tourB = new Tour();
        tourB.setName("sdhifo");
        tourB.setDescription("asdijfopasdf");
        tourB.setFrom("s^dfghjk");
        tourB.setTo("tgfbnmkiuzgv");
        tourB.setDistance(456789F);
        tourB.setDuration(3467890);
        tourB.setTransportType(secondPedestrian);

        // Persist the tour object
        tourDao.create(tour);
        tourDao.create(tourB);

        tourDao.deleteAll();

        // Never forget to close the entityManagerFactory
        entityManagerFactory.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //testFunctionDatabase();
        //testFunctionSingleReport();
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