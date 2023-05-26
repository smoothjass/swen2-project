package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.SybaseSqmToSqlAstConverter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

    private DatabaseService databaseService = new DatabaseService();
    private MapService mapService = new MapService();

    public Tour saveTour() {
        // invoke MapService to get distance, duration and picture
        Object[] array = new Object[3];
        try {
            array = mapService.getRoute(from.getValue(), to.getValue(), transportType.getValue());
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // build entity
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
        // TODO update currently selected id
        this.getDistance().setValue(Float.toString(tour.getDistance()) + " km");
        this.getDuration().setValue(Integer.toString(tour.getDuration()) + " seconds");
        return tour;
    }
}
