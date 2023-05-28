package fhtw.swen2.duelli.duvivie.swen2project;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import fhtw.swen2.duelli.duvivie.swen2project.Controller.ControllerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Services.CSVService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application extends javafx.application.Application {

     public void testFunctionSummaryReport(){
        TransportType pedestrian = new TransportType();
        pedestrian.setTransport_type_id(1);
        pedestrian.setType("pedestrian");

        TransportType car = new TransportType();
        car.setTransport_type_id(2);
        car.setType("car");

        // Create a new tour
        Tour tourA = new Tour();
        tourA.setTour_id(1);
        tourA.setName("TestTourA");
        tourA.setDescription("TestDescriptionA");
        tourA.setFrom("TestFromA");
        tourA.setTo("TestToA");
        tourA.setDistance(100F);
        tourA.setDuration(100);
        tourA.setTransportType(pedestrian);

        Tour tourB = new Tour();
        tourB.setTour_id(2);
        tourB.setName("TestTourB");
        tourB.setDescription("TestDescriptionB");
        tourB.setFrom("TestFromB");
        tourB.setTo("TestToB");
        tourB.setDistance(200F);
        tourB.setDuration(200);
        tourB.setTransportType(car);

        Tour tourC = new Tour();
        tourC.setTour_id(3);
        tourC.setName("TestTourC");
        tourC.setDescription("TestDescriptionC");
        tourC.setFrom("TestFromC");
        tourC.setTo("TestToC");
        tourC.setDistance(300F);
        tourC.setDuration(300);
        tourC.setTransportType(pedestrian);

        List<Tour> tourList = new ArrayList<>();
        tourList.add(tourA);
        tourList.add(tourB);
        tourList.add(tourC);

        Log logA1 = new Log();
        logA1.setLog_id(1);
        logA1.setDifficulty(1);
        logA1.setComment("vghjkl");
        logA1.setTotal_time(100);
        logA1.setStarting_time(new Timestamp(100));
        logA1.setRating(10);
        logA1.setTour_id(tourA.tour_id);

        Log logA2 = new Log();
        logA2.setLog_id(2);
        logA2.setDifficulty(2);
        logA2.setComment("vghjkl");
        logA2.setTotal_time(200);
        logA2.setRating(20);
        logA2.setStarting_time(new Timestamp(200));

        logA2.setTour_id(tourA.tour_id);

        Log logB1 = new Log();
        logB1.setLog_id(3);
        logB1.setDifficulty(3);
        logB1.setComment("vghjkl");
        logB1.setTotal_time(300);
        logB1.setStarting_time(new Timestamp(300));
        logB1.setRating(30);
        logB1.setTour_id(tourB.tour_id);

        Log logB2 = new Log();
        logB2.setLog_id(4);
        logB2.setDifficulty(4);
        logB2.setComment("vghjkl");
        logB2.setTotal_time(400);
        logB2.setStarting_time(new Timestamp(400));
        logB2.setRating(40);
        logB2.setTour_id(tourB.tour_id);

        Log logC1 = new Log();
        logC1.setLog_id(5);
        logC1.setDifficulty(5);
        logC1.setComment("vghjkl");
        logC1.setTotal_time(500);
        logC1.setStarting_time(new Timestamp(500));
        logC1.setRating(50);
        logC1.setTour_id(tourC.tour_id);

        Log logC2 = new Log();
        logC2.setLog_id(6);
        logC2.setDifficulty(6);
        logC2.setComment("vghjkl");
        logC2.setTotal_time(600);
        logC2.setStarting_time(new Timestamp(600));
        logC2.setRating(60);
        logC2.setTour_id(tourC.tour_id);

        //create a list of logs
        List<Log> logList = new ArrayList<>();
        logList.add(logA1);
        logList.add(logA2);
        logList.add(logB1);
        logList.add(logB2);
        logList.add(logC1);
        logList.add(logC2);

        ReportService reportService = new ReportService();

        try {
            reportService.createSummaryReport(tourList, logList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void testFunctionCSVExport(){
         TransportType pedestrian = new TransportType();
        pedestrian.setTransport_type_id(1);
        pedestrian.setType("pedestrian");

        Tour tourA = new Tour();
        tourA.setTour_id(1);
        tourA.setName("TestTourA");
        tourA.setDescription("TestDescriptionA");
        tourA.setFrom("TestFromA");
        tourA.setTo("TestToA");
        tourA.setDistance(100F);
        tourA.setDuration(100);
        tourA.setTransportType(pedestrian);

         Log logA1 = new Log();
        logA1.setLog_id(1);
        logA1.setDifficulty(1);
        logA1.setComment("vghjkl");
        logA1.setTotal_time(100);
        logA1.setStarting_time(new Timestamp(100));
        logA1.setRating(10);
        logA1.setTour_id(tourA.tour_id);

        Log logA2 = new Log();
        logA2.setLog_id(2);
        logA2.setDifficulty(2);
        logA2.setComment("vghjkl");
        logA2.setTotal_time(200);
        logA2.setRating(20);
        logA2.setStarting_time(new Timestamp(200));

        logA2.setTour_id(tourA.tour_id);

        List<Log> logList = new ArrayList<>();
        logList.add(logA1);
        logList.add(logA2);

        CSVService csvService = new CSVService();
        try {
            csvService.export(tourA, logList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void testImportCSV(){

         Map<Tour, List<Log>> result;

        CSVService csvService = new CSVService();
        try {
            result = csvService.importCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //print out the result
        for (Map.Entry<Tour, List<Log>> entry : result.entrySet()) {
            Tour resultTour = entry.getKey();

            System.out.println(resultTour.tour_id);
            System.out.println(resultTour.getName());
            System.out.println(resultTour.getDescription());
            System.out.println(resultTour.getFrom());
            System.out.println(resultTour.getTo());
            System.out.println(resultTour.getTransportType().getType());
            System.out.println(resultTour.getDistance());
            System.out.println(resultTour.getDuration());

            for (Log log : entry.getValue()) {
                //print out the log values
                System.out.println(log.log_id);
                System.out.println(log.tour_id);
                System.out.println(log.starting_time);
                System.out.println(log.comment);
                System.out.println(log.difficulty);
                System.out.println(log.total_time);
                System.out.println(log.rating);

            }
        }
    }

     @Override
    public void start(Stage stage) throws IOException {
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