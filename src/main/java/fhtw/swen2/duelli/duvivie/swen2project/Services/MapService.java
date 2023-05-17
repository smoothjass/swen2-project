package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MapService {
    private String URL;
    public static final String testRequest = "https://www.mapquestapi.com/directions/v2/route" +
            "?key=9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK&from=ClarendonBlvd,Arlington,VA&to=2400+S+Glebe+Rd,+Arlington,+VA";

public static void getTestRoute() throws IOException, URISyntaxException, InterruptedException, ExecutionException {
    // get the route
    CompletableFuture<RawResponse> yieldFact = getRoute(testRequest);
    System.out.println("Waiting for fact"); // TODO make spinner here
    while(!yieldFact.isDone()) {
        // System.out.print(".");
        Thread.sleep(250);
    }
    System.out.println("Fact received :" + yieldFact);
    // get bounding box, session id and distance from the response
    JsonNode boundingBox = yieldFact.get().getRoute().get("boundingBox");
    String sessionId = String.valueOf(yieldFact.get().getRoute().get("sessionId")).replace("\"", "");
    Float distance = Float.valueOf(String.valueOf(yieldFact.get().getRoute().get("distance")));
    System.out.println(boundingBox);
    System.out.println(sessionId);
    System.out.println(distance);
    // send another request to get the static map
    //TODO some magic
}

private static CompletableFuture<RawResponse> getRoute(String url) throws URISyntaxException, JsonProcessingException {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenApply(stringHttpResponse -> {
        try {
            return parseResponse(stringHttpResponse.body());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    });
}
private static RawResponse parseResponse(String toParse) throws JsonProcessingException {
    RawResponse response;
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
        if(beanOrClass.getClass().equals(RawResponse.class)) {
            p.skipChildren();
            return true;
        } else {
            return false;
        }
    }
    });
    return objectMapper.readValue(toParse, RawResponse.class);
}
}
// change