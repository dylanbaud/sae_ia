package filtres;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Blanc {

    private String fileName;
    private String fileOutput;

    /**
     * @param fileName   chemin et nom de l'image à flouter
     * @param fileOutput chemin et nom de l'image créée
     */
    public Blanc(String fileName, String fileOutput) {
        this.fileName = fileName;
        this.fileOutput = fileOutput;
    }

    public void blanchir() {
        try {
            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);
            BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Color color = new Color(image.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();

                    red = Math.min(255, (int) Math.round(red + 0.75 * (255 - red)));
                    green = Math.min(255, (int) Math.round(green + 0.75 * (255 - green)));
                    blue = Math.min(255, (int) Math.round(blue + 0.75 * (255 - blue)));

                    Color newColor = new Color(red, green, blue);
                    imageCopy.setRGB(x, y, newColor.getRGB());
                }
            }
            ImageIO.write(imageCopy, "jpg", new File(fileOutput));
        } catch (IOException e) {
            System.out.println("Erreur lors de la copie de l'image");
        }
    }
}