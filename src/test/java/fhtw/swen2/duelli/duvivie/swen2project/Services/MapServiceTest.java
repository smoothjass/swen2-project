package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.databind.JsonNode;
import fhtw.swen2.duelli.duvivie.swen2project.Models.LogViewModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class MapServiceTest {

    private static MapService mapService;
    private static final String key = "9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK";

    @BeforeAll
    static void setUp() throws NoSuchFieldException, IllegalAccessException, URISyntaxException {
        mapService = new MapService();
    }

    @Test
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
    void buildImageRequest() {
        // arrange
        // TODO @smoothjass
    }
}