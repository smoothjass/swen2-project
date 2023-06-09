package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.databind.JsonNode;
import fhtw.swen2.duelli.duvivie.swen2project.Models.LogViewModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class MapServiceTest {

    private static MapService mapService;
    private static final String key = "9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK";

    @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException, URISyntaxException {
        mapService = new MapService();
    }

    @Test
    @DisplayName("build directions request")
    void buildDirectionsRequest() {
        // arrange
        String to = "linz";
        String from = "wien";
        String car = "car";
        String bicycle = "bicycle";

        // act
        String actualRequest = mapService.buildDirectionsRequest(from, to, car);
        String expectedRequest = "https://www.mapquestapi.com/directions/v2/route?key=9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK&from=wien&to=linz";
        // assert
        assertEquals(expectedRequest, actualRequest);

        // act
        actualRequest = mapService.buildDirectionsRequest(from, to, bicycle);
        expectedRequest = "https://www.mapquestapi.com/directions/v2/route?key=9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK&from=wien&to=linz&routeType=bicycle";
        // assert
        assertEquals(expectedRequest, actualRequest);
    }

    @Test
    @DisplayName("build image request")
    void buildImageRequest() throws ExecutionException, InterruptedException {
        // arrange
        String to = "linz";
        String from = "wien";
        String sessonId = "AJAA5wcAAIoAAAAOAAAADQAAAK0AAAB42mNYy8DAyMTAwMCekVqUapWce0xoESuQy1B40nAfx8rji7sTWN2T1gLpHCDNgAXAND7hX80C4s-cObOSoUVEs-tCc2RSFpC-D6QZcIBD9cu2OTMCGekcmgwODIlMvGIOLAwOThwCKMoEFDgYWDkEVBiYAPX4IVjnFUm8:car";
        String boundingBox = "48.30422100000016,14.28223000000009,48.11205999999994,16.36842";

        // act
        String expectetRequest = "https://www.mapquestapi.com/staticmap/v5/map?key=9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK&start=wien&end=linz&boundingBox=48.30422100000016,14.28223000000009,48.11205999999994,16.36842&size=1000,300&sessionId=AJAA5wcAAIoAAAAOAAAADQAAAK0AAAB42mNYy8DAyMTAwMCekVqUapWce0xoESuQy1B40nAfx8rji7sTWN2T1gLpHCDNgAXAND7hX80C4s-cObOSoUVEs-tCc2RSFpC-D6QZcIBD9cu2OTMCGekcmgwODIlMvGIOLAwOThwCKMoEFDgYWDkEVBiYAPX4IVjnFUm8:car";
        String actualRequest = mapService.buildImageRequest(boundingBox, sessonId, to, from);

        // assert
        assertEquals(expectetRequest, actualRequest);
    }
}