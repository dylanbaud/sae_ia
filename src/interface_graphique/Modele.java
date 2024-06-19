package interface_graphique;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Modele {
    private String fichierCourant;
    private ImageView imageView;
    private Stage stage;
    private VBox biomes;

    public Modele(Stage s, ImageView iv, VBox biomes, String fichierCourant) {
        this.stage = s;
        this.imageView = iv;
        this.biomes = biomes;
        this.fichierCourant = fichierCourant;
    }

    public void setFichierCourant(String f) {
        this.fichierCourant = f;
    }

    public String getFichierCourant() {return fichierCourant;}
    public ImageView getImageView() {return imageView;}
    public VBox getBiomes() {return biomes;}
    public Stage getStage() {return stage;}
}