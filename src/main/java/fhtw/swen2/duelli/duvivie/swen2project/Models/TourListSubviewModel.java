package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.ReportService;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class TourListSubviewModel {
    private DatabaseService databaseService;
    private ReportService reportService;

    public TourListSubviewModel() {
        this.databaseService = new DatabaseService();
        this.reportService = new ReportService();
    }

    public List<Tour> getTours() {
        return databaseService.getTours();
    }

    public void createSummary() {
        try {
            reportService.createSummaryReport(databaseService.getTours(), databaseService.getTourLogs());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
