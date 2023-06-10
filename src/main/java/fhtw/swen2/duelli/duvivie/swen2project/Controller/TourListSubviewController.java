package fhtw.swen2.duelli.duvivie.swen2project.Controller;
import com.itextpdf.layout.element.ListItem;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListSubviewModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.SubmissionPublisher;

public class TourListSubviewController implements Initializable {

    @FXML
    public VBox tourListButtons;

    @FXML
    TextField searchBox;

    @FXML
    public ListView<String> tours;
    private Map<Integer, Tour> tourMap = new HashMap<>();
    private TourListSubviewModel tourListSubviewModel;
    private SubmissionPublisher<Map<Tour, Image>> publisher;
    private Map<Tour, Image> currentlySelected = new HashMap<>();
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private List<String> tourNames = new ArrayList<>();
    public TourListSubviewController(TourListSubviewModel tourListSubviewModel, SubmissionPublisher<Map<Tour, Image>> publisher) {
        this.tourListSubviewModel = tourListSubviewModel;
        this.publisher = publisher;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeFieldSearchable();
       // reloadList();
        //load all tours and create a TourListItemModel for each tour
        tourListSubviewModel.getTours().forEach(tour -> {
            tours.getItems().add(String.valueOf(tour.getTour_id()) +
                    ": " +
                    tour.getName());
            tourMap.put(tour.getTour_id(), tour);
            tourNames.add(tour.getName());
        });
    }

    private void reloadList() {
        tourNames.clear();

        // https://stackoverflow.com/questions/17850191/why-am-i-getting-java-lang-illegalstateexception-not-on-fx-application-thread
        // runLater to prevent illegal state exception
        Platform.runLater(
            () -> {
                tours.getItems().clear();
                tourMap.clear();
                //load all tours
                List<Tour> list = tourListSubviewModel.getTours();
                for (Tour tour : list) {
                    tours.getItems().add(String.valueOf(tour.getTour_id()) +
                        ": " +
                        tour.getName());
                    tourMap.put(tour.getTour_id(), tour);
                    tourNames.add(tour.getName());
                }
            }
        );
    }

    private void updateList(Tour newTour) {
        tourNames.add(newTour.getName());
        Platform.runLater(
            () -> {
                tours.getItems().add(String.valueOf(newTour.getTour_id()) +
                        ": " +
                        newTour.getName());
            });
        tourMap.put(newTour.getTour_id(), newTour);
    }

    @FXML public void handleMouseClick(MouseEvent mouseEvent) {
        String selected = tours.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        Integer selectedId = Integer.valueOf(selected.split(":")[0]);
        currentlySelected.clear();
        currentlySelected.put(tourMap.get(selectedId), null);
        publisher.submit(currentlySelected);
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        currentlySelected = item;
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();
        if (tour == null) {
            tours.getSelectionModel().clearSelection();
            reloadList(); // something might have been deleted
        }
        else if (!tourMap.containsKey(tour.getTour_id())) {
            updateList(currentlySelected.entrySet().iterator().next().getKey());
        }
        else { // something was updated -> reload list
            for (Map.Entry<Integer, Tour> entry : tourMap.entrySet()) {
                if (entry.getKey() == tour.getTour_id()) {
                    if (!Objects.equals(entry.getValue().getName(), tour.getName())) {
                        reloadList();
                        break; // can't update more than one item at a time
                    }
                }
            }
        }
    }

    public void createSummary(javafx.event.ActionEvent actionEvent) {
        this.tourListSubviewModel.createSummary();
    }

    public void importData(ActionEvent actionEvent) {
        this.tourListSubviewModel.importTourData();
        reloadList();
    }

    public void exportData(ActionEvent actionEvent) {
        if(currentlySelected == null) {
            return;
        } else if (currentlySelected.isEmpty()) {
            return;
        }
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();

        if(tour != null){
            this.tourListSubviewModel.exportTourData(tour);
        }
    }

    private void makeFieldSearchable(){
        searchBox.setOnKeyReleased(event -> {
                String search = searchBox.getText();

                tours.getItems().clear();
                List<Tour> list = tourListSubviewModel.getTours();
                for (Tour tour : list) {

                    String currentNameAndId = String.valueOf(tour.getTour_id()) +
                            ": " +
                            tour.getName();

                    if(currentNameAndId.toLowerCase().contains(search.toLowerCase())){
                        tours.getItems().add(String.valueOf(tour.getTour_id()) +
                                ": " +
                                tour.getName());
                        tourMap.put(tour.getTour_id(), tour);
                    }
                }
        });
    }
}

