package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Daos.TourDao;
import fhtw.swen2.duelli.duvivie.swen2project.Models.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ControllerFactory {

    private final MainViewModel mainViewModel;
    private final TourDetailsSubviewModel tourDetailsSubviewModel;
    private final TourFormModel tourFormModel;
    private final MapSubviewModel mapSubviewModel;
    private final TourListSubviewModel tourListSubviewModel;
    private final LogsListModel logsListModel;
    private final TourListItemModel tourListItemModel;
    private final PictureGalleryModel pictureGalleryModel;

    public ControllerFactory() {
        this.mainViewModel = new MainViewModel();
        this.tourDetailsSubviewModel = new TourDetailsSubviewModel();
        this.tourFormModel = new TourFormModel();
        this.mapSubviewModel = new MapSubviewModel();
        this.tourListSubviewModel = new TourListSubviewModel();
        this.logsListModel = new LogsListModel();
        this.tourListItemModel = new TourListItemModel();
        this.pictureGalleryModel = new PictureGalleryModel();
    }

    public Object create(Class<?> controllerClass) throws Exception {
        if (controllerClass == MainViewController.class) {
            return new MainViewController(this.mainViewModel);
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
            return new TourFormController(this.tourFormModel);
        }
        else if (controllerClass == LogsListController.class){
            return new LogsListController(this.logsListModel);
        }
        else if (controllerClass == TourListItemController.class){
            return new TourListItemController(this.tourListItemModel);
        }
        else if (controllerClass == PictureGalleryController.class){
            return new PictureGalleryController(this.pictureGalleryModel);
        }
        else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }
}
