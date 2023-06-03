package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
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

    public TourListSubviewModel() {
        this.databaseService = new DatabaseService();
        this.reportService = new ReportService();
        this.TXTService = new TXTService();
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

    public void importTourData() {
        Map<Tour, List<Log>> data = null;

        try {
            data = TXTService.importData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Tour currentTour = data.entrySet().iterator().next().getKey();
        List<Log> newLogs = data.entrySet().iterator().next().getValue();

        if(currentTour != null){
            final Tour newTour = databaseService.saveTour(currentTour);
            if(!newLogs.isEmpty()){
                newLogs.forEach( log -> {
                    if (log != null){
                        log.setTour_id(newTour.getTour_id());
                        databaseService.saveLog(log);
                    }
                });
            }
        }
    }

    public void exportTourData(Tour tour){
        List<Log> logs = databaseService.getAllLogsForTour(tour);
        try {
            TXTService.export(tour, logs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
