package fhtw.swen2.duelli.duvivie.swen2project.Controller;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListSubviewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
public class TourListSubviewController implements Initializable {
    private TourListSubviewModel tourListSubviewModel;

    @FXML
    private VBox tourListButtons;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox.setVgrow(tourListButtons, Priority.ALWAYS);
        tourListButtons.setAlignment(Pos.BOTTOM_CENTER);
    }
}
