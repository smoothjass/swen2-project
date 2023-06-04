package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import javafx.stage.FileChooser;

import java.sql.Timestamp;
import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class TXTService {
    public void export(Tour tour, List<Log> logs) throws IOException {
            String fileName = tour.getName() + "-Export.txt";
            String DEST = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/generatedCSVs/" + fileName;

            FileWriter writer = new FileWriter(DEST);
            writer.append("Tour: " + tour.getName());
            writer.append("\nDescription: " + tour.getDescription());
            writer.append("\nFrom: " + tour.getFrom());
            writer.append("\nTo: " + tour.getTo());
            writer.append("\nTransport Type: " + tour.getTransportType().getType());
            writer.append("\nDistance: " + tour.getDistance());
            writer.append("\nDuration: " + tour.getDuration());
            writer.append("\n");
            writer.append("\n");

            //append each log entry to the csv file
            if(logs != null && !logs.isEmpty()) {
                for (Log log : logs) {
                    if (log != null) {
                        writer.append("\nStarting_time: " + log.getStarting_time());
                        writer.append("\nComment: " + log.getComment());
                        writer.append("\nDifficulty: " + log.getDifficulty());
                        writer.append("\nTotal_time: " + log.getTotal_time());
                        writer.append("\nRating: " + log.getRating());
                        writer.append("\n");
                    }
                }
            }

            writer.flush();
            writer.close();

            // Open the save dialog window
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save " + fileName);
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
    };

    public Map<Tour, List<Log>> importData() throws IOException {

        Map<Tour, List<Log>> tourData = new HashMap<>();
        Tour tour = new Tour();
        List<Log> logs = new ArrayList<>();

        // Open the save dialog window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import TXT");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        //read the csv file and save the tour data in a tour object and the log data in list of log objects
        if (file != null) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            String[] data = line.split(": ");

            if (data[0].equals("Tour")) {
                    data = line.split(": ");
                    tour.setName(data[1]);
                    line = reader.readLine();
                    data = line.split(": ");
                    tour.setDescription(data[1]);
                    line = reader.readLine();
                    data = line.split(": ");
                    tour.setFrom(data[1]);
                    line = reader.readLine();
                    data = line.split(": ");
                    tour.setTo(data[1]);
                    line = reader.readLine();
                    data = line.split(": ");

                    TransportType transportType = new TransportType();

                    switch (data[1]) {
                        case "bicycle":
                            transportType.setType("bike");
                            transportType.setTransport_type_id(1);
                            break;
                        case "car":
                            transportType.setType("car");
                            transportType.setTransport_type_id(2);
                            break;
                        case "pedestrian":
                            transportType.setType("pedestrian");
                            transportType.setTransport_type_id(3);
                            break;
                    }

                    tour.setTransportType(transportType);
                    line = reader.readLine();

                    data = line.split(": ");
                    tour.setDistance(Float.parseFloat(data[1]));
                    line = reader.readLine();
                    data = line.split(": ");
                    tour.setDuration(Integer.parseInt(data[1]));
            }

            //read the log data and save it in a list of log objects
            while ((line = reader.readLine()) != null) {
                Log log = new Log();

                data = line.split(": ");
                if(data[0].equals("Starting_time")){

                    Timestamp timestamp = Timestamp.valueOf(data[1]);
                    log.setStarting_time(timestamp);

                    line = reader.readLine();
                    data = line.split(": ");
                    log.setComment(data[1]);

                    line = reader.readLine();
                    data = line.split(": ");
                    log.setDifficulty(Integer.parseInt(data[1]));

                    line = reader.readLine();
                    data = line.split(": ");
                    log.setTotal_time(Integer.parseInt(data[1]));

                    line = reader.readLine();
                    data = line.split(": ");
                    log.setRating(Integer.parseInt(data[1]));

                    logs.add(log);
                }
            }
        }
        tourData.put(tour, logs);
        return tourData;
    }
}