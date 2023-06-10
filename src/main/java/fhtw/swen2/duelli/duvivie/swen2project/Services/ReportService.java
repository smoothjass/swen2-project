package fhtw.swen2.duelli.duvivie.swen2project.Services;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import com.itextpdf.layout.Document;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.itextpdf.layout.properties.AreaBreakType.NEXT_PAGE;

public class ReportService {

    public ReportService() {}
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private static final LoadingSpinnerService loadingSpinnerService = new LoadingSpinnerService();

    public void createSingleReport(Tour tour, List<Log> logs) throws IOException {
        loadingSpinnerService.showSpinnerWindow();
        String fileName = tour.getName() + "-Report.pdf";
        String DEST = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/generatedReports/singleReports/" + fileName;

        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph tourInfo = new Paragraph("Tour: " + tour.getName())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK);

        document.add(tourInfo);

        int days = tour.getDuration()/ 86400;
        int hours = (tour.getDuration() % 86400 ) / 3600 ;
        int minutes = ((tour.getDuration() % 86400 ) % 3600 ) / 60 ;

        String time = days + " days " + hours + " hours " + minutes + " minutes";

        String childFriendliness = calculateChildFriendliness(logs);
        String avgRating = calculateAvgRating(logs);

        String tourData = "Description: " + tour.getDescription()
                + "\nFrom: " + tour.getFrom()
                + "\nTo: " + tour.getTo()
                + "\nTransport Type: " + tour.getTransportType().getType()
                + "\nDistance in km: " + tour.getDistance()
                + "\nDuration: " + time
                + "\nChild Friendliness: " + childFriendliness
                + "\nAverage Rating: " + avgRating;

        document.add(new Paragraph(tourData));

        Paragraph imageHeader = new Paragraph("Tour Map:")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(imageHeader);
        Object[] array = new Object[3];
        try {
            MapService mapService = new MapService();
            array = mapService.getRoute(tour.from, tour.to, tour.transportType.getType());
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            System.out.println("An error occurred while getting the route from " + tour.from + " to " + tour.to);
            System.out.println(e.getMessage());
            logger.error("An error occurred while getting the route from " + tour.from + " to " + tour.to + " " + e.getMessage());
            throw new RuntimeException(e);
        }
        com.itextpdf.layout.element.Image pdfImage = new com.itextpdf.layout.element.Image(ImageDataFactory.create((BufferedImage) array[2], null));
        document.add(pdfImage);
        document.add(new AreaBreak(NEXT_PAGE));

        Paragraph tableHeader = new Paragraph("Log entries: ")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(tableHeader);

        if(logs.isEmpty()) {
            document.add(new Paragraph("No log entries available"));
        } else {
            Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
            table.addHeaderCell("Starting Time");
            table.addHeaderCell("Comment");
            table.addHeaderCell("Difficulty");
            table.addHeaderCell("Total Time");
            table.addHeaderCell("Rating 1-5");
            table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);

