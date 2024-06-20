package interface_graphique;

import algos.DBScan;
import filtres.ClusterImage;
import filtres.FlouMoyenne;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import norme.NormeRedmean;
import norme.OutilCouleur;

import java.util.ArrayList;
import java.util.Arrays;

public class Modele {
    private Stage stage;
    private Rectangle imageTraitee;
    private Rectangle imageOriginale;
    private VBox containerBiomes;
    private String fichierCourant;

    /**
     * Constructeur du modèle
     * @param s le stage de l'application graphique
     * @param imageTrait l'image traitée
     * @param imageOrigin l'image originale
     * @param containerBiomes le conteneur contenant les informations sur les biomes
     * @param fichierCourant le chemin vers le fichier de l'image que l'on souhaite traiter
     */
    public Modele(Stage s, Rectangle imageTrait, Rectangle imageOrigin, VBox containerBiomes, String fichierCourant) {
        this.stage = s;
        this.imageTraitee = imageTrait;
        this.imageOriginale = imageOrigin;
        this.containerBiomes = containerBiomes;
        this.fichierCourant = fichierCourant;
    }

    /**
     * Traite et crée une nouvelle image à partir d'une autre
     * @param nouvFichier le chemin du fichier à traiter
     */
    public void traiter(String nouvFichier) {
        // On floute puis on applique un algorithme ?
        // Flou
        FlouMoyenne flou = new FlouMoyenne(getFichierCourant(), nouvFichier);
        flou.flouter(Constantes.ECART_TYPE);

        //setFichierCourant(nouvFichier); // Le fichier courant est l'image floutée donc

        // DBScan
        /*DBScan dbScan=new DBScan(new NormeRedmean(), Constantes.ECART_TYPE, Constantes.TAILLE_MATRICE);
        int[][] data= OutilCouleur.convertTab(getFichierCourant());
        int[] result=dbScan.run(data);
        ClusterImage.afficherClusters(result, getFichierCourant(), nouvFichier);*/
    }

    /**
     * Remplace l'image originale ou traitée
     * en fonction des paramètres
     * @param image l'image à afficher
     * @param imgRectangle le container où afficher l'image
     */
    public void setImage(Image image, Rectangle imgRectangle) {
        imgRectangle.setWidth(image.getWidth());
        imgRectangle.setHeight(image.getHeight());
        imgRectangle.setArcWidth(75);   // Des bords ronds
        imgRectangle.setArcHeight(75);
        ImagePattern pattern = new ImagePattern(image);
        imgRectangle.setFill(pattern);

        // Si on peut mettre des ombres
        if (Platform.isSupported(ConditionalFeature.EFFECT)) {
            imgRectangle.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.8)));
        }
    }

    /**
     * Met à jour la liste des biomes trouvés
     * @param biomes les données des biomes
     */
    public void setBiomes(ArrayList<String> biomes) {
        // TODO
    }

    // Des setters et des getters
    public void setFichierCourant(String f) {this.fichierCourant = f;}
    public String getFichierCourant() {return fichierCourant;}
    public Rectangle getImageTraitee() {return imageTraitee;}
    public Rectangle getImageOriginale() {return imageOriginale;}
    public VBox getContainerBiomes() {return containerBiomes;}
    public Stage getStage() {return stage;}
}