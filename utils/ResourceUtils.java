package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ResourceUtils {
    public static JLabel loadImage(String resource) {
        BufferedImage image;

        try {
            InputStream inputStream = ResourceUtils.class.getResourceAsStream(resource);
            System.out.println("Trying to load: " + resource);
            System.out.println("InputStream is null: " + (inputStream == null));
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            image = ImageIO.read(inputStream);
            System.out.println("Image is null: " + (image == null));
            return new JLabel(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return new JLabel("Image couldn't load!");
    }
}
