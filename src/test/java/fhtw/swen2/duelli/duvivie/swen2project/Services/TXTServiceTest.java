package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TXTServiceTest {

    TXTService txtService;

    @BeforeEach
    void setUp() {
        //create a txtservice spy

        txtService = Mockito.spy(TXTService.class);
    }

    @Test
    @DisplayName("Test if the txt file is created")
    void testCreateTXTFile() {
        //create a dummy tour
        Tour tour = new Tour();
        tour.setTour_id(1);
        tour.setName("TestTour");
        tour.setDistance(100F);
        tour.setDuration(100);
        tour.setTo("TestTo");
        tour.setFrom("TestFrom");

        List<Log> logs = new ArrayList<>();

        //create thre dummy logs for the tour
        Log log1 = new Log();
        log1.setLog_id(1);
        log1.setTour_id(tour.getTour_id());

        Log log2 = new Log();
        log2.setLog_id(2);
        log2.setTour_id(tour.getTour_id());

        Log log3 = new Log();
        log3.setLog_id(3);
        log3.setTour_id(tour.getTour_id());

        //add the logs to the tour
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);

        //call the method
        try {
            txtService.export(tour, logs);
        } catch (IOException e) {
            assertTrue(false);
        }

        //verify if that the method wants to create a file

    }

}