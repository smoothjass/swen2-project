package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.*;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class ControllerFactory {

    private final MainViewModel mainViewModel;
    private final TourDetailsSubviewModel tourDetailsSubviewModel;
    private final TourFormModel tourFormModel;
    private final MapSubviewModel mapSubviewModel;
    private final TourListSubviewModel tourListSubviewModel;
    private final LogsListModel logsListModel;

    public SubmissionPublisher<Tour> publisher = new SubmissionPublisher<>();
    private MainViewController mainViewController;

    public ControllerFactory() {
        this.mainViewModel = new MainViewModel();
        this.tourDetailsSubviewModel = new TourDetailsSubviewModel();
        this.tourFormModel = new TourFormModel();
        this.mapSubviewModel = new MapSubviewModel();
        this.tourListSubviewModel = new TourListSubviewModel();
        this.logsListModel = new LogsListModel();
    }

    public Object create(Class<?> controllerClass) throws Exception {
        if (controllerClass == MainViewController.class) {
            this.mainViewController = new MainViewController(this.mainViewModel);
            publisher.subscribe(this.mainViewController);
            return mainViewController;
        }
        else if (controllerClass == MapSubviewController.class) {
            return new MapSubviewController(this.mapSubviewModel);
        }
        else if (controllerClass == TourDetailsSubviewController.class) {
            return new TourDetailsSubviewController(this.tourDetailsSubviewModel);
        }
        else if (controllerClass == TourListSubviewController.class) {
            return new TourListSubviewController(this.tourListSubviewModel);
        }
        else if (controllerClass == TourFormController.class) {
            return new TourFormController(this.tourFormModel, publisher);
        }
        else if (controllerClass == LogsListController.class){
            return new LogsListController(this.logsListModel);
        }
        else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }
}
