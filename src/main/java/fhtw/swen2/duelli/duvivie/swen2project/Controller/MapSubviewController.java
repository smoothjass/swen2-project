package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.MapSubviewModel;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class MapSubviewController implements Initializable {
    @FXML
    public ImageView imageView;
    private MapSubviewModel mapSubviewModel;

    public MapSubviewController(MapSubviewModel mapSubviewModel) {
        this.mapSubviewModel = mapSubviewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void requestImage(Tour item) {
        // none of these lines work
        //imageView.setImage(this.mapSubviewModel.requestImage(item));
        imageView = new ImageView(this.mapSubviewModel.requestImage(item));
    }
}
