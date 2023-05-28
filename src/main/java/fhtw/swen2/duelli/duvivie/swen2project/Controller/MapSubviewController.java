package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.MapSubviewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapSubviewController implements Initializable {
    @FXML
    public TextField test;
    @FXML
    public ImageView imageView;
    private MapSubviewModel mapSubviewModel;

    public MapSubviewController(MapSubviewModel mapSubviewModel) {
        this.mapSubviewModel = mapSubviewModel;
    }



    public void requestImage(Tour item) {
        // none of these lines work
        //imageView.setImage(this.mapSubviewModel.requestImage(item));
        this.mapSubviewModel.requestImage(item);
        System.out.println(this.imageView); // this is null. WHY IS THIS NULL?!
        System.out.println(this.test); // this is null. WHY IS THIS NULL?!
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.test.textProperty().bindBidirectional(mapSubviewModel.getTest());
        this.imageView.imageProperty().bindBidirectional(mapSubviewModel.getImageView());
    }
}
