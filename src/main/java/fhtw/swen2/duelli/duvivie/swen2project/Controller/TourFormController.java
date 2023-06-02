package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourFormModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.SubmissionPublisher;

import static java.lang.Integer.parseInt;

public class TourFormController implements Initializable {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    private final TourFormModel tourFormModel;
    public ImageView imageView;
    private SubmissionPublisher<Map<Tour, Image>> publisher;

    public TextField name;
    public TextField distance;
    public TextField duration;
    public TextField to;
    public TextField from;
    public TextArea description;
    public ChoiceBox<String> transportType;
    private Map<Tour, Image> currentlySelected = new HashMap<>();

    public TourFormController(TourFormModel tourFormModel, SubmissionPublisher<Map<Tour, Image>> publisher) {
        this.tourFormModel = tourFormModel;
        this.publisher = publisher;
    }

    public void saveNewTourData(ActionEvent actionEvent) {
        // TODO input validation
        // display error, if applicable

        // TODO display spinner

        // invoke model to request directions & image & save tour to db
        Map<Tour, Image> tour = tourFormModel.saveTour();
        // fire event so that mainview controller updates currentlySelectedTour
        publisher.submit(tour);

        // TODO remove spinner
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
        this.imageView.imageProperty().bindBidirectional(tourFormModel.getImageView());
    }

    public Image requestImage(Tour tour) {
        return tourFormModel.requestImage(tour);
    }

    private void updateImage(Image image) {
        // check if null
        imageView.setImage(image);
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        currentlySelected = item;
        if(currentlySelected.entrySet().iterator().next().getValue() != null) {
            updateImage(currentlySelected.entrySet().iterator().next().getValue());
        }
        if(currentlySelected.entrySet().iterator().next().getKey() != null) {
            autoFillTourData(currentlySelected.entrySet().iterator().next().getKey());
        }
    }

    private void autoFillTourData(Tour tour) {
        this.to.setText(tour.to);
        this.from.setText(tour.from);
        this.description.setText(tour.description);
        // TODO set transport type, gotta check how
        // this.transportType.setValue(String.valueOf(tour.transportType.getType()));
        this.duration.setText(String.valueOf(tour.duration));
        this.distance.setText(String.valueOf(tour.distance));
        this.name.setText(tour.name);
    }
}
