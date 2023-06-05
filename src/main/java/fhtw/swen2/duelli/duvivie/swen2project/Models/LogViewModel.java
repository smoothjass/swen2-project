package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.List;

@Getter
@Setter
public class LogViewModel {
    public StringProperty comment = new SimpleStringProperty();
    public StringProperty difficulty = new SimpleStringProperty();
    public StringProperty rating = new SimpleStringProperty();
    public StringProperty days = new SimpleStringProperty();
    public StringProperty hours = new SimpleStringProperty();
    public StringProperty minutes = new SimpleStringProperty();
    public StringProperty timestamp = new SimpleStringProperty();

    private DatabaseService databaseService;
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public LogViewModel() {
        this.databaseService = new DatabaseService();
    }
    public List<Log> getLogs(Integer tour_id) {
        try {
            return databaseService.getLogsForTour(tour_id);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the logs for tour with id " + tour_id);
            System.out.println(e.getMessage());
            logger.error("An error occurred while fetching the logs for tour with id " + tour_id + " " + e.getMessage());
        }
        return null;
    }

    private void alert(String titleBar, String headerMessage, String infoMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);

        alert.showAndWait();
    }

    public void setInputs(Log log){

        //calculate the number of days, hours and minutes from the total time (seconds)
        int days = log.getTotal_time()/ 86400;
        int hours = (log.getTotal_time() % 86400 ) / 3600 ;
        int minutes = ((log.getTotal_time() % 86400 ) % 3600 ) / 60 ;

        this.comment.setValue(log.getComment());
        this.difficulty.setValue(String.valueOf(log.getDifficulty()));
        this.rating.setValue(String.valueOf(log.getRating()));
        this.days.setValue(String.valueOf(days));
        this.hours.setValue(String.valueOf(hours));
        this.minutes.setValue(String.valueOf(minutes));
        this.timestamp.setValue(String.valueOf(log.getStarting_time()));
    }

    public Boolean validateInput(Integer tour_id){
        if(this.comment.getValue() == null|| this.difficulty.getValue() == null || this.rating.getValue() == null || this.days.getValue() == null || this.hours.getValue() == null|| this.minutes.getValue() == null){
            alert("Error", "Please fill out all fields", "Please fill out all fields");
            logger.warn("Input validation failed while adding a new log entry for tour with id " + tour_id + " because one or more fields were null");
            return false;
        }
        //check iif comment, days, hours and minutes do not contain any empty srings
        else if(this.comment.getValue().isBlank() || this.difficulty.getValue().isBlank() || this.rating.getValue().isBlank()|| this.days.getValue().isBlank() || this.hours.getValue().isBlank()|| this.minutes.getValue().isBlank()){
            alert("Error", "Please fill out all fields", "Please fill out all fields");
            logger.warn("Input validation failed while adding a new log entry for tour with id " + tour_id + " because one or more fields were empty");
            return false;
        }
        //check if days, hours and minutes contains only numbers and not any other caracters
        else if(!days.getValue().matches("[0-9]+") || !hours.getValue().matches("[0-9]+") || !minutes.getValue().matches("[0-9]+")){
            alert("Error", "Time must be a number", "Time must be a number");
            logger.warn("Input validation failed while adding a new log entry for tour with id " + tour_id + " because time was not a number");
            return false;
        }
        else if(Integer.parseInt(String.valueOf(this.days.getValue())) < 0 || Integer.parseInt(String.valueOf(this.hours.getValue())) < 0 || Integer.parseInt(String.valueOf(this.minutes.getValue())) < 0){
            alert("Error", "Time must be positive", "Time must be positive");
            logger.warn("Input validation failed while adding a new log entry for tour with id " + tour_id + " because time was negative");
            return false;
        }
        return true;
    }

    public Log addLog(Integer tour_id) {
        if(validateInput(tour_id)){
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            timestamp.setValue(currentTimestamp.toString());

            Log log = new Log();
            log.setTour_id(tour_id);
            log.setComment(this.comment.getValue());
            log.setDifficulty(Integer.parseInt(this.difficulty.getValue()));
            log.setRating(Integer.parseInt(this.rating.getValue()));
            log.setTotal_time(Integer.parseInt(this.days.getValue()) * 24 * 60 * 60 + Integer.parseInt(this.hours.getValue()) * 60 * 60 + Integer.parseInt(this.minutes.getValue())*60);
            log.setStarting_time(currentTimestamp);

            try {
                return databaseService.saveLog(log);
            } catch (Exception e) {
                System.out.println("An error occurred while saving the log");
                System.out.println(e.getMessage());
                logger.error("An error occurred while saving the log " + e.getMessage());
            }
        }
        return null;
    }

    public Log updateLog(Log log) {
        if(validateInput(log.getTour_id())){
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            //set timestamp to current time
            timestamp.setValue(currentTimestamp.toString());

            log.setComment(comment.getValue());
            log.setDifficulty(Integer.parseInt(difficulty.getValue()));
            log.setRating(Integer.parseInt(rating.getValue()));
            log.setTotal_time(Integer.parseInt(days.getValue()) * 24 * 60 * 60 + Integer.parseInt(hours.getValue()) * 60 * 60 + Integer.parseInt(minutes.getValue())*60);
            log.starting_time = currentTimestamp;

            try {
                return databaseService.updateLog(log);
            } catch (Exception e) {
                System.out.println("An error occurred while updating the log with id " + log.getLog_id());
                System.out.println(e.getMessage());
                logger.error("An error occurred while updating the log with id " + log.getLog_id() + " " + e.getMessage());
            }
        }
        return null;
    }

    public void deleteLog(Integer log_id) {
        try {
            databaseService.deleteLog(log_id);
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the log with id " + log_id);
            System.out.println(e.getMessage());
            logger.error("An error occurred while deleting the log with id " + log_id + " " + e.getMessage());
        }
    }
}
