package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class MapSubviewModel {
    private MapService mapService = new MapService();
    public Image requestImage(Tour item) {
        Object[] array = new Object[3];
        Image image = null;
        try {
            array = mapService.getRoute(item.from, item.to, item.transportType.getType());
            BufferedImage bufferedImage = (BufferedImage) array[2];
            // convert to Image
            // https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
            image = convertToFxImage(bufferedImage);
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
