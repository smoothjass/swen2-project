package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
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
import java.util.concurrent.Flow;

public class MainViewController implements Initializable, Flow.Subscriber<Tour> {
// see this tutorial on publish and subscribe
// https://www.appsdeveloperblog.com/reactive-programming-creating-publishers-and-subscribers-in-java/
    private MainViewModel mainViewModel;
    private Flow.Subscription subscription;
    private Tour currentlySelected;

    // TODO keep checking if this keeps working or if we need to inject the mapsubviewcontroller
    // from the controller factory
    private MapSubviewController mapSubviewController = new MapSubviewController(new MapSubviewModel());

    // jeder controller sollte nur sein eigenes model haben
    // private TourListSubviewModel tourListSubviewModel;
    // private TourDetailsSubviewModel tourDetailsSubviewModel;
    // private MapSubviewModel mapSubviewModel;

    public MainViewController(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Tour item) {
        System.out.println("Received Tour: " + item);
        currentlySelected = item;
        mapSubviewController.requestImage(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable error) {
        System.out.println("Error Occurred: " + error.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Every Element has been received");
    }
}