package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.TransportType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TXTServiceTest {

    TXTService txtService;
    TransportType transportType;

    @BeforeEach
    void setUp() {
        //create a txtservice spy
        txtService = Mockito.spy(TXTService.class);

        transportType = new TransportType();
        transportType.setType("Bicycle");
        transportType.setTransport_type_id(1);
    }

    @Test
    @DisplayName("Test importing with null file")
    void testImportWithNullFile() {
        // Create a dummy file
        File file = null;

        // when the txtService calls its getFile() method, return the dummy file but do not actually call the method
        Mockito.doReturn(file).when(txtService).getFile();

        // Verify that the txtService throws an exception
        Assertions.assertThrows(IOException.class, () -> txtService.importData());
    }

    @Test
    @DisplayName("Test importing with empty file")
    void testImportWithActualFile() {
        // Create a dummy file
        File file = new File("src/test/fhtw/swen2/duelli/duvivie/swen2project/temporaryTestFiles/test.txt");

        // when the txtService calls its getFile() method, return the dummy file but do not actually call the method
        Mockito.doReturn(file).when(txtService).getFile();

        // Verify that the txtService throws an exception
        Assertions.assertThrows(IOException.class, () -> txtService.importData());

        //delete the dummy file
        file.delete();
    }
    @Test
    @DisplayName("Test exporting with null arguments")
    void testExportWithNullFile(){
        // Create a null tour
        Tour tour = null;

        // Create a null log list
        List<Log> logList = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> txtService.export(tour, logList));
    }

    @Test
    @DisplayName("Test exporting with null attributes tour")
    void testExportWithNullAttributesTour(){
        // Create a dummy tour with null attributes
        Tour tour = new Tour();

        // Create a dummy log list
        List<Log> logList = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> txtService.export(tour, logList));
    }
}