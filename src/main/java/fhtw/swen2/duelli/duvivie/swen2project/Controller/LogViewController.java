package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.LogViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class LogViewController implements Initializable {
    public ImageView imageView;
    private LogViewModel logViewModel;

    @FXML
    Button saveLogButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public LogViewController(LogViewModel logViewModel) {
        this.logViewModel = logViewModel;
    }

    public void initialize() {
    }

    public void updateImage(Image image) {
        imageView.setImage(image);
    }
}
