package fhtw.swen2.duelli.duvivie.swen2project;

import fhtw.swen2.duelli.duvivie.swen2project.Controller.ControllerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class Application extends javafx.application.Application {

    private Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        // connectToDatabase();
        // Create an EntityManagerFactory when you start the application.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JavaHelps");
        // Create the DAO object
        TourDao dao = new TourDao(entityManagerFactory);

        Tour testTour = new Tour();
        testTour.setName("test");
        testTour.setDescription("description");
        testTour.setDistance(5.0F);
        testTour.setFrom("uranus");
        testTour.setTo("joemama");
        dao.create(testTour);

        List<Tour> tours = dao.findAll();
        if (tours != null) {
            for (Tour tour : tours) {
                System.out.println(tour);
            }
        }

        dao.delete(1);

        tours = dao.findAll();
        if (tours != null) {
            for (Tour tour : tours) {
                System.out.println(tour);
            }
        }

        // Never forget to close the entityManagerFactory
        entityManagerFactory.close();

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

    private void connectToDatabase() {
        try{
            connection = new DatabaseService().getConnection();
            System.out.println("Database connection established");
        }
        catch (Exception e){
            e.printStackTrace();
            //Stop the application if the database connection fails
            System.exit(1);
        }
   }

    public static void main(String[] args) {
        launch();
    }
}