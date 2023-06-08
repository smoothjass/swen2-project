package fhtw.swen2.duelli.duvivie.swen2project.Services;

import fhtw.swen2.duelli.duvivie.swen2project.Entities.Tour;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PicturesServiceTest {
    private PicturesService picturesService;
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
            picturesService = new PicturesService();
    }

    // Helper method to create a dummy BufferedImage
    private BufferedImage createDummyImage() {
        int width = 500;
        int height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Set dummy pixel values or perform any desired image manipulation
        return image;
    }

    @Test
    @DisplayName("Test scaling image")
    void testScaleImage() {
        // Create a dummy image
        BufferedImage originalImage = createDummyImage();

        // Scale the image
        int newWidth = 300;
        int newHeight = 200;
        BufferedImage scaledImage = picturesService.scale(originalImage, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertEquals(newWidth, scaledImage.getWidth());
        Assertions.assertEquals(newHeight, scaledImage.getHeight());
    }

    @Test
    @DisplayName("Test scaling image with null width")
    void testScaleImageWithNullWidth() {
        // Create a dummy image
        BufferedImage originalImage = createDummyImage();

        // Scale the image
        int newWidth = 0;
        int newHeight = 200;
        BufferedImage scaledImage = picturesService.scale(originalImage, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertNull(scaledImage);
    }

    @Test
    @DisplayName("Test scaling image with null height")
    void testScaleImageWithNullHeight() {
        // Create a dummy image
        BufferedImage originalImage = createDummyImage();

        // Scale the image
        int newWidth = 300;
        int newHeight = 0;
        BufferedImage scaledImage = picturesService.scale(originalImage, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertNull(scaledImage);
    }

    @Test
    @DisplayName("Test scaling image with null image")
    void testScaleImageWithNullImage() {
        // Scale the image
        int newWidth = 300;
        int newHeight = 200;
        BufferedImage scaledImage = picturesService.scale(null, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertNull(scaledImage);
    }

    @Test
    @DisplayName("Test scaling image with negative width")
    void testScaleImageWithNegativeWidth() {
        // Create a dummy image
        BufferedImage originalImage = createDummyImage();

        // Scale the image
        int newWidth = -300;
        int newHeight = 200;
        BufferedImage scaledImage = picturesService.scale(originalImage, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertNull(scaledImage);
    }

    @Test
    @DisplayName("Test scaling image with negative height")
    void testScaleImageWithNegativeHeight() {
        // Create a dummy image
        BufferedImage originalImage = createDummyImage();

        // Scale the image
        int newWidth = 300;
        int newHeight = -200;
        BufferedImage scaledImage = picturesService.scale(originalImage, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertNull(scaledImage);
    }

    @Test
    @DisplayName("Test scaling image with correct input data")
    void testScaleImageWithCorrectInputData() {
        // Create a dummy image
        BufferedImage originalImage = createDummyImage();

        // Scale the image
        int newWidth = 300;
        int newHeight = 200;
        BufferedImage scaledImage = picturesService.scale(originalImage, newWidth, newHeight);

        // Verify the dimensions of the scaled image
        Assertions.assertEquals(newWidth, scaledImage.getWidth());
        Assertions.assertEquals(newHeight, scaledImage.getHeight());
    }
}
