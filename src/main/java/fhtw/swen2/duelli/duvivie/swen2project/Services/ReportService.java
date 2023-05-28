package fhtw.swen2.duelli.duvivie.swen2project.Services;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import com.itextpdf.layout.Document;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.itextpdf.layout.properties.AreaBreakType.NEXT_PAGE;

public class ReportService {

    public ReportService() {}

    public void genereateSingleTourPdf(Tour tour, ArrayList<Log> logs, Image image) throws IOException {

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

        String tourData = "Description: " + tour.getDescription()
                + "\nFrom: " + tour.getFrom()
                + "\nTo: " + tour.getTo()
                + "\nTransport Type: " + tour.getTransportType().getType()
                + "\nDistance: " + tour.getDistance()
                + "\nDuration: " + tour.getDuration();

        document.add(new Paragraph(tourData));

        Paragraph imageHeader = new Paragraph("Tour Map:")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(imageHeader);
        document.add(image);

        document.add(new AreaBreak(NEXT_PAGE));

        Paragraph tableHeader = new Paragraph("Log entries: ")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(tableHeader);

        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        table.addHeaderCell("Starting Time");
        table.addHeaderCell("Comment");
        table.addHeaderCell("Difficulty");
        table.addHeaderCell("Total Time");
        table.addHeaderCell("Rating 1-5");
        table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);

        //for each log entry add a row to the table
        for (Log log : logs) {
            table.addCell(log.getStarting_time().toString());
            table.addCell(log.getComment());
            table.addCell(String.valueOf(log.getDifficulty()));
            table.addCell(String.valueOf(log.getTotal_time()));
            table.addCell(String.valueOf(log.getRating()));
        }

        document.add(table);
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

    public void createSummaryReport(List<Tour> tours, List<Log> logs) throws IOException {

        String fileName = "Summary-Report.pdf";
        String DEST = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/generatedReports/summaryReports/" + fileName;

        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        List<Log> logList = logs;

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
                    + "\nDistance: " + tour.getDistance()
                    + "\nDuration: " + tour.getDuration();

            document.add(new Paragraph(tourData));

            Paragraph tableHeader = new Paragraph("Log statistics: ")
                    .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                    .setFontSize(18)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(tableHeader);

            //find all logs associated with the current tour in the logList and save them in a list and then remove them from the logList
            List<Log> tourLogs = new ArrayList<>();
            for (Log log : logList) {
                if (log.tour_id == tour.getTour_id()) {
                    tourLogs.add(log);
                }
            }
            logList.removeAll(tourLogs);

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

            // add the calculated values to the summary report
            Table table = new Table(UnitValue.createPercentArray(3)).useAllAvailableWidth();
            table.addHeaderCell("Average Time");
            table.addHeaderCell("Average Distance");
            table.addHeaderCell("Average Rating");
            table.setFontSize(14).setBackgroundColor(ColorConstants.WHITE);
            table.addCell(String.valueOf(averageTime));
            table.addCell(String.valueOf(averageDistance));
            table.addCell(String.valueOf(averageRating));
            document.add(table);

            document.add(new AreaBreak(NEXT_PAGE));
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
