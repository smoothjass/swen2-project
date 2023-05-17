package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourFormModel;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class TourFormController implements Initializable {

    private final TourFormModel tourFormModel;

    public TextField name;
    public TextField distance;
    public TextField duration;
    public TextField to;
    public TextField from;
    public TextArea description;
    //public ChoiceBox<String> transportType;

    public TourFormController(TourFormModel tourFormModel) {
        this.tourFormModel = tourFormModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.textProperty().bindBidirectional(tourFormModel.getName());
        this.distance.textProperty().bindBidirectional(tourFormModel.getDistance());
        this.duration.textProperty().bindBidirectional(tourFormModel.getDuration());
        this.to.textProperty().bindBidirectional(tourFormModel.getTo());
        this.from.textProperty().bindBidirectional(tourFormModel.getFrom());
        this.description.textProperty().bindBidirectional(tourFormModel.getDescription());
        //this.transportType.valueProperty().bindBidirectional(tourFormModel.getTransportType());
    }
}