            //for each log entry add a row to the table
            for (Log log : logs) {

                int log_days = (log.getTotal_time() / 86400);
                int log_hours = ((log.getTotal_time() % 86400 ) / 3600);
                int log_minutes = (((log.getTotal_time() % 86400 ) % 3600 ) / 60);

                String log_time = log_days + " days " + log_hours + " hours " + log_minutes + " minutes";

                table.addCell(log.getStarting_time().toString());
                table.addCell(log.getComment());
                table.addCell(String.valueOf(log.getDifficulty()));
                table.addCell(log_time);
                table.addCell(String.valueOf(log.getRating()));
            }
            document.add(table);
        }
        document.close();

        loadingSpinnerService.hideSpinnerWindow();

        // Open the save dialog window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.setInitialFileName(fileName);
        File file = fileChooser.showSaveDialog(null);

        // Move the generated PDF file to the chosen location
        if (file != null) {
            File generatedFile = new File(DEST);
            generatedFile.renameTo(file);
        }

        //delete the generated file
        File generatedFile = new File(DEST);
        generatedFile.delete();
    }

    public String calculateAvgRating(List<Log> logs) {
        Float avgRating = 0.0F;
        for (Log log : logs) {
            avgRating = avgRating + log.getRating();
        }
        if (logs.size() != 0){
            avgRating = avgRating / logs.size();
        }
        if (avgRating != 0.0) {
            return String.valueOf(avgRating);
        }
        else { // no logs
            return "cannot calculate without logs";
        }
    }

    public String calculateChildFriendliness(List<Log> logs) {
        Float avgDifficulty = 0.0F;
        for (Log log : logs) {
            avgDifficulty = avgDifficulty + log.getDifficulty();
        }
        avgDifficulty = avgDifficulty / logs.size();
        if (avgDifficulty > 0 && avgDifficulty <= 1) {
            return "very good";
        }
        else if (avgDifficulty > 1 && avgDifficulty <= 2.5){
            return "ok";
        }
        else if (avgDifficulty > 2.5 && avgDifficulty <= 4){
            return "probably not suitable for children";
        }
        else if (avgDifficulty > 4){
            return "not suitable for children";
        }
        else {
            return "cannot calculate without logs";
        }
    }

    public void createSummaryReport(List<Tour> tours, List<Log> logs) throws IOException {

        String fileName = "Summary-Report.pdf";
        String DEST = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/generatedReports/summaryReports/" + fileName;

        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph summaryHeader = new Paragraph("Summary Report: ")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(summaryHeader);

        // for each tour save the average time, -distance and rating over all associated tour-logs to the summary report
        for (Tour tour : tours) {
            Paragraph tourInfo = new Paragraph("Tour: " + tour.getName())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);

            document.add(tourInfo);

            String tourData = "Description: " + tour.getDescription()
                    + "\nFrom: " + tour.getFrom()
                    + "\nTo: " + tour.getTo()
                    + "\nTransport Type: " + tour.getTransportType().getType()
                    + "\nDistance in km: " + tour.getDistance()
                    + "\nDuration: " + tour.getDuration();

            document.add(new Paragraph(tourData));

            Paragraph tableHeader = new Paragraph("Log statistics: ")
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(tableHeader);


            List<Log> tourLogs = new ArrayList<>();
            //find all logs associated with the current tour in the logList and save them in a list and then remove them from the logList
            for (Log log : logs) {
                if (log.getTour_id() == tour.getTour_id()) {
                    tourLogs.add(log);
                }
            }

            if(!tourLogs.isEmpty()){

                // calculate the average time, -distance and rating over all associated tour-logs
                double averageTime = 0;
                double averageDistance = 0;
                double averageRating = 0;

                for (Log log : tourLogs) {
                    averageTime += log.getTotal_time();
                    averageDistance += tour.getDistance();
                    averageRating += log.getRating();
                }

                averageTime = averageTime / tourLogs.size();
                averageDistance = averageDistance / tourLogs.size();
                averageRating = averageRating / tourLogs.size();

                int days = (int) (averageTime / 86400);
                int hours = (int) ((averageTime % 86400 ) / 3600);
                int minutes = (int) (((averageTime % 86400 ) % 3600 ) / 60);

                String averageTimeString = days + " days " + hours + " hours " + minutes + " minutes";

                // add the calculated values to the summary report
                Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
                table.addHeaderCell("Average duration");
                table.addHeaderCell("Average distance in km");
                table.addHeaderCell("Average rating");
                table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
                table.addCell(averageTimeString);
                table.addCell(String.valueOf(averageDistance));
                table.addCell(String.valueOf(averageRating));
                document.add(table);
                }
            else {
                    document.add(new Paragraph("No logs found for this tour!"));

            }

            //if the are still tours left add a page break
            if(tours.indexOf(tour) != tours.size()-1){
                document.add(new AreaBreak(NEXT_PAGE));
            }
        }

        document.close();

        // Open the save dialog window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.setInitialFileName(fileName);
        File file = fileChooser.showSaveDialog(null);

        // Move the generated PDF file to the chosen location
        if (file != null) {
            File generatedFile = new File(DEST);
            generatedFile.renameTo(file);
        }

        //delete the generated file
        File generatedFile = new File(DEST);
        generatedFile.delete();
    }
}
