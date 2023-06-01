package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TourListItemModel {

    public StringProperty name = new SimpleStringProperty();
    public IntegerProperty id = new SimpleIntegerProperty();
}
