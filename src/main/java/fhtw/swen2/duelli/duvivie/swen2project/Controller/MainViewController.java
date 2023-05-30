package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Models.MainViewModel;
import javafx.fxml.Initializable;

import javafx.scene.image.Image;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class MainViewController implements Initializable, Flow.Subscriber<Map<Tour, Image>> {
// see this tutorial on publish and subscribe
// https://www.appsdeveloperblog.com/reactive-programming-creating-publishers-and-subscribers-in-java/
    private MainViewModel mainViewModel;
    private Flow.Subscription subscription;
    private Tour currentlySelected;
    private LogViewController logViewController;
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public MainViewController(MainViewModel mainViewModel, LogViewController logViewController){
        this.mainViewModel = mainViewModel;
        this.logViewController = logViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        // testing loger
        logger.debug("subscribed");
        logger.warn("warning");
        logger.error("error");
        logger.fatal("fatal");
        subscription.request(1);
    }

    @Override
    public void onNext(Map<Tour, Image> item) {
        System.out.println("Received Tour: " + item);
        currentlySelected = item.entrySet().iterator().next().getKey();
        Image image = item.get(currentlySelected);
        logViewController.updateImage(image);
        // TODO refresh tour list in tourListSubviewController
        // mapSubView had some trouble with binding and we have no idea why, maybe we injected the controller wrong
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