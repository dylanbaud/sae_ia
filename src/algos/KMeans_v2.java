package algos;

import filtres.ClusterImage;
import norme.OutilCouleur;
import norme.Pixel2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe implémentant l'algorithme KMeans
 */
public class KMeans_v2 implements Algorithme {

    /**
     * L'algorithme KMeans est un algorithme de clustering qui permet
     * de regrouper des données en K clusters.
     *
     * @param nbClusters le nombre de clusters à former (nombre de centres)
     * @param tabCouleurs liste de couleur avec leurs coordonnées et leur couleur
     * @return les groupes de pixels correspondants aux clusters
     */
//    public int[] run(int nbClusters, int[][] tabCouleurs) {
//
//        // on vérifie que le nombre de groupes est supérieur à 0
//        if(nbClusters >= 0){
//
//            // condition d'arret de l'algorithme
//            boolean fini = false;
//
//            // on initialise les dimensions de l'image
//            int x = this.image.getWidth();
//            int y = this.image.getHeight();
//
//            // on crée un tableau de résultat contenant la moyenne des couleurs d'un cluster
//            Map<Integer, Color> c = new TreeMap<>();
//            Map<Integer, Color> oldC;
//
//            // on initialise aléatoirement la position des centres des clusters
//            for (int i = 0; i < nbClusters; i++) {
//                // on choisit un pixel aléatoirement dans l'image
//                Pixel2 pixelRandom = pixels[(int)(Math.random()*pixels.length)];
//
//                // on ajoute la couleur du pixel prit aléatoirement dans le tableau initial,
//                // il correspond à la moyenne initiale des couleurs du cluster i
//                c.put(i, pixelRandom.c);
//            }
//
//            int[] tabResults = new int[pixels.length];
//
//            // boucle principale
//            while(!fini){
//                // initialisation des groupes (vides)
//                for(int i = 0; i < pixels.length; i++){
//                    tabResults[i] = 0;
//                }
//
//                // construction des groupes, on ajoute chaque pixel à un cluster en fonction du centroïde le plus proche
//                for (int i = 0; i < pixels.length; i++) {
//
//                    // pixel courant
//                    Pixel2 pixel = pixels[i];
//
//                    // on récupère la valeur du centroïde le plus proche
//                    int k = indiceDuCentroideLePlusProche(pixel, c);
//
//                    // on ajoute un numéro de cluster au pixel courant
//                    tabResults[i] = k;
//                }
//
//                oldC = Map.copyOf(c);
//
//                // on met à jour les centroïdes en calculant les moyennes
//                for(int i = 0; i < nbClusters; i++){
//                    // pour le groupe courant, on calcule la moyenne et
//                    // on met à jour la liste de centroïdes en fonction de la nouvelle moyenne
//                    c.put(i, moyenne(tabResults, pixels, i));
//                }
//
//                // condition d'arret, si entre 2 itérations les moyennes des couleurs
//                // des clusters sont égaux alors on s'arrête
//                if(oldC.equals(c)){
//                    fini = true;
//                }
//            }
//
//            return tabResults;
//        }else{
//            System.out.println("Le nombre de clusters doit être positif.");
//        }
//        return null;
//    }
//
//    /**
//     * Méthode qui permet de déterminer le centroïde (pixel) le plus proche du pixel donné
//     *
//     * @param pixel pixel pour lequel on veut trouver le pixel le plus proche
//     * @param c tableau de pixel dans lequel on va chercher le pixel le plus proche
//     * @return l'indice du pixel le plus proche (dans le tableau de pixel)
//     */
//    public int indiceDuCentroideLePlusProche(Pixel2 pixel, Map<Integer, Color> c){
//        int distanceLaPlusProche = Integer.MAX_VALUE;
//        int indicePlusProche = Integer.MAX_VALUE;
//
//        // on calcule la distance (au niveau de la couleur) avec tous les pixels du tableau et on garde le plus proche
//        for (int i = 0; i < c.size(); i++) {
//            Color c1 = pixel.c;
//            Color c2 = c.get(i);
//            int distanceCourante = formuleProximite(c1, c2);
//
//            if(distanceCourante < distanceLaPlusProche){
//                distanceLaPlusProche = distanceCourante;
//                indicePlusProche = i;
//            }
//        }
//        return indicePlusProche;
//    }
//
//    /**
//     * Méthode qui permet de calculer la distance entre 2 couleurs
//     *
//     * @param color1 couleur 1
//     * @param color2 couleur 2
//     * @return la distance entre les 2 couleurs
//     */
//    public int formuleProximite(Color color1, Color color2){
//        return (int) Math.round(Math.pow((color1.getRed() - color2.getRed()), 2) + Math.pow((color1.getGreen() - color2.getGreen()), 2) + Math.pow((color1.getBlue() - color2.getBlue()), 2));
//    }
//
//    /**
//     * Méthode qui permet de calculer la couleur moyenne d'un groupe
//     *
//     * @param tabResults tableau de résultat contenant une liste de numéro de cluster correspondant au cluster de chaque pixel
//     * @param pixels liste de tous les pixels avec leurs coordonnées et leur couleur
//     * @param numCluster numéro du cluster pour lequel on veut calculer la moyenne du cluster
//     * @return la couleur moyenne d'un groupe
//     */
//    public Color moyenne(int[] tabResults, Pixel2[] pixels, int numCluster){
//        int sommeR = 0;
//        int sommeG = 0;
//        int sommeB = 0;
//
//        int nb = 0;
//
//        // on parcourt tous les pixels d'un groupe
//        for(int i = 0; i < tabResults.length; i++){
//            if(tabResults[i] == numCluster){
//                Color couleurCourante = pixels[i].c;
//                sommeR += couleurCourante.getRed();
//                sommeG += couleurCourante.getGreen();
//                sommeB += couleurCourante.getBlue();
//                nb++;
//            }
//        }
//
//        if(nb == 0){
//            return Color.RED; // Valeur par défaut si le groupe est vide
//        } else {
//            // on retourne une nouvelle couleur correspondant à la moyenne des couleurs des pixels du cluster
//            return new Color(sommeR / nb, sommeG / nb, sommeB / nb);
//        }
//    }

    public static void main(String[] args) throws IOException {

        // image utilisée pour réaliser le clustering
        KMeans_v2 kMeans = new KMeans_v2();

        int[][] tabImage = OutilCouleur.convertTab("img/flouGausien.jpg");
        System.out.println(Arrays.deepToString(tabImage));

//
//        Pixel2[] pixels = new Pixel2[kMeans.image.getWidth() * kMeans.image.getHeight()];
//        int l = 0;
//
//        for(int i = 0; i < kMeans.image.getWidth(); i++){
//            for(int j = 0; j < kMeans.image.getHeight(); j++){
//                Pixel2 pCourant = new Pixel2(i, j, new Color(kMeans.image.getRGB(i, j)));
//                pixels[l] = pCourant;
//                l++;
//            }
//        }
//
//        // on applique l'algorithme
//        int[] tabRes = kMeans.run(2, pixels);
//
//        ClusterImage.afficherClusters(tabRes, "img/16x16.png", "img/16x16-2.jpg");
//        System.out.println("Image générée avec succès !");
    }
}
