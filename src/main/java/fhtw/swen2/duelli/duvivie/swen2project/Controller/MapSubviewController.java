package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.MapSubviewModel;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class MapSubviewController implements Initializable {
    @FXML
    public Label requestRoute;
    private MapSubviewModel mapSubviewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void onRequestRouteButton(ActionEvent actionEvent) {
        try {
            MapService.getTestRoute();
            requestRoute.setText("the button works");
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
