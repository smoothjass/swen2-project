package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.LogViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import java.util.concurrent.SubmissionPublisher;

public class LogViewController implements Initializable {
    public ImageView imageView;
    private LogViewModel logViewModel;
    private Map<Tour, Image> currentlySelected = new HashMap<>();
    private Log currentlySelectedLog = null;
    private Map<Integer, Log> logMap = new HashMap<>();

    public ListView<String> logs;
    public TextArea comment;
    public ChoiceBox<String> difficulty;
    public ChoiceBox<String> rating;
    public TextField days;

    public TextField hours;
    public TextField minutes;

    public Label timestamp;
    private SubmissionPublisher<Map<Tour, Image>> publisher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comment.textProperty().bindBidirectional(logViewModel.getComment());
        this.difficulty.valueProperty().bindBidirectional(logViewModel.getDifficulty());
        this.rating.valueProperty().bindBidirectional(logViewModel.getRating());
        this.days.textProperty().bindBidirectional(logViewModel.getDays());
        this.hours.textProperty().bindBidirectional(logViewModel.getHours());
        this.minutes.textProperty().bindBidirectional(logViewModel.getMinutes());
        this.timestamp.textProperty().bindBidirectional(logViewModel.getTimestamp());
    }

    public LogViewController(LogViewModel logViewModel, SubmissionPublisher<Map<Tour, Image>> publisher) {
        this.logViewModel = logViewModel;
        this.publisher = publisher;
    }

    public void loadLogs(Integer tour_id){
        Platform.runLater(
            () -> {
                logs.getItems().clear();

                List<Log> logsList = logViewModel.getLogs(tour_id);

                if(logsList.isEmpty()){
                    return;
                }

                //sort logs by id
                logsList.sort(Comparator.comparingInt(o -> o.log_id));

                logsList.forEach(log -> {
                    logs.getItems().add((log.log_id) +
                            ": " +
                            log.comment);
                    logMap.put(log.getLog_id(), log);
                });
            });
    }

    public void clearLogs(){
        Platform.runLater(
            () -> {
                logs.getItems().clear();
                logMap.clear();
            });
    }

    public void clearForm(){
        Platform.runLater(
            () -> {
                comment.clear();
                difficulty.getSelectionModel().clearSelection();
                rating.getSelectionModel().clearSelection();
                days.clear();
                hours.clear();
                minutes.clear();
                timestamp.setText("");
            });
    }

    private void updateImage(Image image) {
        imageView.setImage(image);
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        clearForm();
        currentlySelected = item;
        Image image = currentlySelected.entrySet().iterator().next().getValue();
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();
        updateImage(image);
        if(tour == null) {
            currentlySelectedLog = null;
            clearLogs();
            clearForm();
        }
        else{
            loadLogs(tour.getTour_id());
        }
    }

    public void saveLog(ActionEvent actionEvent) {
        if(!currentlySelected.isEmpty()) {
            Tour tour = currentlySelected.entrySet().iterator().next().getKey();
            if(currentlySelectedLog == null) {
               currentlySelectedLog = logViewModel.addLog(tour.getTour_id());
               loadLogs(tour.getTour_id());
            }
            else{
                currentlySelectedLog = logViewModel.updateLog(currentlySelectedLog);
                loadLogs(tour.getTour_id());
            }
        }
    }

    @FXML public void handleMouseClick(MouseEvent mouseEvent) {
        String selected = logs.getSelectionModel().getSelectedItem();
        Integer selectedId = Integer.valueOf(selected.split(":")[0]);
        Log selectedLog = logMap.get(selectedId);
        this.logViewModel.setInputs(selectedLog);
        currentlySelectedLog = selectedLog;
    }

    public void deleteLog(ActionEvent actionEvent) {
       if(!currentlySelected.isEmpty()) {
           Tour tour = currentlySelected.entrySet().iterator().next().getKey();
           if(currentlySelectedLog != null) {
               this.logViewModel.deleteLog(currentlySelectedLog.log_id);
               clearLogs();
               clearForm();
               currentlySelectedLog = null;
               loadLogs(tour.getTour_id());
           }
       }
    }

    public void newLog(ActionEvent actionEvent) {
        clearForm();
        currentlySelectedLog = null;
    }
}
