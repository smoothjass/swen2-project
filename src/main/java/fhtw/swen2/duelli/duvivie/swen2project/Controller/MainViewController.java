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
        // mapSubView has some trouble with binding and we have no idea why, maybe we injected the controller wrong
        // mapSubviewController.requestImage(item);
        // TODO update currently selected in other controllers / publish it, so they can update whatever they need to
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