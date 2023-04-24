package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.MainViewModel;
import fhtw.swen2.duelli.duvivie.swen2project.Models.MapSubviewModel;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourDetailsSubviewModel;
import fhtw.swen2.duelli.duvivie.swen2project.Models.TourListSubviewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private MainViewModel mainViewModel;
    private TourListSubviewModel tourListSubviewModel;
    private TourDetailsSubviewModel tourDetailsSubviewModel;
    private MapSubviewModel mapSubviewModel;

    public MainViewController(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}