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
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return new JLabel("Image couldn't load!");
    }
}
