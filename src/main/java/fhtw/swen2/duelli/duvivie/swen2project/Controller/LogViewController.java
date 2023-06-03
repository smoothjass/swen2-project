package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.LogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LogViewController implements Initializable {
    public ImageView imageView;
    private LogViewModel logViewModel;

    private Map<Tour, Image> currentlySelected = new HashMap<>();
    @FXML
    Button saveLogButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public LogViewController(LogViewModel logViewModel) {
        this.logViewModel = logViewModel;
    }

    public void initialize() {
    }

    private void updateImage(Image image) {
        imageView.setImage(image);
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        currentlySelected = item;
        Image image = currentlySelected.entrySet().iterator().next().getValue();
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();
        updateImage(image);
        if(tour == null) {
            // TODO clear everything because a new tour is being created
        }
    }

    public void addLog(ActionEvent actionEvent) {
    }
}
