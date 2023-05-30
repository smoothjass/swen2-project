package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import fhtw.swen2.duelli.duvivie.swen2project.Models.PictureGalleryModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PictureGalleryController implements Initializable {
    private PictureGalleryModel pictureGalleryModel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public PictureGalleryController(PictureGalleryModel pictureGalleryModel) {
        this.pictureGalleryModel = pictureGalleryModel;
    }
}
