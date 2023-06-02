package fhtw.swen2.duelli.duvivie.swen2project.Controller;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListSubviewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Setter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.SubmissionPublisher;

public class TourListSubviewController implements Initializable {
    @FXML
    public VBox tourListButtons;
    @FXML
    public ListView<String> tours;
    private Map<Integer, Tour> tourMap = new HashMap<>();
    private TourListSubviewModel tourListSubviewModel;
    private SubmissionPublisher<Map<Tour, Image>> publisher;
    private Map<Tour, Image> currentlySelected = new HashMap<>();
    public TourListSubviewController(TourListSubviewModel tourListSubviewModel, SubmissionPublisher<Map<Tour, Image>> publisher) {
        this.tourListSubviewModel = tourListSubviewModel;
        this.publisher = publisher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //load all tours and create a TourListItemModel for each tour
        tourListSubviewModel.getTours().forEach(tour -> {
            /*
            TourListItemModel tourListItemModel = new TourListItemModel();
            tourListItemModel.name.setValue(tour.getName());
            tourListItemModel.id.setValue(tour.getTour_id());
            tours.getItems().add(String.valueOf(tourListItemModel.getId().getValue()) +
                    ": " +
                    String.valueOf(tourListItemModel.getName().getValue()));
            tourMap.put(tourListItemModel.getId().getValue(), tour);
             */
            tours.getItems().add(String.valueOf(tour.getTour_id()) +
                    ": " +
                    tour.getName());
            tourMap.put(tour.getTour_id(), tour);
        });
    }

    private void updateList(Tour newTour) {
        tours.getItems().add(String.valueOf(newTour.getTour_id()) +
                ": " +
                newTour.getName());
        tourMap.put(newTour.getTour_id(), newTour);
    }

    public void addTour(ActionEvent actionEvent) {
        // TODO somehow we need to get from here to the form
        // maybe fire an event with a null item so mainviewcontroller checks if item is null and if yes, it sets
        // currently selected to null and invokes controllers respectively
        // OR PUT THAT BUTTON INTo TOUR-FORM! < TODO this
    }

    @FXML public void handleMouseClick(MouseEvent mouseEvent) {
        String selected = tours.getSelectionModel().getSelectedItem();
        Integer selectedId = Integer.valueOf(selected.split(":")[0]);
        currentlySelected.clear();
        currentlySelected.put(tourMap.get(selectedId), null);
        publisher.submit(currentlySelected);
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        currentlySelected = item;
        if (!tourMap.containsKey(currentlySelected.entrySet().iterator().next().getKey().getTour_id())) {
            updateList(currentlySelected.entrySet().iterator().next().getKey());
        }
    }
}

