package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TourFormModelTest {

    private static DatabaseService databaseService;
    private static List<Log> emptyLogList;
    private static List<Log> fullLogList;
    private static TourFormModel tourFormModel;

    @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException {
        databaseService = Mockito.mock(DatabaseService.class);
        tourFormModel = new TourFormModel(databaseService);
        emptyLogList = new ArrayList<>();
        fullLogList = new ArrayList<>();

        Log log1 = Mockito.mock(Log.class);
        Mockito.when(log1.getRating()).thenReturn(1);
        Mockito.when(log1.getDifficulty()).thenReturn(1);

        Log log2 = Mockito.mock(Log.class);
        Mockito.when(log2.getRating()).thenReturn(2);
        Mockito.when(log2.getDifficulty()).thenReturn(2);

        Log log3 = Mockito.mock(Log.class);
        Mockito.when(log3.getRating()).thenReturn(3);
        Mockito.when(log3.getDifficulty()).thenReturn(3);

        fullLogList.add(log1);
        fullLogList.add(log2);
        fullLogList.add(log3);
    }

    @Test
    @DisplayName("convert buffered image to fx image")
    void convertToFxImage() {
        // arrange
        int width = 500;
        int height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // act
        Image FxImage = tourFormModel.convertToFxImage(image);

        // assert
        assertNotEquals(null, FxImage);
        assertEquals(height, FxImage.getHeight());
        assertEquals(width, FxImage.getWidth());
    }

    @Test
    @DisplayName("cannot calculate values for tour with no logs")
    void cannotCalculateValuesForTourWithNoLogs() {
        // arrange
        Mockito.when(databaseService.getAllLogsForTour(Mockito.any())).thenReturn(emptyLogList);

        // act
        tourFormModel.calculateValues(new Tour());
        String expectedChildfriendliness = "cannot calculate at the moment";
        String expectedPopularity = "cannot calculate at the moment";
        String actualChildfriendliness = tourFormModel.getChildFriendliness().getValue();
        String actualPopularity = tourFormModel.getPopularity().getValue();

        // assert
        assertEquals(expectedChildfriendliness, actualChildfriendliness);
        assertEquals(expectedPopularity, actualPopularity);
        assertNotEquals(null, actualChildfriendliness);
        assertNotEquals(null, actualPopularity);
        assertNotEquals("NaN", actualPopularity);
    }

    @Test
    @DisplayName("calculate values for tour with logs")
    void calculateValuesForTourWithLogs() {
        // arrange
        Mockito.when(databaseService.getAllLogsForTour(Mockito.any())).thenReturn(fullLogList);

        // act
        tourFormModel.calculateValues(new Tour());
        String expectedChildfriendliness = "ok";
        String expectedPopularity = "2.0";
        String actualChildfriendliness = tourFormModel.getChildFriendliness().getValue();
        String actualPopularity = tourFormModel.getPopularity().getValue();

        // assert
        assertEquals(expectedChildfriendliness, actualChildfriendliness);
        assertEquals(expectedPopularity, actualPopularity);
        assertNotEquals(null, actualChildfriendliness);
        assertNotEquals(null, actualPopularity);
        assertNotEquals("NaN", actualPopularity);
    }

    @Test
    @DisplayName("calculate different durations")
    void calculateDuration() {
        // arrange
        Tour veryshortTour = Mockito.mock(Tour.class);
        Tour shortTour = Mockito.mock(Tour.class);
        Tour longTour = Mockito.mock(Tour.class);
        Tour veryLongTour = Mockito.mock(Tour.class);
        Mockito.when(veryshortTour.getDuration()).thenReturn(60); // one minute
        Mockito.when(shortTour.getDuration()).thenReturn(60*60); // on hour
        Mockito.when(longTour.getDuration()).thenReturn(60*60*24); // a day
        Mockito.when(veryLongTour.getDuration()).thenReturn(60*60*24*7); // a week

        // act
        tourFormModel.calculateDuration(veryshortTour);
        String expectedDuration = "0 days 0 hours 1 minutes";
        String actualDuration = tourFormModel.getDuration().getValue();
        // assert
        assertEquals(expectedDuration, actualDuration);
        assertNotEquals(null, actualDuration);

        // act
        tourFormModel.calculateDuration(shortTour);
        expectedDuration = "0 days 1 hours 0 minutes";
        actualDuration = tourFormModel.getDuration().getValue();
        // assert
        assertEquals(expectedDuration, actualDuration);
        assertNotEquals(null, actualDuration);

        // act
        tourFormModel.calculateDuration(longTour);
        expectedDuration = "1 days 0 hours 0 minutes";
        actualDuration = tourFormModel.getDuration().getValue();
        // assert
        assertEquals(expectedDuration, actualDuration);
        assertNotEquals(null, actualDuration);

        // act
        tourFormModel.calculateDuration(veryLongTour);
        expectedDuration = "7 days 0 hours 0 minutes";
        actualDuration = tourFormModel.getDuration().getValue();
        // assert
        assertEquals(expectedDuration, actualDuration);
        assertNotEquals(null, actualDuration);
    }
}