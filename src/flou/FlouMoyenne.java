package flou;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import outil.OutilCouleur;

public class FlouMoyenne {

    private String fileName;

    public FlouMoyenne(String fileName) {
        this.fileName = fileName;
    }

    public void flouter(int largeur, int hauteur) {
        try {
            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);
            BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int[] tabColor = OutilCouleur.getTabColor(image.getRGB(x, y));
                    int red = tabColor[0];
                    int green = tabColor[1];
                    int blue = tabColor[2];
                    int redMoyenne = 0;
                    int greenMoyenne = 0;
                    int blueMoyenne = 0;
                }
            }
            ImageIO.write(imageCopy, "jpg", new File("flouMoyenne.jpg"));
        } catch (IOException e) {
            System.out.println("Erreur lors de la copie de l'image");
        }
    }
}