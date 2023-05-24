package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

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
    public static final String testRequest = "https://www.mapquestapi.com/directions/v2/route" +
            "?key=9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK&from=ClarendonBlvd,Arlington,VA&to=2400+S+Glebe+Rd,+Arlington,+VA";
    public static final String key = "9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK";

    public static void getTestRoute() throws IOException, URISyntaxException, InterruptedException, ExecutionException {
        // get the route
        CompletableFuture<DirectionsResponse> yieldFact = requestRouteFromAPI(testRequest);
        System.out.println("Waiting for fact"); // TODO make spinner here
        while(!yieldFact.isDone()) {
            // System.out.print(".");
            Thread.sleep(250);
        }
        System.out.println("Fact received :" + yieldFact);
        // get start and end from model
        // get bounding box, session id, distance and duration (time) from the response

        JsonNode boundingBox = yieldFact.get().getRoute().get("boundingBox");
        String sessionId = String.valueOf(yieldFact.get().getRoute().get("sessionId")).replace("\"", "");
        Float distance = Float.valueOf(String.valueOf(yieldFact.get().getRoute().get("distance")));
        System.out.println(boundingBox);
        System.out.println(sessionId);
        System.out.println(distance);
        // send another request to get the static map
        //TODO some magic
    }
    public Object[] getRoute(String from, String to, String transportType) throws IOException, URISyntaxException, InterruptedException, ExecutionException{
        // replace white spaces
        from = from.replaceAll("\\s", "");
        to = to.replaceAll("\\s", "");
        // create the URL
        String getRequest = "https://www.mapquestapi.com/directions/v2/route" +
                "?key=" + key +
                "&from=" + from +
                "&to=" + to;
        if(!Objects.equals(transportType, "car")) {
            // car is default, bycicle and pedestrian need to be specified
            getRequest = getRequest + "&routeType=" + transportType;
        }
        System.out.println(getRequest);

        // get the route
        CompletableFuture<DirectionsResponse> yieldDirections = requestRouteFromAPI(getRequest);
        System.out.println("Waiting for fact"); // TODO make spinner here
        while(!yieldDirections.isDone()) {
            // System.out.print(".");
            Thread.sleep(250);
        }
        // System.out.println("Fact received :" + yieldDirections);

        // get bounding box, session id, distance and duration (time) from the response
        JsonNode tempBoundingBox= yieldDirections.get().getRoute().get("boundingBox");
        String boundingBox = tempBoundingBox.get("ul").get("lat") + "," +
                tempBoundingBox.get("ul").get("lng") + "," +
                tempBoundingBox.get("lr").get("lat") + "," +
                tempBoundingBox.get("lr").get("lng");
        String sessionId = String.valueOf(yieldDirections.get().getRoute().get("sessionId")).replace("\"", "");
        Float distance = Float.valueOf(String.valueOf(yieldDirections.get().getRoute().get("distance")));
        Integer time = Integer.valueOf(String.valueOf(yieldDirections.get().getRoute().get("time"))); //seconds
/*
        System.out.println(boundingBox);
        System.out.println(sessionId);
        System.out.println(distance);
        System.out.println(time);
*/

        // send another request with key, start, end, bounding box coordinates and sessionId to get the static map
        getRequest = "https://www.mapquestapi.com/staticmap/v5/map" +
                "?key=" + key +
                "&start=" +  from +
                "&end=" + to +
                "&boundingBox=" + boundingBox +
                "&sessionId=" + sessionId;
        System.out.println(getRequest);
        // get the image
        CompletableFuture<BufferedImage> yieldImage = requestImageFromAPI(getRequest);
        System.out.println("Waiting for fact"); // TODO make spinner here
        while(!yieldImage.isDone()) {
            // System.out.print(".");
            Thread.sleep(250);
        }
        System.out.println("Fact received :" + yieldImage);
        System.out.println("Fact received :" + yieldImage.get());

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
                e.printStackTrace();
            }
            return null;
        });
    }

    private static DirectionsResponse parseDirectionsResponse(String toParse) throws JsonProcessingException {
        DirectionsResponse response;
        var objectMapper = new ObjectMapper();
        System.out.println(toParse);
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
// change