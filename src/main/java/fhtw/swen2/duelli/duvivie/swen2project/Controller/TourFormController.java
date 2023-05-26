package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourFormModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.SubmissionPublisher;

import static java.lang.Integer.parseInt;

public class TourFormController implements Initializable {

    private final TourFormModel tourFormModel;
    private SubmissionPublisher<Tour> publisher;

    public TextField name;
    public TextField distance;
    public TextField duration;
    public TextField to;
    public TextField from;
    public TextArea description;
    public ChoiceBox<String> transportType;

    public TourFormController(TourFormModel tourFormModel, SubmissionPublisher<Tour> publisher) {
        this.tourFormModel = tourFormModel;
        this.publisher = publisher;
    }

    public void saveNewTourData(ActionEvent actionEvent) {
        // TODO input validation
        // display error, if applicable

        // model aufrufen
        Tour tour = tourFormModel.saveTour();
        publisher.submit(tour);
        // todo fire event so that mainview controller updates currentlySelectedTour and displays image
        // right now, image is retrieved when saving the tour but it would make more sense if the mapsubviewmodel would
        // be invoked by the mainviewcontroller (via the mapsubviewcontroller) and then the mapsubviewcontroller called
        // the mapservice to get and display the image (with the information it can get about the tour using the
        // currently selected tour id TODO this ^
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.textProperty().bindBidirectional(tourFormModel.getName());
        this.distance.textProperty().bindBidirectional(tourFormModel.getDistance());
        this.duration.textProperty().bindBidirectional(tourFormModel.getDuration());
        this.to.textProperty().bindBidirectional(tourFormModel.getTo());
        this.from.textProperty().bindBidirectional(tourFormModel.getFrom());
        this.description.textProperty().bindBidirectional(tourFormModel.getDescription());
        this.transportType.valueProperty().bindBidirectional(tourFormModel.getTransportType());
    }
}
