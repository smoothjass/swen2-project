package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MapService {
    private String URL;
    private static final String key = "9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK";
            // back up key "0fpJuKUjoz6RWEX8UipQQuOLFJgEG8ep";
            // old key "9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK";
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public String buildDirectionsRequest(String from, String to, String transportType) {
        String getRequest = "https://www.mapquestapi.com/directions/v2/route" +
                "?key=" + key +
                "&from=" + from +
                "&to=" + to;
        if(!Objects.equals(transportType, "car")) {
            // car is default, bycicle and pedestrian need to be specified
            getRequest = getRequest + "&routeType=" + transportType;
        }
        return getRequest;
    }

    public String buildImageRequest(String boundingBox, String sessionId, String to, String from) throws ExecutionException, InterruptedException {
        // build another request string with key, start, end, bounding box coordinates and sessionId to get the static map
        String getRequest = "https://www.mapquestapi.com/staticmap/v5/map" +
                "?key=" + key +
                "&start=" +  from +
                "&end=" + to +
                "&boundingBox=" + boundingBox +
                "&size=1000,300" +
                "&sessionId=" + sessionId;
        return getRequest;
    }

    public Object[] getRoute(String from, String to, String transportType) throws IOException, URISyntaxException, InterruptedException, ExecutionException{
        // replace white spaces
        from = from.replaceAll("\\s", "");
        to = to.replaceAll("\\s", "");
        // create the URL for the directions api
        String getRequest = buildDirectionsRequest(from, to, transportType);
        // request the route from the api
        CompletableFuture<DirectionsResponse> yieldDirections = requestRouteFromAPI(getRequest);
        logger.debug("waiting for route from: " + getRequest);
        while(!yieldDirections.isDone()) {
            Thread.sleep(250);
        }
        // System.out.println("Fact received :" + yieldDirections.get().getRoute());

        if(yieldDirections.get().getRoute() != null) {
            if (Objects.equals(yieldDirections.get().getRoute().toString(), "{\"routeError\":{\"errorCode\":2,\"message\":\"\"}}")) {
                // error while retrieving directions
                return null;
            }
        }
        else {
            return null;
        }
        // get bounding box, session id, distance and duration (time) from the response
        JsonNode tempBoundingBox= yieldDirections.get().getRoute().get("boundingBox");
        String boundingBox = tempBoundingBox.get("ul").get("lat") + "," +
                tempBoundingBox.get("ul").get("lng") + "," +
                tempBoundingBox.get("lr").get("lat") + "," +
                tempBoundingBox.get("lr").get("lng");
        String sessionId = String.valueOf(yieldDirections.get().getRoute().get("sessionId")).replace("\"", "");
        Float distance = Float.valueOf(String.valueOf(yieldDirections.get().getRoute().get("distance")));
        Integer time = Integer.valueOf(String.valueOf(yieldDirections.get().getRoute().get("time"))); //seconds
        getRequest = buildImageRequest(boundingBox, sessionId, to, from);

        // request the image from the static map api
        CompletableFuture<BufferedImage> yieldImage = requestImageFromAPI(getRequest);
        logger.debug("waiting for image from: " + getRequest);
        while(!yieldImage.isDone()) {
            Thread.sleep(250);
        }

        Object[] array = new Object[3];

        array[0] = distance;
        array[1] = time;
        array[2] = yieldImage.get();

        return array;
    }

    private static CompletableFuture<BufferedImage> requestImageFromAPI(String getRequest) throws URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(getRequest)).build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofInputStream()).thenApply(stringHttpResponse -> {
            try {
                return ImageIO.read(stringHttpResponse.body());
            } catch (IOException e) {
                System.out.println("An error occurred while requesting an image");
                System.out.println(e.getMessage());
                logger.error("An error occurred while requesting an image" + e.getMessage());
                e.printStackTrace();
            }
            return null;
        });
    }

    private static CompletableFuture<DirectionsResponse> requestRouteFromAPI(String getRequest) throws URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(getRequest)).build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenApply(stringHttpResponse -> {
            try {
                return parseDirectionsResponse(stringHttpResponse.body());
            } catch (JsonProcessingException e) {
                System.out.println("An error occurred while requesting a rout");
                System.out.println(e.getMessage());
                logger.error("An error occurred while requesting a route" + e.getMessage());
                e.printStackTrace();
            }
            return null;
        });
    }

    public static DirectionsResponse parseDirectionsResponse(String toParse) throws JsonProcessingException {
        DirectionsResponse response;
        var objectMapper = new ObjectMapper();
        // System.out.println(toParse);
        objectMapper.addHandler(new DeserializationProblemHandler() {
        @Override
        public boolean handleUnknownProperty(
            DeserializationContext ctxt,
            JsonParser p,
            JsonDeserializer<?> deserializer,
            Object beanOrClass,
            String propertyName) throws IOException {
            if(beanOrClass.getClass().equals(DirectionsResponse.class)) {
                p.skipChildren();
                return true;
            } else {
                return false;
            }
        }
        });
        return objectMapper.readValue(toParse, DirectionsResponse.class);
    }
}