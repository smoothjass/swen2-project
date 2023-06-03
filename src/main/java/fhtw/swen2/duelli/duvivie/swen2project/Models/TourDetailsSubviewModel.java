package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.ReportService;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.io.IOException;

public class TourDetailsSubviewModel {

    private DatabaseService databaseService;
    private ReportService reportService;

    public TourDetailsSubviewModel() {
        this.databaseService = new DatabaseService();
        this.reportService = new ReportService();
    }

    public void createSingleReport(Tour currentlySelectedTour, Image image) {
        try {
            reportService.createSingleReport(currentlySelectedTour, databaseService.getAllLogsForTour(currentlySelectedTour));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCurrentTour(Tour currentlySelectedTour) {
        databaseService.deleteTour(currentlySelectedTour);
    }
}
