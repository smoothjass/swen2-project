package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.SybaseSqmToSqlAstConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
    public ObjectProperty<Image> imageView = new SimpleObjectProperty<>();

    private DatabaseService databaseService = new DatabaseService();
    private MapService mapService = new MapService();

    public Map<Tour, Image> saveTour() {
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
        // TODO convert the time to a reasonable format to display
        this.getDistance().setValue(Float.toString(tour.getDistance()) + " km");
        this.getDuration().setValue(Integer.toString(tour.getDuration()) + " seconds");
        Image image = convertToFxImage((BufferedImage) array[2]);
        this.getImageView().setValue(image);

        // return the tour so it can be updated whereever needed
        Map<Tour, Image> tourImageMap = new HashMap<>();
        tourImageMap.put(tour, image);
        return tourImageMap;
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
}
