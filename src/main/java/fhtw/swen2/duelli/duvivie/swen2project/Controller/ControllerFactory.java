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
    private final PictureGalleryModel pictureGalleryModel;
    private LogViewController logViewController;
    private TourFormController tourFormController;
    private TourListSubviewController tourListSubviewController;
    private PictureGalleryController pictureGalleryController;
    private TourDetailsSubviewController tourDetailsSubviewController;
    public SubmissionPublisher<Map<Tour, Image>> publisher = new SubmissionPublisher<>();
    private MainViewController mainViewController;

    public ControllerFactory() {
        this.mainViewModel = new MainViewModel();
        this.tourDetailsSubviewModel = new TourDetailsSubviewModel();
        this.tourFormModel = new TourFormModel();
        this.tourListSubviewModel = new TourListSubviewModel();
        this.logViewModel = new LogViewModel();
        this.pictureGalleryModel = new PictureGalleryModel();
        logViewController = new LogViewController(this.logViewModel);
        tourFormController = new TourFormController(this.tourFormModel, publisher);
        tourListSubviewController = new TourListSubviewController(this.tourListSubviewModel, publisher);
        pictureGalleryController = new PictureGalleryController(this.pictureGalleryModel);
        tourDetailsSubviewController = new TourDetailsSubviewController(this.tourDetailsSubviewModel, publisher);
    }

    public Object create(Class<?> controllerClass) throws Exception {
        if (controllerClass == MainViewController.class) {
            this.mainViewController = new MainViewController(this.mainViewModel, logViewController,
                    tourListSubviewController, tourFormController, pictureGalleryController, tourDetailsSubviewController);
            publisher.subscribe(this.mainViewController);
            return mainViewController;
        }
        else if (controllerClass == TourDetailsSubviewController.class) {
            return tourDetailsSubviewController;
        }
        else if (controllerClass == TourListSubviewController.class) {
            return tourListSubviewController;
        }
        else if (controllerClass == TourFormController.class) {
            return tourFormController;
        }
        else if (controllerClass == LogViewController.class){
            return logViewController;
        }
        else if (controllerClass == PictureGalleryController.class){
            return pictureGalleryController;
        }
        else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }
}
