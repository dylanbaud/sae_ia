package interface_graphique;

import algos.DBScan;
import algos.HAC;
import algos.KMeans_v1;
import algos.KMeans_v2;
import filtres.Biome;
import filtres.ClusterImage;
import filtres.FlouMoyenne;
import filtres.Gausien;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.css.converter.ColorConverter;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import norme.NormeRedmean;
import norme.OutilCouleur;
import norme.Palette;
import norme.Pixel2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Modele {

    public static final Palette palette = new Palette(new java.awt.Color[]{
            Biome.TUNDRA.getColor(),
            Biome.TAIGA.getColor(),
            Biome.FORET_TEMPEREE.getColor(),
            Biome.FORET_TROPICALE.getColor(),
            Biome.SAVANE.getColor(),
            Biome.PRAIRIE.getColor(),
            Biome.DESERT.getColor(),
            Biome.GLACIER.getColor(),
            Biome.EAU_PEU_PROFONDE.getColor(),
            Biome.EAU_PROFONDE.getColor()
    });

    /**
     * Nombre de clusters pour certains algorithmes
     */
    public static final int NB_CLUSTERS = 10;

    /**
     * Liste des algorithmes existants
     */
    public static final String[] ALGORITHMES = {"KMeans", "DBScan", "HAC", "Flou"};

    /**
     * Taille visuelle maximale d'une image
     */
    public static final int TAILLE_MAX = 500;

    /**
     * Image s'affichant au démarrage
     */
    public static final String IMAGE_DEFAUT = "img/planete1.jpg";

    /**
     * Ecart-type par défaut pour les algorithmes
     */
    public static final int ECART_TYPE = 5;

    /**
     * Taille d'une matrice pour les algorithmes en ayant besoin
     */
    public static final int TAILLE_MATRICE = 8;

    private int[] dernierResult;
    private Stage stage;
    private Rectangle imageTraitee;
    private Rectangle imageOriginale;
    private VBox containerBiomes;
    private String fichierCourant;
    private String algorithme;
    private boolean flouter;

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
        this.algorithme = "Flou";
        this.flouter = false;
        dernierResult = new int[]{};
    }

    /**
     * Traite et crée une nouvelle image à partir d'une autre
     * @param nouvFichier le chemin du fichier à traiter
     */
    public void traiter(String nouvFichier) {
        // Floute l'image avant la traiter
        String fichierTemp = getFichierCourant();
        if (flouter) {
            /*Gausien flou = new Gausien(getFichierCourant(), nouvFichier);
            flou.flouter(21, Modele.ECART_TYPE);*/

            FlouMoyenne flou = new FlouMoyenne(fichierTemp, nouvFichier);
            flou.flouter(3);

            // Le fichier sur lequel on se base est celui flouté
            fichierTemp = nouvFichier;
        }

        switch (this.algorithme) {
            case "DBScan" -> {
                DBScan dbScan = new DBScan(new NormeRedmean(), Modele.ECART_TYPE, Modele.TAILLE_MATRICE);
                int[][] data = OutilCouleur.convertTab(fichierTemp);
                int[] result = dbScan.run(data);
                dernierResult = result;
                ClusterImage.afficherClustersBiome(result, fichierTemp, nouvFichier, palette);
            }
            case "KMeans" -> {
                KMeans_v2 kMeans = new KMeans_v2(NB_CLUSTERS);
                int[][] tabImage = OutilCouleur.convertTab(fichierTemp);
                int[] result = kMeans.run(tabImage);
                dernierResult = result;
                ClusterImage.afficherClustersBiome(result, fichierTemp, nouvFichier, palette);
            }
            case  "HAC" -> {
                HAC hac = new HAC(new NormeRedmean(), 2);
                int[] result = hac.run(Objects.requireNonNull(OutilCouleur.convertTab(fichierTemp)));
                dernierResult = result;
                ClusterImage.afficherClustersBiome(result, fichierTemp, nouvFichier, palette);
            }
            default -> {
                FlouMoyenne flou = new FlouMoyenne(fichierTemp, nouvFichier);
                flou.flouter(Modele.ECART_TYPE);
            }
        }
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
    public void setBiomes(HashMap<String, Color> biomes) {
        containerBiomes.getChildren().clear();
        containerBiomes.getChildren().add(new Text("Biomes repérés :\n\n"));

        // Ajoute en légende la couleur de chaque biome et son nom
        biomes.forEach((nom, color) -> {
            HBox hb = new HBox();
            Rectangle couleur = new Rectangle(40, 25, color);
            couleur.setStroke(Color.BLACK);

            // Affiche un biome spécifique quand on clique sur l'un d'eux
            ControleurLegende c = new ControleurLegende(this);
            hb.setOnMouseClicked(c);

            hb.getChildren().add(couleur);
            hb.getChildren().add(new Text(nom));
            containerBiomes.getChildren().add(hb);
        });
    }

    // Des setters et des getters
    public void afficherBiome(String biome) {
        String nouvFichier = getFichierCourant() + "-traitee" + ".jpg";
        ClusterImage.afficherBiome(dernierResult, getFichierCourant(), nouvFichier, palette, Biome.valueOf(biome));

        Image image = new Image("file:"+nouvFichier, Modele.TAILLE_MAX, Modele.TAILLE_MAX, true, false);
        setImage(image, getImageTraitee());
    }
    public void setAlgorithme(String algo) {this.algorithme = algo;}
    public void setFlouter(boolean flouter) {this.flouter = flouter;}
    public void setFichierCourant(String f) {this.fichierCourant = f;}
    public String getFichierCourant() {return fichierCourant;}
    public Rectangle getImageTraitee() {return imageTraitee;}
    public Rectangle getImageOriginale() {return imageOriginale;}
    public VBox getContainerBiomes() {return containerBiomes;}
    public Stage getStage() {return stage;}
}