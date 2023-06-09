package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Services.TXTService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.ReportService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TourListSubviewModel {
    private DatabaseService databaseService;
    private ReportService reportService;
    private TXTService TXTService;
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public TourListSubviewModel(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.reportService = new ReportService();
        this.TXTService = new TXTService();
    }

    public List<Tour> getTours() {
        try {
            return databaseService.getTours();
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the tours from the database");
            System.out.println(e.getMessage());
            logger.error("An error occurred while fetching the tours from the database " + e.getMessage());
        }
        return null;
    }

    public void createSummary() {
        try {
            reportService.createSummaryReport(databaseService.getTours(), databaseService.getTourLogs());
        } catch (Exception e) {
            System.out.println("An error occurred while creating the summary report");
            System.out.println(e.getMessage());
            logger.error("An error occurred while creating the summary report " + e.getMessage());
        }
    }

    public void importTourData() {
        Map<Tour, List<Log>> data = null;

        try {
            data = TXTService.importData();
        } catch (IOException e) {
            System.out.println("An error occurred while importing data");
            System.out.println(e.getMessage());
            logger.error("An error occurred while importing data" + e.getMessage());
            throw new RuntimeException(e);
        }

        Tour currentTour = data.entrySet().iterator().next().getKey();
        List<Log> newLogs = data.entrySet().iterator().next().getValue();

        if(currentTour != null){
            final Tour newTour;
            try {
                newTour = databaseService.saveTour(currentTour);
                logger.debug("Tour with id " + currentTour.getTour_id() + " was saved successfully");
            } catch (Exception e) {
                System.out.println("An error occurred while saving the tour with id " + currentTour.getTour_id());
                System.out.println(e.getMessage());
                logger.error("An error occurred while saving the tour with id " + currentTour.getTour_id() + " " + e.getMessage());
                return;
            }
            if(!newLogs.isEmpty()){
                newLogs.forEach( log -> {
                    if (log != null){
                        log.setTour_id(newTour.getTour_id());
                        try {
                            databaseService.saveLog(log);
                            logger.debug("Log with id " + log.getLog_id() + " was saved successfully");
                        } catch (Exception e) {
                            System.out.println("An error occurred while saving the log with id " + log.getLog_id());
                            System.out.println(e.getMessage());
                            logger.error("An error occurred while saving the log with id " + log.getLog_id() + " " + e.getMessage());
                        }
                    }
                });
            }
        }
    }

    public void exportTourData(Tour tour) {
        List<Log> logs = null;
        try {
            logs = databaseService.getAllLogsForTour(tour);
            logger.debug("Logs for tour with id " + tour.getTour_id() + " were fetched successfully");
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the logs for tour with id " + tour.getTour_id());
            System.out.println(e.getMessage());
            logger.error("An error occurred while fetching the logs for tour with id " + tour.getTour_id() + " " + e.getMessage());
        }
        try {
            TXTService.export(tour, logs);
        } catch (Exception e) {
            System.out.println("An error occurred while exporting the tour with id " + tour.getTour_id());
            System.out.println(e.getMessage());
            logger.error("An error occurred while exporting the tour with id " + tour.getTour_id() + " " + e.getMessage());
        }
    }
}
