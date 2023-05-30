package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.databind.JsonNode;

public class DirectionsResponse {
        public JsonNode getRoute() {return route;}
        public void setRoute(JsonNode route) { this.route = route;}
        private JsonNode route;
}