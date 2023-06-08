package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
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
import java.util.Objects;
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
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public Map<Tour, Image> saveTour() {
        if (validateInput()) {
            // invoke MapService to get distance, duration and picture
            Object[] array = new Object[3];
            try {
                array = mapService.getRoute(from.getValue(), to.getValue(), transportType.getValue());
                if (array == null) {
                    alert("Error", "Cannot calculate this route", "Please review your input or try again later");
                    return null;
                }
                logger.debug("Successfully got the route from " + from.getValue() + " to " + to.getValue());

            } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
                System.out.println("An error occurred while getting the route from " + from.getValue() + " to " + to.getValue());
                System.out.println(e.getMessage());
                logger.error("An error occurred while getting the route from " + from.getValue() + " to " + to.getValue() + " " + e.getMessage());
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
            // System.out.println(newTour.getTransportType().getTransport_type_id());
            // System.out.println(newTour.getTransportType().getType());

        // give to database service
        Tour tour = null;
        try {
            tour = databaseService.saveTour(newTour);
            logger.debug("Tour saved to database.");
        } catch (Exception e) {
            System.out.println("An error occurred while saving the tour to the database.");
            System.out.println(e.getMessage());
            logger.error("An error occurred while saving the tour to the database:" + e.getMessage());
        }

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
            System.out.println("An error occurred while getting the route from " + from.getValue() + " to " + to.getValue());
            System.out.println(e.getMessage());
            logger.error("An error occurred while getting the route from " + from.getValue() + " to " + to.getValue() + " " + e.getMessage());
            throw new RuntimeException(e);
        }
        return convertToFxImage((BufferedImage) array[2]);
    }

    public Map<Tour, Image> updateTour(Tour tour) {
        // validate input
        if (validateInput()) {
            // get the old tour from the db
            Tour oldTour = databaseService.getTourById(tour.getTour_id());
            // build the updated tour from the inputs
            Tour newTour = new Tour();
            newTour.setTour_id(oldTour.getTour_id());
            newTour.setName(name.getValue());
            newTour.setTo(to.getValue());
            newTour.setDistance(Float.valueOf(distance.getValue()));
            newTour.setDuration(oldTour.getDuration());
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

            // compare
            // if to/from/transporttype changed >> request new directions and image, return new tour and new image
            if (!Objects.equals(oldTour.getTo(), newTour.getTo()) ||
                    !Objects.equals(oldTour.getFrom(), newTour.getFrom()) ||
                    !Objects.equals(oldTour.getTransportType().getType(), newTour.getTransportType().getType())) {
                // invoke MapService to get distance, duration and picture
                Object[] array = new Object[3];
                try {
                    array = mapService.getRoute(this.getFrom().getValue(), this.getTo().getValue(), this.getTransportType().getValue());
                    if (array == null) {
                        alert("Error", "Cannot calculate this route", "Please review your input or try again later");
                        return null;
                    }
                    // update bound values and tour values
                    newTour.setDistance((Float) array[0]);
                    newTour.setDuration((Integer) array[1]);
                    this.getDistance().setValue(Float.toString((Float) array[0]) + " km");
                    //calculate the number of days, hours and minutes from the duration (seconds)
                    calculateDuration(newTour);
                    Image image = convertToFxImage((BufferedImage) array[2]);
                    this.getImageView().setValue(image);

                    // update and get the updated tour
                    Tour updatedTour = databaseService.updateTour(newTour);

                    // return the tour so it can be updated whereever needed
                    Map<Tour, Image> tourImageMap = new HashMap<>();
                    tourImageMap.put(updatedTour, image);
                    return tourImageMap;
                } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
                    System.out.println("An error occurred while getting the route from " + from.getValue() + " to " + to.getValue());
                    System.out.println(e.getMessage());
                    logger.error("An error occurred while getting the route from " + from.getValue() + " to " + to.getValue() + " " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            // if something else changed >> only update in db, return new tour and null image (image will be reset to the old one in the controller)
            else {
                // update and get the updated tour
                Tour updatedTour = databaseService.updateTour(newTour);

                // return the tour so it can be updated whereever needed
                Map<Tour, Image> tourImageMap = new HashMap<>();
                tourImageMap.put(updatedTour, null);
                return tourImageMap;
            }
        }
        return null;
    }

    public void calculateValues(Tour tour) {
        // id needed because we need to get the logs from the db
        List<Log> list = null;
        try {
            list = databaseService.getAllLogsForTour(tour);
            logger.debug("Logs fetched from database for tour with id " + tour.getTour_id() + ".");
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the logs from the database.");
            System.out.println(e.getMessage());
            logger.error("An error occurred while fetching the logs from the database:" + e.getMessage());
        }
        Float avgDifficulty = 0.0F;
        Float avgRating = 0.0F;
        for (Log log : list) {
            avgDifficulty = avgDifficulty + log.getDifficulty();
            avgRating = avgRating + log.getRating();
        }
        avgDifficulty = avgDifficulty / list.size();
        avgRating = avgRating / list.size();
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
        this.getPopularity().setValue(String.valueOf(avgRating)); // average rating = popularity
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

    public void calculateDuration(Tour tour) {
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
