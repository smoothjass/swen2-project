package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import fhtw.swen2.duelli.duvivie.swen2project.Models.*;

import javafx.scene.image.Image;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class ControllerFactory {

    private final MainViewModel mainViewModel;
    private final TourDetailsSubviewModel tourDetailsSubviewModel;
    private final TourFormModel tourFormModel;
    private final TourListSubviewModel tourListSubviewModel;
    private final LogViewModel logViewModel;
    private final TourListItemModel tourListItemModel;
    private final PictureGalleryModel pictureGalleryModel;
    private final LogListItemModel logListItemModel;
    private LogViewController logViewController;
    private TourListSubviewController tourListSubviewController;
    public SubmissionPublisher<Map<Tour, Image>> publisher = new SubmissionPublisher<>();
    private MainViewController mainViewController;

    public ControllerFactory() {
        this.mainViewModel = new MainViewModel();
        this.tourDetailsSubviewModel = new TourDetailsSubviewModel();
        this.tourFormModel = new TourFormModel();
        this.tourListSubviewModel = new TourListSubviewModel();
        this.logViewModel = new LogViewModel();
        this.tourListItemModel = new TourListItemModel();
        this.pictureGalleryModel = new PictureGalleryModel();
        this.logListItemModel = new LogListItemModel();
        logViewController = new LogViewController(this.logViewModel);
        tourListSubviewController = new TourListSubviewController(this.tourListSubviewModel);
    }

    public Object create(Class<?> controllerClass) throws Exception {
        if (controllerClass == MainViewController.class) {
            this.mainViewController = new MainViewController(this.mainViewModel, logViewController, tourListSubviewController);
            publisher.subscribe(this.mainViewController);
            return mainViewController;
        }
        else if (controllerClass == TourDetailsSubviewController.class) {
            return new TourDetailsSubviewController(this.tourDetailsSubviewModel);
        }
        else if (controllerClass == TourListSubviewController.class) {
            return tourListSubviewController;
        }
        else if (controllerClass == TourFormController.class) {
            return new TourFormController(this.tourFormModel, publisher);
        }
        else if (controllerClass == LogViewController.class){
            return logViewController;
        }
        else if (controllerClass == TourListItemController.class){
            return new TourListItemController(this.tourListItemModel);
        }
        else if (controllerClass == PictureGalleryController.class){
            return new PictureGalleryController(this.pictureGalleryModel);
        }
        else if (controllerClass == LogListItemController.class){
            return new LogListItemController(this.logListItemModel);
        }
        else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }
}
