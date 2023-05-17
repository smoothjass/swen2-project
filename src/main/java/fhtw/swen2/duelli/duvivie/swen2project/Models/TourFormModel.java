package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class TourFormModel {
    public StringProperty name = new SimpleStringProperty();
    public StringProperty distance = new SimpleStringProperty();
    public StringProperty duration = new SimpleStringProperty();
    public StringProperty to = new SimpleStringProperty();
    public StringProperty from = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    //public  transportType = new SimpleStringProperty();

}
