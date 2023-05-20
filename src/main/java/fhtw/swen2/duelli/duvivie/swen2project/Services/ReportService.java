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

import java.io.IOException;
import java.util.ArrayList;

import static com.itextpdf.layout.properties.AreaBreakType.NEXT_PAGE;

public class ReportService {

    private String mapImagesPath;

    public ReportService() {
        mapImagesPath = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/mapImages/";
    }

    public String genereateSingleTourPdf(Tour tour, ArrayList<Log> logs, String mapFileName) throws IOException {

        String fileName = tour.getName() + "-Report.pdf";
        String DEST = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/generatedReports/singleReports/"+fileName;

        PdfWriter writer = new PdfWriter(DEST);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

         Paragraph loremIpsumHeader = new Paragraph("Tour: " + tour.getName())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK);

        document.add(loremIpsumHeader);

        String tourData  = "Description: " + tour.getDescription()
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
        ImageData imageData = ImageDataFactory.create(mapImagesPath+mapFileName);
        Image image = new Image(imageData);
        document.add(image);

        document.add(new AreaBreak(NEXT_PAGE));

        Paragraph tableHeader = new Paragraph("Log entries: ")
                .setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN))
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.GREEN);
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

        //return the path to the generated pdf
        return DEST;
    }

    public String createSummaryReport(){
        return "";
    }
}
