package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Log;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourFormModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {
    private static List<Log> emptyLogList;
    private static List<Log> fullLogList;
    private static ReportService reportService;

    @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException {
        reportService = new ReportService();
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
    @DisplayName("cannot calculate rating for empty log list")
    void calculateAvgRatingForEmptyLogList() {
        // arrange in before all

        // act
        String actualResult = reportService.calculateAvgRating(emptyLogList);
        String expectedResult = "cannot calculate without logs";

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("calculate rating for full logs list")
    void calculateAvgRatingForFullLogList() {
        // arrange in before all

        // act
        String actualResult = reportService.calculateAvgRating(fullLogList);
        String expectedResult = "2.0";

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("cannot calculate child friendliness for empty logs list")
    void calculateChildFriendlinessForEmptyLogList() {
        // arrange in before all

        // act
        String actualResult = reportService.calculateChildFriendliness(emptyLogList);
        String expectedResult = "cannot calculate without logs";

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("calculate child friendliness for full logs list")
    void calculateChildFriendlinessForFullLogList() {
        // arrange in before all

        // act
        String actualResult = reportService.calculateChildFriendliness(fullLogList);
        String expectedResult = "ok";

        // assert
        assertEquals(expectedResult, actualResult);
    }
}