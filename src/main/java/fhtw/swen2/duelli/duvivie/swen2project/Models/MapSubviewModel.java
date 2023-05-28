package fhtw.swen2.duelli.duvivie.swen2project.Models;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Services.MapService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
@Getter
@Setter
public class MapSubviewModel {
    private MapService mapService = new MapService();
    public StringProperty test = new SimpleStringProperty();
    public ObjectProperty<Image> imageView = new SimpleObjectProperty<>();

    public void requestImage(Tour item) {
        Object[] array = new Object[3];

        try {
            array = mapService.getRoute(item.from, item.to, item.transportType.getType());
            BufferedImage bufferedImage = (BufferedImage) array[2];
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

            // convert to Image
            // https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
            Image image = convertToFxImage(bufferedImage);
            this.getImageView().setValue(image);
            System.out.println(image +" image");
            System.out.println(this.getImageView()+ " imageproperty");
        } catch (IOException | URISyntaxException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        this.test.setValue("test");
        System.out.println(test +" test");

        //System.out.println(imageView.get());
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
