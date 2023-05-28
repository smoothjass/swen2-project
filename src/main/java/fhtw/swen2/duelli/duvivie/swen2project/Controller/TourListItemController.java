package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListItemModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListItemController implements Initializable {

    public TourListItemModel tourListItemModel;

    @FXML
    public Label name;

    public TourListItemController(TourListItemModel tourListItemModel) {
        this.tourListItemModel = tourListItemModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.textProperty().bindBidirectional(tourListItemModel.getName());
    }
}
