package fhtw.swen2.duelli.duvivie.swen2project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Models.PictureGalleryModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PictureGalleryController implements Initializable {
    public VBox pictureGallery;
    private PictureGalleryModel pictureGalleryModel;
    private Map<Tour, Image> currentlySelected;
    private int currentIndex = 0;
    @FXML
    private ImageView imageView;
    @FXML
    Button previous;
    @FXML
    Button next;
    private List<Image> images = new ArrayList<>();
    private List<String> fileNames = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void putImages(List<Image> tourImages, Tour associatedTour) {
        for (Image image : tourImages) {
            images.add(image);
            fileNames = pictureGalleryModel.getFileNames(associatedTour);
        }

        //set the first image as the current image
        if (!images.isEmpty()) {
            imageView.setImage(images.get(0));
        }
        else {
            imageView.setImage(null);
        }

        currentIndex = 0;

        if(!images.isEmpty()){
            previous.setDisable(false);
            next.setDisable(false);
        }else {
            previous.setDisable(true);
            next.setDisable(true);
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
            putImages(images, tour);
        }
        else {
            imageView.setImage(null);
        }
    }

    public void deleteAssociatedImages() {
        if(currentlySelected != null) {
            Tour tour = currentlySelected.entrySet().iterator().next().getKey();
            images.clear();
            imageView.setImage(null);
            fileNames.clear();
            pictureGalleryModel.deleteAssociatedImages(tour);
        }
    }

    public void addNewPicture(ActionEvent actionEvent) {
        if(currentlySelected != null) {
            Tour tour = currentlySelected.entrySet().iterator().next().getKey();

            if(tour == null) {
                return;
            }

            Image newImage = pictureGalleryModel.addNewPicture(tour);
            String fileName = pictureGalleryModel.getLatestFileName(tour);
            if(newImage != null) {
                images.add(newImage);
                imageView.setImage(newImage);
                fileNames.add(fileName);
                currentIndex = images.size() - 1;
            }
        }
        if(images.size() == 2){
            previous.setDisable(false);
            next.setDisable(false);
        }
    }

    public void deleteCurrentImage(ActionEvent actionEvent) {
            //delete the image from the source
            if(currentlySelected != null) {
                if(images.size() == 1) {
                    //delete the image from images
                    pictureGalleryModel.deleteImage(fileNames.get(0));
                    images.remove(currentIndex);
                    fileNames.remove(currentIndex);
                    imageView.setImage(null);
                }else if(images.size() > 1) {
                    pictureGalleryModel.deleteImage(fileNames.get(currentIndex));
                    images.remove(currentIndex);
                    fileNames.remove(currentIndex);
                    imageView.setImage(images.get(0));
                    currentIndex = 0;
                }
                else {
                    imageView.setImage(null);
                }
        }

        if(images.size() <= 1){
            previous.setDisable(true);
            next.setDisable(true);
        }
    }
}

