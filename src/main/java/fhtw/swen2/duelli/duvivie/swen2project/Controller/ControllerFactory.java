package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.MainViewModel;

public class ControllerFactory {

    private final MainViewModel mainViewModel;

    public ControllerFactory() {
        this.mainViewModel = new MainViewModel();
    }

    public Object create(Class<?> controllerClass) throws Exception {
        if (controllerClass == MainViewController.class) {
            return new MainViewController(this.mainViewModel);
        }
        else if (controllerClass == MapSubviewController.class) {
            return new MapSubviewController();
        }
        else if (controllerClass == TourDetailsSubviewController.class) {
            return new TourDetailsSubviewController();
        }
        else if (controllerClass == TourListSubviewController.class) {
            return new TourListSubviewController();
        }
        else if (controllerClass == TourFormController.class) {
            return new TourFormController();
        }
        else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }
}
