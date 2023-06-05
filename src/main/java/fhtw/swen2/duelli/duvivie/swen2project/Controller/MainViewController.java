package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Models.MainViewModel;
import javafx.fxml.Initializable;

import javafx.scene.image.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class MainViewController implements Initializable, Flow.Subscriber<Map<Tour, Image>> {
// see this tutorial on publish and subscribe
// https://www.appsdeveloperblog.com/reactive-programming-creating-publishers-and-subscribers-in-java/
    private MainViewModel mainViewModel;
    private Flow.Subscription subscription;
    private Map<Tour, Image> currentlySelected = new HashMap<>();
    private LogViewController logViewController;
    private TourFormController tourFormController;
    private TourListSubviewController tourListSubviewController;
    private PictureGalleryController pictureGalleryController;
    private TourDetailsSubviewController tourDetailsSubviewController;
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public MainViewController(MainViewModel mainViewModel, LogViewController logViewController, TourListSubviewController tourListSubviewController, TourFormController tourFormController, PictureGalleryController pictureGalleryController, TourDetailsSubviewController tourDetailsSubviewController){
        this.mainViewModel = mainViewModel;
        this.logViewController = logViewController;
        this.tourListSubviewController = tourListSubviewController;
        this.pictureGalleryController = pictureGalleryController;
        this.tourFormController = tourFormController;
        this.tourDetailsSubviewController = tourDetailsSubviewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        // testing loger
        // logger.debug("subscribed");
        // logger.warn("warning");
        // logger.error("error");
        // logger.fatal("fatal");
        subscription.request(1);
    }

    @Override
    public void onNext(Map<Tour, Image> item) {
        System.out.println("Received Tour: " + item);
        // TODO check if tour/image are null and invoke respectively
        Tour tour = item.entrySet().iterator().next().getKey();
        Image image = item.get(currentlySelected);
        if(image == null & tour != null) {
            // get image from api
            image = tourFormController.requestImage(tour);
        }
        currentlySelected.clear();
        currentlySelected.put(tour, image);
        // update curentlyselected everywhere and then let the controllers invoke whatever they need
        logViewController.setCurrentlySelected(currentlySelected);
        tourFormController.setCurrentlySelected(currentlySelected);
        tourListSubviewController.setCurrentlySelected(currentlySelected);
        pictureGalleryController.setCurrentlySelected(currentlySelected);
        tourDetailsSubviewController.setCurrentlySelected(currentlySelected);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable error) {
        logger.error("Error Occurred: " + error.getMessage());
        System.out.println("Error Occurred: " + error.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Every Element has been received");
    }
}