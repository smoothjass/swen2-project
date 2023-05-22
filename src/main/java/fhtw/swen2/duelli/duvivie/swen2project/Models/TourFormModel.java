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

    public void saveTour() {
        // TODO invoke MapService to get distance, duration and picture
        // return type for getRoute?
        try {
            mapService.getRoute(from.getValue(), to.getValue(), transportType.getValue());
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        // for now hardcoded
        Float tempDistance = 0.0F;
        Integer tempDuration = 0;
        /*
        // debug messages
        System.out.println(name);
        System.out.println(distance);
        System.out.println(duration);
        System.out.println(to);
        System.out.println(from);
        System.out.println(description);
        System.out.println(transportType);
        */

        // build entity
        Tour newTour = new Tour();
        newTour.setName(name.getValue());
        newTour.setDistance(tempDistance);
        newTour.setDuration(tempDuration);
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
        databaseService.saveTour(newTour);
    }
}
