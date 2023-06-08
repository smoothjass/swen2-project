package fhtw.swen2.duelli.duvivie.swen2project;

import fhtw.swen2.duelli.duvivie.swen2project.Controller.ControllerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

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
                                logger.error("An error occurred while creating Controllers" + e.getMessage());
                            }
                            return null;
                        });
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }
}