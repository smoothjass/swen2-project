package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class LogViewModelTest {

    private static LogViewModel logViewModel;
    private static DatabaseService databaseService;

    @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException {
        databaseService = Mockito.mock(DatabaseService.class);
        logViewModel = new LogViewModel(databaseService);
    }

    @Test
    @DisplayName("duration calculation")
    void setInputs() {
        // arrange
        Log veryshortTour = Mockito.mock(Log.class);
        Log shortTour = Mockito.mock(Log.class);
        Log longTour = Mockito.mock(Log.class);
        Log veryLongTour = Mockito.mock(Log.class);
        Mockito.when(veryshortTour.getTotal_time()).thenReturn(60); // one minute
        Mockito.when(shortTour.getTotal_time()).thenReturn(60*60); // on hour
        Mockito.when(longTour.getTotal_time()).thenReturn(60*60*24); // a day
        Mockito.when(veryLongTour.getTotal_time()).thenReturn(60*60*24*7); // a week

        // act
        logViewModel.setInputs(veryshortTour);
        // assert
        assertEquals(logViewModel.getDays().getValue(), "0");
        assertEquals(logViewModel.getHours().getValue(), "0");
        assertEquals(logViewModel.getMinutes().getValue(), "1");

        // act
        logViewModel.setInputs(shortTour);
        // assert
        assertEquals(logViewModel.getDays().getValue(), "0");
        assertEquals(logViewModel.getHours().getValue(), "1");
        assertEquals(logViewModel.getMinutes().getValue(), "0");

        // act
        logViewModel.setInputs(longTour);
        // assert
        assertEquals(logViewModel.getDays().getValue(), "1");
        assertEquals(logViewModel.getHours().getValue(), "0");
        assertEquals(logViewModel.getMinutes().getValue(), "0");

        // act
        logViewModel.setInputs(veryLongTour);
        // assert
        assertEquals(logViewModel.getDays().getValue(), "7");
        assertEquals(logViewModel.getHours().getValue(), "0");
        assertEquals(logViewModel.getMinutes().getValue(), "0");
    }
}