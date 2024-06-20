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
    public static void afficherClusters(int[] tabClusters, String fileName, String fileOutput) {
        File file = new File(fileName);
        BufferedImage image = null;

        HashMap<Integer, Color> clusterColor = new HashMap<Integer, Color>();

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int largeur = image.getWidth();
        int longueur = image.getHeight();

        BufferedImage imageCopy = new BufferedImage(largeur, longueur, image.getType());
        int x = 0;
        int y = 0;

        for (int cluster : tabClusters) {
            if (cluster != -1) {
                if (!clusterColor.containsKey(cluster)) {
                    Color color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
                    clusterColor.put(cluster, color);
                }
                imageCopy.setRGB(y, x, clusterColor.get(cluster).getRGB());
            } else {
                imageCopy.setRGB(y, x, Color.white.getRGB());
            }
            x += 1;
            if (x >= largeur) {
                x = 0;
                y += 1;
            }
        }

        try {
            ImageIO.write(imageCopy, "jpg", new File(fileOutput));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
        int longueur = image.getHeight();

        BufferedImage imageCopy = new BufferedImage(largeur, longueur, image.getType());
        int x = 0;
        int y = 0;

        for (int cluster : tabClusters) {
            if (cluster != -1) {
                if (!clusterColor.containsKey(cluster)) {
                    Color color = palette.getPlusProche(new Color(image.getRGB(y, x)), new norme.NormeBase());
                    clusterColor.put(cluster, color);
                }
                imageCopy.setRGB(y, x, clusterColor.get(cluster).getRGB());
            } else {
                imageCopy.setRGB(y, x, Color.white.getRGB());
            }
            x += 1;
            if (x >= largeur) {
                x = 0;
                y += 1;
            }
        }

        try {
            ImageIO.write(imageCopy, "jpg", new File(fileOutput));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
