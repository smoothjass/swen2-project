package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourListSubviewModel {
    private DatabaseService databaseService = new DatabaseService();

    public List<Tour> getTours() {
        return databaseService.getTours();
    }
}
