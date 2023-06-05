package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Getter
@Setter
public class TourFormModel {
    public StringProperty name = new SimpleStringProperty();
    public StringProperty distance = new SimpleStringProperty();
    public StringProperty duration = new SimpleStringProperty();
    public StringProperty to = new SimpleStringProperty();
    public StringProperty from = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    public StringProperty transportType = new SimpleStringProperty();
    public StringProperty childFriendliness = new SimpleStringProperty();
    public StringProperty popularity = new SimpleStringProperty();
    public ObjectProperty<Image> imageView = new SimpleObjectProperty<>();

    private DatabaseService databaseService = new DatabaseService();
    private MapService mapService = new MapService();
    public Map<Tour, Image> saveTour() {
        if (validateInput()) {
            // invoke MapService to get distance, duration and picture
            Object[] array = new Object[3];
            try {
                array = mapService.getRoute(from.getValue(), to.getValue(), transportType.getValue());
            } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            // build entity for db
            Tour newTour = new Tour();
            newTour.setName(name.getValue());
            newTour.setDistance((Float) array[0]);
            newTour.setDuration((Integer) array[1]);
            newTour.setTo(to.getValue());
            newTour.setFrom(from.getValue());
            newTour.setDescription(description.getValue());
            TransportType newTransportType = new TransportType();
            newTransportType.setType(transportType.getValue());
            switch (newTransportType.getType()) {
                case "bicycle" -> newTransportType.setTransport_type_id(1);
                case "car" -> newTransportType.setTransport_type_id(2);
                case "pedestrian" -> newTransportType.setTransport_type_id(3);
            }
            newTour.setTransportType(newTransportType);
            System.out.println(newTour.getTransportType().getTransport_type_id());
            System.out.println(newTour.getTransportType().getType());

            // give to database service
            Tour tour = databaseService.saveTour(newTour);

            // update bound values
            this.getDistance().setValue(Float.toString(tour.getDistance()) + " km");
            //calculate the number of days, hours and minutes from the duration (seconds)
            calculateDuration(tour);
            Image image = convertToFxImage((BufferedImage) array[2]);
            this.getImageView().setValue(image);

            // return the tour so it can be updated whereever needed
            Map<Tour, Image> tourImageMap = new HashMap<>();
            tourImageMap.put(tour, image);
            return tourImageMap;
        }
        return null;
    }

    private static Image convertToFxImage(BufferedImage image) {
        // https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    public Image requestImage(Tour tour) {
        Object[] array = new Object[3];
        try {
            array = mapService.getRoute(tour.from, tour.to, tour.transportType.getType());
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return convertToFxImage((BufferedImage) array[2]);
    }

    public Map<Tour, Image> updateTour(int tour_id) {
        if (validateInput()) {

        }
        return null;
        // TODO
        //  validate input
        //  fetch tour with id from db
        //  compare
        //  if to/from/transporttype changed >> request new directions and image, update in db, return new tour and new image
        //  if something else changed >> only update in db, return new tour and null image (image will be reset to the old one in the controller)
    }

    public void calculateValues(Tour tour) {
        // TODO calculate comupted values
        // id needed because we need to get the logs from the db
        List<Log> list = databaseService.getAllLogsForTour(tour);
        Float avgDifficulty = 0.0F;
        for (Log log : list) {
            avgDifficulty = avgDifficulty + log.getDifficulty();
        }
        avgDifficulty = avgDifficulty / list.size();
        if (avgDifficulty > 0 && avgDifficulty <= 1) {
            this.getChildFriendliness().setValue("very good");
        }
        else if (avgDifficulty > 1 && avgDifficulty <= 2.5){
            this.getChildFriendliness().setValue("ok");
        }
        else if (avgDifficulty > 2.5 && avgDifficulty <= 4){
            this.getChildFriendliness().setValue("probably not suitable for children");
        }
        else if (avgDifficulty > 4){
            this.getChildFriendliness().setValue("not suitable for children");
        }
        else {
            this.getChildFriendliness().setValue("cannot calculate at the moment");
        }
        this.getPopularity().setValue(String.valueOf(list.size())); // number of logs = popularity
        // TODO calculate more elaborate popularity value if there is some time
    }

    private Boolean validateInput() {
        if (this.getDescription().getValue() == null ||
                this.getTransportType().getValue() == null ||
                this.getFrom().getValue() == null ||
                this.getTo().getValue() == null ||
                this.getName().getValue() == null) {
            alert("Error", "Please fill out all fields", "Please fill out all fields");
            return false;
        }
        else if (this.getDescription().getValue().isBlank() ||
            this.getTransportType().getValue().isBlank() ||
            this.getFrom().getValue().isBlank() ||
            this.getTo().getValue().isBlank() ||
            this.getName().getValue().isBlank()) {
            alert("Error", "Please fill out all fields", "Please fill out all fields");
            return false;
        }
        return true;
    }

    private void alert(String titleBar, String headerMessage, String infoMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);

        alert.showAndWait();
    }

    public void autoFillTourData(Tour tour) {
        Platform.runLater(
            () -> {
                if (tour.getDistance() != null){
                    getDistance().setValue(String.valueOf(tour.distance));
                }
                else {
                    getDistance().setValue(null);
                }
                getName().setValue(tour.name);
                getTo().setValue(tour.to);
                getFrom().setValue(tour.from);
                getDescription().setValue(tour.description);
                if(tour.getTransportType() != null) {
                    getTransportType().setValue(tour.transportType.getType());
                }
                else {
                    getTransportType().setValue(null);
                }
                //calculate the number of days, hours and minutes from the duration (seconds)
                calculateDuration(tour);
                calculateValues(tour);
            });
    }

    private void calculateDuration(Tour tour) {
        if (tour.getDuration() != null){
            int days = tour.getDuration()/ 86400;
            int hours = (tour.getDuration() % 86400 ) / 3600 ;
            int minutes = ((tour.getDuration() % 86400 ) % 3600 ) / 60 ;
            this.getDuration().setValue(Integer.toString(days) + " days " +
                    Integer.toString(hours) + " hours " +
                    Integer.toString(minutes) + " minutes");
        }
        else {
            this.getDuration().setValue(null);
        }
    }
}
