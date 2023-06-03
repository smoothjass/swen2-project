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