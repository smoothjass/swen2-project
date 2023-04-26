module fhtw.swen2.duelli.duvivie.swen2project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens fhtw.swen2.duelli.duvivie.swen2project.Services to com.fasterxml.jackson.databind;
    exports fhtw.swen2.duelli.duvivie.swen2project.Services;

    opens fhtw.swen2.duelli.duvivie.swen2project to javafx.fxml;
    exports fhtw.swen2.duelli.duvivie.swen2project;
    exports fhtw.swen2.duelli.duvivie.swen2project.Controller;
    opens fhtw.swen2.duelli.duvivie.swen2project.Controller to javafx.fxml;
}