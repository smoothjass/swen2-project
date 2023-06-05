package fhtw.swen2.duelli.duvivie.swen2project.Models;

import javafx.scene.image.Image;
import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.DatabaseService;
import fhtw.swen2.duelli.duvivie.swen2project.Services.PicturesService;

import java.util.List;

public class PictureGalleryModel {
    private PicturesService picturesService = new PicturesService();
    public Image addNewPicture(Tour associatedTour) {
        return picturesService.getNewPicture(associatedTour);
    }

    public void deleteAssociatedImages(Tour associatedTour) {
        picturesService.deleteAssociatedImages(associatedTour);
    }

    public List<Image> getImages(Tour associatedTour) {
        return picturesService.getAsscociatedImages(associatedTour);
    }

    public void deleteImage(String fileName) {
        picturesService.deleteImage(fileName);
    }

    public List<String> getFileNames(Tour associatedTour) {
        return picturesService.getFileNames(associatedTour);
    }

    public String getLatestFileName(Tour tour) {
        return picturesService.getLatestFileName(tour);
    }
}
