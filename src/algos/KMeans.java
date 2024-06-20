package algos;

import norme.Pixel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static javax.imageio.ImageIO.write;

/**
 * Classe implémentant l'algorithme KMeans
 */
public class KMeans implements Algorithme {

    /**
     * Image pour laquelle on veut déterminer les différents biomes
     */
    BufferedImage image;

    /**
     * Contructeur
     *
     * @param cheminImage chemin de l'image sur laquelle on veut déterminer les différents biomes
     */
    public KMeans(String cheminImage) {
        try{
            this.image = ImageIO.read(new File(cheminImage));
        }catch (IOException e){
            System.out.println("Problème lors de la lecture de l'image.");
        }
    }

    /**
     * L'algorithme KMeans est un algorithme de clustering qui permet
     * de regrouper des données en K clusters à partir d'une palette
     * de couleurs.
     *
     * @param nbClusters le nombre de clusters à former (nombre de centres)
     * @return les groupes de pixels correspondants aux clusters
     */
    public TreeMap<Integer, ArrayList<Pixel>> run(int nbClusters) {

        // on vérifie que le nombre de groupes est supérieur à 0
        if(nbClusters >= 0){

            // condition d'arret de l'algorithme
            boolean fini = false;

            // on initialise les dimensions de l'image
            int x = this.image.getWidth();
            int y = this.image.getHeight();

            // on crée un tableau de résultat contenant la moyenne des couleurs d'un cluster
            Map<Integer, Color> c = new TreeMap<>();
            Map<Integer, Color> oldC;

            // on initialise aléatoirement la position des centres des clusters
            for (int i = 0; i < nbClusters; i++) {
                // on choisit un pixel aléatoirement dans l'image
                int xRandom = (int) (Math.random()*x);
                int yRandom = (int) (Math.random()*y);

                // on ajoute la couleur du pixel généré aléatoirement dans le tableau initial,
                // il correspond à la moyenne des couleurs d'un cluster
                c.put(i, new Color(this.image.getRGB(xRandom, yRandom)));
            }

            // initialisation des groupes (liste (de la taille du nombre de groupes) de listes(liste des pixels du groupe))
            TreeMap<Integer, ArrayList<Pixel>> groupes = new TreeMap<>();

            // boucle principale
            while(!fini){
                fini = true;
                // initialisation des groupes (vides)
                for(int i = 0; i < nbClusters; i++){
                    groupes.put(i, new ArrayList<>());
                }

                // construction des groupes, on ajoute chaque pixel à un cluster en fonction du centroïde le plus proche
                for (int a = 0; a < x; a++) {
                    for (int b = 0; b < y; b++) {
                        // pixel courant
                        Pixel pixel = new Pixel(a, b);

                        // on récupère la valeur du centroïde le plus proche
                        int k = indiceDuCentroideLePlusProche(pixel, c);

                        // on ajoute le pixel au groupe correspondant au centroïde
                        ArrayList<Pixel> nouvelleListe = groupes.get(k);
                        nouvelleListe.add(pixel);
                        groupes.put(k, nouvelleListe);
                    }
                }

                oldC = Map.copyOf(c);

                // on met à jour les centroïdes en calculant les moyennes
                for(int i = 0; i < nbClusters; i++){
                    // pour le groupe courant, on calcule la moyenne et
                    // on met à jour la liste de centroïdes en fonction de la nouvelle moyenne
                    c.put(i, moyenne(groupes.get(i)));
                }

                // condition d'arret, si entre 2 itérations les moyennes des couleurs
                // des clusters sont égaux alors on s'arrête
                if(oldC.equals(c)){
                    fini = true;
                }
            }

            return groupes;
        }else{
            System.out.println("Le nombre de clusters doit être positif.");
        }
        return null;
    }

    /**
     * Méthode qui permet de déterminer le centroïde (pixel) le plus proche du pixel donné
     *
     * @param pixel pixel pour lequel on veut trouver le pixel le plus proche
     * @param c tableau de pixel dans lequel on va chercher le pixel le plus proche
     * @return l'indice du pixel le plus proche (dans le tableau de pixel)
     */
    public int indiceDuCentroideLePlusProche(Pixel pixel, Map<Integer, Color> c){
        int distanceLaPlusProche = Integer.MAX_VALUE;
        int indicePlusProche = Integer.MAX_VALUE;

        // on calcule la distance (au niveau de la couleur) avec tous les pixels du tableau et on garde le plus proche
        for (int i = 0; i < c.size(); i++) {
            Color c1 = new Color(this.image.getRGB(pixel.x, pixel.y));
            Color c2 = c.get(i);
            int distanceCourante = formuleProximite(c1, c2);

            if(distanceCourante < distanceLaPlusProche){
                distanceLaPlusProche = distanceCourante;
                indicePlusProche = i;
            }
        }
        return indicePlusProche;
    }

    /**
     * Méthode qui permet de calculer la distance entre 2 couleurs
     *
     * @param color1 couleur 1
     * @param color2 couleur 2
     * @return la distance entre les 2 couleurs
     */
    public int formuleProximite(Color color1, Color color2){
        return (int) Math.round(Math.pow((color1.getRed() - color2.getRed()), 2) + Math.pow((color1.getGreen() - color2.getGreen()), 2) + Math.pow((color1.getBlue() - color2.getBlue()), 2));
    }

    /**
     * Méthode qui permet de calculer la couleur moyenne d'un groupe
     *
     * @param groupe groupe pour lequel on veut calculer la couleur moyenne
     * @return la couleur moyenne d'un groupe
     */
    public Color moyenne(ArrayList<Pixel> groupe){
        int sommeRGB = 0;

        // on parcourt tous les pixels d'un groupe
        for(int i = 0; i < groupe.size(); i++){
            sommeRGB += this.image.getRGB(groupe.get(i).x, groupe.get(i).y);
        }

        if(groupe.size() == 0){
            return Color.RED;
        }else{
            // on retourne une nouvelle couleur correspondant à la moyenne des couleurs des pixels du cluster
            return new Color(sommeRGB/groupe.size());
        }
    }

    public static void main(String[] args) throws IOException {

        // image utilisée pour réaliser le clustering
        KMeans kMeans = new KMeans("img/flouGausien.jpg");
        // on applique l'algorithme
        TreeMap<Integer, ArrayList<Pixel>> res = kMeans.run(8);

        // on crée une image "vide"
        BufferedImage sortie = new BufferedImage(kMeans.image.getWidth(), kMeans.image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // on parcourt les clusters un par un
        for (int i = 0; i < res.size(); i++) {
            // on définit une couleur aléatoire pour chaque cluster
            Random random = new Random();
            Color cCourante = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            // on parcourt tous les pixels du cluster
            for(int j = 0; j < res.get(i).size(); j++){
                sortie.setRGB(res.get(i).get(j).x, res.get(i).get(j).y, cCourante.getRGB());
            }
        }

        // nouvelle image contenant les différents clusters
        write(sortie, "jpg", new File("img/nouvelleImageKMeans.jpg"));
        System.out.println("Image générée avec succès !");
    }
}
