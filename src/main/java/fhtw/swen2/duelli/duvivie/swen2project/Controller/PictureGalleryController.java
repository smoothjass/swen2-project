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

/*
            // write to disk
            // https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html
            String path = "C:\\Users\\jassi\\OneDrive\\Dokumente\\FH\\BIF\\2023_SS\\SWEN2\\swen2-project\\src\\main\\" +
                    "java\\fhtw\\swen2\\duelli\\duvivie\\swen2project\\Controller\\saved.png";
            File outputfile = new File(path);
            ImageIO.write(bufferedImage, "png", outputfile);
            this.imageProperty.set(new Image(path));
            this.imageProperty.setValue(new Image(path));
*/