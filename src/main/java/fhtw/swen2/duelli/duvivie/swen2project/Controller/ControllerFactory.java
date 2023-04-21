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
        else {
            throw new Exception("Controller not supported " + controllerClass.getName());
        }
    }
}
