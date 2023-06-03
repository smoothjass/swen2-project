package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.PictureGalleryModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PictureGalleryController implements Initializable {
    private PictureGalleryModel pictureGalleryModel;
    private Map<Tour, Image> currentlySelected;
    private int currentIndex = 0;
    @FXML
    private ImageView imageView;

    private List<Image> images = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void putImages(List<Image> tourImages) {
        for (Image image : tourImages) {
            images.add(image);
        }

        //set the first image as the current image
        if (!images.isEmpty()) {
            imageView.setImage(images.get(0));
        }
        else {
            imageView.setImage(null);
        }
    }

    @FXML
    public void goToPreviousImage() {
       if(images.isEmpty()){
           return;
       }

        //if the current image is the first image, go to the last image
        if (currentIndex == 0) {
            currentIndex = (images.size() - 1);
            imageView.setImage(images.get(currentIndex));
        }
        else {
            currentIndex = (currentIndex - 1);
            imageView.setImage(images.get(currentIndex));
        }
    }

    @FXML
    public void goToNextImage() {
        if (images.isEmpty()) {
            return;
        }

        //if the current image is the last image, go back to the first image
        if (currentIndex == (images.size() - 1)) {
            currentIndex = 0;
            imageView.setImage(images.get(currentIndex));
            return;
        }
        else {
            currentIndex = (currentIndex + 1);
            imageView.setImage(images.get(currentIndex));
        }
    }

    public PictureGalleryController(PictureGalleryModel pictureGalleryModel) {
        this.pictureGalleryModel = pictureGalleryModel;
    }

    public void setCurrentlySelected(Map<Tour, Image> item) {
        this.currentlySelected = item;
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();
        images.clear();
        if(tour != null) {
            List<Image> images = pictureGalleryModel.getImages(tour);
            putImages(images);
        }
        else {
            imageView.setImage(null);
        }
    }

    public void deleteAssociatedImages(Tour associatedTour) {
        pictureGalleryModel.deleteAssociatedImages(associatedTour);
    }

    public void addNewPicture(ActionEvent actionEvent) {
        Tour tour = currentlySelected.entrySet().iterator().next().getKey();
        Image newImage = pictureGalleryModel.addNewPicture(tour);
        if(newImage != null) {
            images.add(newImage);
            imageView.setImage(newImage);
        }
    }
}

