package fhtw.swen2.duelli.duvivie.swen2project.Controller;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListItemModel;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListSubviewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
public class TourListSubviewController implements Initializable {

    @FXML
    public VBox tourListButtons;

    @FXML
    public ListView<TourListItemModel> tours;

    private TourListSubviewModel tourListSubviewModel;

    public TourListSubviewController(TourListSubviewModel tourListSubviewModel) {
        this.tourListSubviewModel = tourListSubviewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox.setVgrow(tourListButtons, Priority.ALWAYS);
        tourListButtons.setAlignment(Pos.BOTTOM_CENTER);

        //load all tours and create a TourListItemModel for each tour
        tourListSubviewModel.getTours().forEach(tour -> {
            TourListItemModel tourListItemModel = new TourListItemModel();
            tourListItemModel.name.setValue(tour.getName());
            //tourListItemModel.setName(new SimpleStringProperty(tour.getName()));
            tours.getItems().add(tourListItemModel);
            System.out.println(tourListItemModel.getName().getValue());
        });
    }
}

