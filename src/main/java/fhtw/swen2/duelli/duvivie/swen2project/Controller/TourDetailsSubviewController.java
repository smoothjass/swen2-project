package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.TourDetailsSubviewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TourDetailsSubviewController implements Initializable {
    private TourDetailsSubviewModel tourDetailsSubviewModel;
    private TourFormController tourFormController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public TourDetailsSubviewController(TourDetailsSubviewModel tourDetailsSubviewModel) {
        this.tourDetailsSubviewModel = tourDetailsSubviewModel;
    }
}
