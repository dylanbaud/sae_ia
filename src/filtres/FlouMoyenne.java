package filtres;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe qui permet de flouter une image en utilisant un flou par moyenne
 */
public class FlouMoyenne {

    private String fileName;
    private String fileOutput;

    /**
     * @param fileName chemin et nom de l'image à flouter
     * @param fileOutput chemin et nom de l'image créée
     */
    public FlouMoyenne(String fileName, String fileOutput) {
        this.fileName = fileName;
        this.fileOutput = fileOutput;
    }

    /**
     * Méthode qui permet de flouer une image.
     * Le coefficient fait varier l'intensité du flou
     *
     * @param coeff entier impair qui permet de calculer le flou
     */
    public void flouter(int coeff) {
        // on vérifie que le coefficient est bien imapir et supérieur à 1
        if(coeff > 1 && coeff%2 != 0) {
            try {
                File file = new File(fileName);
                BufferedImage image = ImageIO.read(file);
                // on crée la nouvelle image avec les même dimensions que l'image originale
                BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
                // on parcourt tous les pixels de l'image et on effectue les modifications pour chaque pixel
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        // on récupère tous les voisins de du pixel courant pour ensuite faire la moyenne et
                        // obtenir la nouvelle couleur à ajouter à la nouvelle image
                        ArrayList<Integer> voisins = getRGBVoisins(image, x, y, coeff);
                        int moyenne = moyenne(voisins);
                        imageCopy.setRGB(x, y, moyenne);
                    }
                }
                // on écrit la nouvelle image en format jpg dans le dossier donné en paramètre (avec le nom donné en paramètre)
                ImageIO.write(imageCopy, "jpg", new File(fileOutput));
            } catch (IOException e) { // si on a le moindre problème dans le traitement d'image, on notifie l'utilisateur
                System.out.println("Erreur lors de la copie de l'image");
            }
        }else{ // si le coefficient n'est pas impaire, alors on passe dans cette condition
            System.out.println("Erreur de coefficient, il doit être impair.");
        }
    }

    /**
     * Méthode qui permet de récupérer la valeur RGB des voisins d'un pixel en fonction d'un coefficient
     *
     * @param image image sur laquelle on veut récupérer les voisins d'un pixel
     * @param x coordonnées du pixel en abscisses
     * @param y coordonnées du pixel en ordonnées
     * @param coeff coefficient qui influe sur le nombre de voisins à récupérer
     * @return retourne la liste des valeurs RGB des voisins
     */
    private ArrayList<Integer> getRGBVoisins(BufferedImage image, int x, int y, int coeff) {
        ArrayList<Integer> rgbVoisins = new ArrayList<>(); // création de la nouvelle liste
        // on prend la valeur de tous les pixels adjecents au pixel donné
        for (int i = x - ((coeff-1)/2); i <= x + ((coeff-1)/2); i++) {
            for (int j = y - ((coeff-1)/2); j <= y + ((coeff-1)/2); j++) {
                try {
                    // on ajoute le pixel courant (un voisin du pixel donné ou lui-même) dans la liste des voisins
                    rgbVoisins.add(image.getRGB(i, j));
                } catch (ArrayIndexOutOfBoundsException e) {
                    // dans le cas où on prend un pixel du bord de l'image et qu'il n'a pas de voisins,
                    // on ajoute 0 (comme une valeur par défaut)
                    rgbVoisins.add(0);
                }
            }
        }
        return rgbVoisins;
    }

    /**
     * Méthode qui permet de calculer la moyenne des valeurs RGB d'une liste de voisins
     *
     * @param rgbVoisins liste des valeurs RGB
     * @return la moyenne des valeurs de la liste
     */
    private int moyenne(ArrayList<Integer> rgbVoisins) {
        // on initialise les sommes de chaque couleur à 0
        int sommeR = 0;
        int sommeG = 0;
        int sommeB = 0;
        // on parcourt la liste de tous les voisins
        for (int rgb : rgbVoisins) {
            // on ajoute leur valeur de R, G et B à la somme totale
            Color color = new Color(rgb);
            sommeR += color.getRed();
            sommeG += color.getGreen();
            sommeB += color.getBlue();
        }
        // on calcule la moyenne pour chaque couleur
        int moyenneR = sommeR / rgbVoisins.size();
        int moyenneG = sommeG / rgbVoisins.size();
        int moyenneB = sommeB / rgbVoisins.size();
        // on renvoie la valeur de la nouvelle couleur RGB
        return new Color(moyenneR, moyenneG, moyenneB).getRGB();
    }
}