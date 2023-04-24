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
    private MapSubviewModel mapSubviewModel;

    @FXML
    private Label requestRoute;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onRequestRouteButton(ActionEvent actionEvent) {
        requestRoute.setText("the button works");

        try {
            requestRoute.setText((String) MapService.getTestRoute());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
