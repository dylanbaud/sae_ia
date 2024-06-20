package filtres;

import norme.Palette;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClusterImage {

    public static void afficherClustersBiome(int[] tabClusters, String fileName, String fileOutput, Palette palette) {
        File file = new File(fileName);
        BufferedImage image = null;

        HashMap<Integer, Color> clusterColor = new HashMap<Integer, Color>();

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int largeur = image.getWidth();
        int hauteur = image.getHeight();

        BufferedImage imageCopy = new BufferedImage(largeur, hauteur, image.getType());
        int x = 0;
        int y = 0;

        for (int cluster : tabClusters) {
            if (cluster != -1) {
                if (!clusterColor.containsKey(cluster)) {
                    Color color = palette.getPlusProche(new Color(image.getRGB(x, y)), new norme.NormeBase());
                    clusterColor.put(cluster, color);
                }
                imageCopy.setRGB(x, y, clusterColor.get(cluster).getRGB());
            } else {
                imageCopy.setRGB(x, y, Color.white.getRGB());
            }
            y += 1;
            if (y >= hauteur) {
                y = 0;
                x += 1;
            }
        }

        try {
            ImageIO.write(imageCopy, "jpg", new File(fileOutput));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // doit afficher qque le biome de la couleur qui nous concerne sinon affiche blanc
    public static void afficherBiome(int[] tabClusters, String fileName, String fileOutput, Palette palette, Biome biome) {
        File file = new File(fileName);
        BufferedImage image = null;

        HashMap<Integer, Color> clusterColor = new HashMap<Integer, Color>();

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int largeur = image.getWidth();
        int hauteur = image.getHeight();

        BufferedImage imageCopy = new BufferedImage(largeur, hauteur, image.getType());
        int x = 0;
        int y = 0;

        for (int cluster : tabClusters) {
            if (cluster != -1) {
                if (!clusterColor.containsKey(cluster)) {
                    Color color = palette.getPlusProche(new Color(image.getRGB(x, y)), new norme.NormeBase());
                    clusterColor.put(cluster, color);
                }
                if (biome.getColor().equals(clusterColor.get(cluster))) {
                    imageCopy.setRGB(x, y, clusterColor.get(cluster).getRGB());
                } else {
                    Color color = clusterColor.get(cluster);

                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();

                    red = (int) Math.round(red + 0.75 * (255 - red));
                    green = (int) Math.round(green + 0.75 * (255 - green));
                    blue = (int) Math.round(blue + 0.75 * (255 - blue));

                    Color newColor = new Color(red, green, blue);
                    imageCopy.setRGB(x, y, newColor.getRGB());
                }
            } else {
                imageCopy.setRGB(x, y, Color.white.getRGB());
            }
            y += 1;
            if (y >= hauteur) {
                y = 0;
                x += 1;
            }
        }

        try {
            ImageIO.write(imageCopy, "jpg", new File(fileOutput));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
