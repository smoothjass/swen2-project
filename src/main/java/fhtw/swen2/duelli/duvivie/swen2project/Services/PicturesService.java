package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.ILoggerWrapper;
import fhtw.swen2.duelli.duvivie.swen2project.Logger.LoggerFactory;
import jakarta.persistence.Index;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PicturesService {
    private int imageWidth = 600;
    private int imageHeight = 500;
    private String tourImagesPath = "src/main/resources/fhtw/swen2/duelli/duvivie/swen2project/tourImages/";
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public void deleteAssociatedImages(Tour associatedTour){
        // delete the folder tourImagesPath+associatedTour.getId()
        File folder = new File(tourImagesPath+associatedTour.getTour_id());
        if (folder.exists()) {
            String[]entries = folder.list();
            for(String s: entries){
                File currentFile = new File(folder.getPath(),s);
                currentFile.delete();
            }
            folder.delete();
        }
    }

    private void saveImageToFolder(Tour associatedTour, BufferedImage image){
        // check if the folder tourImagesPath+associatedTour.getId() exists
        // if not, create it
        File folder = new File(tourImagesPath+associatedTour.getTour_id());
        if (!folder.exists()) {
            folder.mkdir();
        }

        String path = tourImagesPath+associatedTour.getTour_id()+"/"+image.hashCode()+".png";

        File outputfile = new File(path);
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            System.out.println("An error occurred while writing an image to the disk");
            System.out.println(e.getMessage());
            logger.error("An error occurred while writing an image to the disk" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

     private Image convertToFxImage(BufferedImage image) {
        // https://stackoverflow.com/questions/30970005/bufferedimage-to-javafx-image
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

    public Image selectNewPicture(Tour associatedTour) {

        // Open the save dialog window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);

        // check if the file is valid
        if (file == null) {
            return null;
        }

        // convert the file to a buffered image
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("An error occurred while reading an image from the disk");
            System.out.println(e.getMessage());
            logger.error("An error occurred while reading an image from the disk" + e.getMessage());
            throw new RuntimeException(e);
        }

        saveImageToFolder(associatedTour, bufferedImage);

        return convertToFxImage(scale(bufferedImage, imageWidth, imageHeight));
    }

    public static BufferedImage scale(BufferedImage src, int w, int h)
    //https://stackoverflow.com/questions/9417356/bufferedimage-resize
    {
        if(src == null || w <= 0 || h <= 0) {
            return null;
        }

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }
    public List<Image> getAsscociatedImages(Tour associatedTour) {
        // check if the folder tourImagesPath+associatedTour.getId() exists
        File folder = new File(tourImagesPath+associatedTour.getTour_id());
        File[] listOfFiles = null;
        if (folder.exists()) {
            //get all the files in the folder in descending order
            listOfFiles = folder.listFiles();
            java.util.Arrays.sort(listOfFiles, java.util.Comparator.comparingLong(File::lastModified).reversed());
        }

        List<Image> images = new java.util.ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                    bufferedImage = scale(bufferedImage, imageWidth, imageHeight);

                } catch (IOException e) {
                    System.out.println("An error occurred while reading an image from the disk");
                    System.out.println(e.getMessage());
                    logger.error("An error occurred while reading an image from the disk" + e.getMessage());
                    throw new RuntimeException(e);
                }
                images.add(convertToFxImage(bufferedImage));
            }
        }
        return images;
    }

    public void deleteImage(String fileName) {
        // delete the file
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public List<String> getFileNames(Tour associatedTour) {
        // check if the folder tourImagesPath+associatedTour.getId() exists
        File folder = new File(tourImagesPath+associatedTour.getTour_id());
        File[] listOfFiles = null;
        if (folder.exists()) {
            listOfFiles = folder.listFiles();
        }

        List<String> fileNames = new java.util.ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                fileNames.add(file.getAbsolutePath());
            }
        }
        return fileNames;
    }

    public String getLatestFileName(Tour tour) {
        // get the name of the newest file
        File folder = new File(tourImagesPath+tour.getTour_id());
        File[] listOfFiles = null;
        if (folder.exists()) {
            listOfFiles = folder.listFiles();
            // sort the files so that the newest file is the first one
            java.util.Arrays.sort(listOfFiles, java.util.Comparator.comparingLong(File::lastModified).reversed());
        }

        if (listOfFiles != null) {
            return listOfFiles[0].getAbsolutePath();
        } else {
            return null;
        }
    }
}