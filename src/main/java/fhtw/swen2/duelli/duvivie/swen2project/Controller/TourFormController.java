package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourFormModel;
import fhtw.swen2.duelli.duvivie.swen2project.Services.LoadingSpinnerService;

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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.SubmissionPublisher;

public class TourFormController implements Initializable {

    private final TourFormModel tourFormModel;
    public ImageView imageView;
    public javafx.scene.control.Label popularity;
    public javafx.scene.control.Label childFriendliness;
    private SubmissionPublisher<Map<Tour, Image>> publisher;

    public TextField name;
    public javafx.scene.control.Label distance;

    public javafx.scene.control.Label duration;
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
        Map<Tour, Image> tour;
        // currentlySelected tour == null >> create new tour, otherwise update
        // error when creating first tour
        if (currentlySelected.isEmpty()){
            // invoke model to request directions & image & save tour to db
            tour = tourFormModel.saveTour();
        }
        else if (currentlySelected.entrySet().iterator().next().getKey() == null) {
            tour = tourFormModel.saveTour();
        }
        else {
            // temporarily save image here
            Image image = currentlySelected.entrySet().iterator().next().getValue();
            tour = tourFormModel.updateTour(currentlySelected.entrySet().iterator().next().getKey());
            if (tour != null){ // tour == null >> invalid input
                if(tour.entrySet().iterator().next().getValue() == null) {
                    tour.entrySet().iterator().next().setValue(image);
                }
            }
        }
        // fire event so that mainview controller updates currentlySelectedTour
        if (tour != null){ // tour == null >> invalid input
            publisher.submit(tour);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.textProperty().bindBidirectional(tourFormModel.getName());
        this.distance.textProperty().bindBidirectional(tourFormModel.getDistance());
        this.duration.textProperty().bindBidirectional(tourFormModel.getDuration());
        this.to.textProperty().bindBidirectional(tourFormModel.getTo());
        this.from.textProperty().bindBidirectional(tourFormModel.getFrom());
        this.description.textProperty().bindBidirectional(tourFormModel.getDescription());
        this.childFriendliness.textProperty().bindBidirectional(tourFormModel.getChildFriendliness());
        this.popularity.textProperty().bindBidirectional(tourFormModel.getPopularity());
        this.transportType.valueProperty().bindBidirectional(tourFormModel.getTransportType());
        this.imageView.imageProperty().bindBidirectional(tourFormModel.getImageView());
    }

    public Image requestImage(Tour tour) {
        return tourFormModel.requestImage(tour);
    }

    private void updateImage(Image image) {
        imageView.setImage(image);
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        currentlySelected = item;
        Image image = currentlySelected.entrySet().iterator().next().getValue();
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();
        updateImage(image);
        tourFormModel.autoFillTourData(Objects.requireNonNullElseGet(tour, Tour::new));
    }

    public void addTour(ActionEvent actionEvent) {
        currentlySelected = new HashMap<>();
        currentlySelected.put(null, null);
        publisher.submit(currentlySelected);
    }
}
