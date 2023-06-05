package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.ReportService;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.io.IOException;

public class TourDetailsSubviewModel {

    private DatabaseService databaseService;
    private ReportService reportService;
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public TourDetailsSubviewModel() {
        this.databaseService = new DatabaseService();
        this.reportService = new ReportService();
    }

    public void createSingleReport(Tour currentlySelectedTour, Image image) {
        try {
            reportService.createSingleReport(currentlySelectedTour, databaseService.getAllLogsForTour(currentlySelectedTour));
            logger.debug("Created report for tour with id " + currentlySelectedTour.getTour_id());
        } catch (Exception e) {
            System.out.println("An error occurred while creating the report for tour with id " + currentlySelectedTour.getTour_id());
            System.out.println(e.getMessage());
            logger.error("An error occurred while creating the report for tour with id " + currentlySelectedTour.getTour_id() + " " + e.getMessage());
        }
    }

    public void deleteCurrentTour(Tour currentlySelectedTour) {
        try {
            databaseService.deleteTour(currentlySelectedTour);
            logger.debug("Deleted tour with id " + currentlySelectedTour.getTour_id());
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the tour with id " + currentlySelectedTour.getTour_id());
            System.out.println(e.getMessage());
            logger.error("An error occurred while deleting the tour with id " + currentlySelectedTour.getTour_id() + " " + e.getMessage());
        }
    }
}
