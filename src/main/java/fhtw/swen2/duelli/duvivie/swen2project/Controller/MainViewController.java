package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private MapSubviewController mapSubviewController;

    private MainViewModel mainViewModel;

    public MainViewController(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application! Change to test push to remote.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}