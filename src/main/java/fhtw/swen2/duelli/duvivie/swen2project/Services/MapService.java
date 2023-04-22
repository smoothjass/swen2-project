package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
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
            "?key=9fgvtkSKGNbYZZEQpGdNlPENFlQWhvEK&from=Clarendon Blvd,Arlington,VA&to=2400+S+Glebe+Rd,+Arlington,+VA";

    // obviously the real method won't retourn the WHOLE response object but
    // for just trying the async request we'll have it that way
    /*
public static Object getTestRoute() throws IOException, URISyntaxException, InterruptedException, ExecutionException {
    CompletableFuture<Object> yieldFact = sendRequest();
    System.out.println("Waiting for fact"); // TODO make spinner here
    while(!yieldFact.isDone()) {
        System.out.print(".");
        Thread.sleep(250);
    }
    System.out.println("Fact received :" + yieldFact);
    return yieldFact;
    }

private static CompletableFuture<Object> sendRequest() throws URISyntaxException, JsonProcessingException {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(testRequest)).build();
    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(
                    HttpResponse::body);
}
*/
}
