package flous;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.*;

public class Gausien {
    private String fileName;
    private String fileOutput;

    public Gausien(String fileName, String fileOutput) {
        this.fileName = fileName;
        this.fileOutput = fileOutput;
    }

    public void flouter(int coeff, double ecartType) {
        if (coeff > 1 && coeff % 2 != 0) {
            try {
                File file = new File(fileName);
                BufferedImage image = ImageIO.read(file);
                BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

                double[][] kernel = genererKernel(coeff, ecartType);

                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        int rgb = appliquerKernel(image, x, y, kernel);
                        imageCopy.setRGB(x, y, rgb);
                    }
                }
                ImageIO.write(imageCopy, "jpg", new File(fileOutput));
            } catch (IOException e) {
                System.out.println("Erreur lors de la copie de l'image");
            }
        } else {
            System.out.println("Erreur de coefficient, il doit être impair.");
        }
    }

    //Genere la matrice de coeffs servant de filtre
    private double[][] genererKernel(int taille, double ecartType) {
        double[][] kernel = new double[taille][taille];
        int millieu = taille / 2;
        double somme = 0.0;

        for (int x = -millieu; x <= millieu; x++) {
            for (int y = -millieu; y <= millieu; y++) {
                double exponent = -(x * x + y * y) / (2 * ecartType * ecartType);
                kernel[x + millieu][y + millieu] = Math.exp(exponent) / (2 * Math.PI * ecartType * ecartType);
                somme += kernel[x + millieu][y + millieu];
            }
        }

        // Normalisation des coefs
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                kernel[i][j] /= somme;
            }
        }
        return kernel;
    }
    //application du filtre
    private int appliquerKernel(BufferedImage image, int x, int y, double[][] kernel) {
        int milieu = kernel.length / 2;
        double red = 0, green = 0, blue = 0;
        for (int i = -milieu; i <= milieu; i++) {
            for (int j = -milieu; j <= milieu; j++) {
                int imgX = x + i;
                int imgY = y + j;
                if (imgX < 0 || imgX >= image.getWidth() || imgY < 0 || imgY >= image.getHeight()) {
                    continue;
                }

                Color color = new Color(image.getRGB(imgX, imgY));
                double coeff = kernel[i + milieu][j + milieu];
                red += color.getRed() * coeff;
                green += color.getGreen() * coeff;
                blue += color.getBlue() * coeff;
            }
        }
        int r = Math.min(255, Math.max(0, (int) red));
        int g = Math.min(255, Math.max(0, (int) green));
        int b = Math.min(255, Math.max(0, (int) blue));

        return new Color(r, g, b).getRGB();
    }
}
