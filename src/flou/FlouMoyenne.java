package flou;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FlouMoyenne {

    private String fileName;
    private String fileOutput;

    public FlouMoyenne(String fileName, String fileOutput) {
        this.fileName = fileName;
        this.fileOutput = fileOutput;
    }

    public void flouter(int coeff) {
        if(coeff > 1 && coeff%2 != 0) {
            try {
                File file = new File(fileName);
                BufferedImage image = ImageIO.read(file);
                BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        ArrayList<Integer> voisins = getRGBVoisins(image, x, y, coeff);
                        int moyenne = moyenne(voisins);
                        imageCopy.setRGB(x, y, moyenne);
                    }
                }
                ImageIO.write(imageCopy, "jpg", new File(fileOutput));
            } catch (IOException e) {
                System.out.println("Erreur lors de la copie de l'image");
            }
        }else{
            System.out.println("Erreur de coefficient, il doit être impaire.");
        }

    }

    private ArrayList<Integer> getRGBVoisins(BufferedImage image, int x, int y, int coeff) {
        ArrayList<Integer> rgbVoisins = new ArrayList<>();
        for (int i = x - ((coeff-1)/2); i <= x + ((coeff-1)/2); i++) {
            for (int j = y - ((coeff-1)/2); j <= y + ((coeff-1)/2); j++) {
                try {
                    rgbVoisins.add(image.getRGB(i, j));
                } catch (ArrayIndexOutOfBoundsException e) {
                    rgbVoisins.add(0);
                }
            }
        }
        return rgbVoisins;
    }

    private int moyenne(ArrayList<Integer> rgbVoisins) {
        int sommeR = 0;
        int sommeG = 0;
        int sommeB = 0;
        for (int rgb : rgbVoisins) {
            Color color = new Color(rgb);
            sommeR += color.getRed();
            sommeG += color.getGreen();
            sommeB += color.getBlue();
        }
        int moyenneR = sommeR / rgbVoisins.size();
        int moyenneG = sommeG / rgbVoisins.size();
        int moyenneB = sommeB / rgbVoisins.size();
        return new Color(moyenneR, moyenneG, moyenneB).getRGB();
    }
}