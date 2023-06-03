package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourDetailsSubviewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.SubmissionPublisher;

public class TourDetailsSubviewController implements Initializable {
    private TourDetailsSubviewModel tourDetailsSubviewModel;
    // private TourFormController tourFormController;
    private SubmissionPublisher<Map<Tour, Image>> publisher;

    private Map<Tour, Image> currentlySelected = new HashMap<>();

    @FXML
    VBox detailsContentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public TourDetailsSubviewController(TourDetailsSubviewModel tourDetailsSubviewModel, SubmissionPublisher<Map<Tour, Image>> publisher) {
        this.tourDetailsSubviewModel = tourDetailsSubviewModel;
        this.publisher = publisher;
    }

    public void showTourForm(ActionEvent actionEvent) {
        detailsContentArea.getChildren().get(0).setVisible(true);
        detailsContentArea.getChildren().get(0).setManaged(true);
        detailsContentArea.getChildren().get(1).setVisible(false);
        detailsContentArea.getChildren().get(1).setManaged(false);
        detailsContentArea.getChildren().get(2).setVisible(false);
        detailsContentArea.getChildren().get(2).setManaged(false);
    }

    public void showLogList(ActionEvent actionEvent) {
        detailsContentArea.getChildren().get(0).setVisible(false);
        detailsContentArea.getChildren().get(0).setManaged(false);
        detailsContentArea.getChildren().get(1).setVisible(true);
        detailsContentArea.getChildren().get(1).setManaged(true);
        detailsContentArea.getChildren().get(2).setVisible(false);
        detailsContentArea.getChildren().get(2).setManaged(false);
    }

    public void showPictureGallery(ActionEvent actionEvent) {
        detailsContentArea.getChildren().get(0).setVisible(false);
        detailsContentArea.getChildren().get(0).setManaged(false);
        detailsContentArea.getChildren().get(1).setVisible(false);
        detailsContentArea.getChildren().get(1).setManaged(false);
        detailsContentArea.getChildren().get(2).setVisible(true);
        detailsContentArea.getChildren().get(2).setManaged(true);
    }

    public void setCurrentlySelectedTour(Tour tour) {
        this.currentlySelectedTour = tour;
    }

    public void createSingleReport(ActionEvent actionEvent) {
        tourDetailsSubviewModel.createSingleReport(currentlySelectedTour);
    }

    public void deleteCurrentTour(ActionEvent actionEvent) {
        setCurrentlySelectedTour(null);

        tourDetailsSubviewModel.deleteCurrentTour(currentlySelectedTour);
    }
}