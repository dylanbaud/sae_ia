package interface_graphique;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Containers
        VBox principal = new VBox();
        HBox containerImages = new HBox();
        VBox containerImgOriginal = new VBox();
        VBox containerImgTraitee = new VBox();
        VBox containerBiomes = new VBox();

        // Barre de menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Fichier");
        MenuItem ouvrir = new MenuItem("Ouvrir");
        menu.getItems().add(ouvrir);
        menuBar.getMenus().add(menu);

        // Images
        Image image = new Image("file:"+Constantes.IMAGE_DEFAUT, Constantes.TAILLE_MAX, Constantes.TAILLE_MAX, true, false);
        Rectangle imgTraitee = new Rectangle(Constantes.TAILLE_MAX, Constantes.TAILLE_MAX);
        Rectangle imgOriginale = new Rectangle(Constantes.TAILLE_MAX, Constantes.TAILLE_MAX);

        // Modele
        Modele modele = new Modele(stage, imgTraitee, imgOriginale, containerBiomes, Constantes.IMAGE_DEFAUT);

        // Affiche l'image par défaut
        modele.setImage(image, modele.getImageOriginale());
        modele.setImage(image, modele.getImageTraitee());

        // Contrôleurs
        Button btnTraiter = new Button("Repérer des biomes");

        ControleurOuvrir contrOuvrir = new ControleurOuvrir(modele);
        ControleurTraiter contrTraiter = new ControleurTraiter(modele);
        ouvrir.setOnAction(contrOuvrir);
        btnTraiter.setOnAction(contrTraiter);

        // CSS pour ceux qui ne savent pas en faire
        principal.setSpacing(10);
        principal.setAlignment(Pos.CENTER);
        containerImgOriginal.setSpacing(15);
        containerImgTraitee.setSpacing(15);
        containerImages.setSpacing(20);
        containerImages.setPadding(new Insets(20));

        // Ajout des widgets
        containerBiomes.getChildren().addAll(new Text("Biomes repérés : "), new HBox(new Rectangle(20, 15, new Color(0.5, 0.2, 0.4, 1))), new Text("Placeholder"), new HBox(new Rectangle(20, 15, new Color(0.1, 0.7, 0.4, 1))), new Text("Placeholder"));
        containerImgOriginal.getChildren().addAll(new Text("Image originale : "), imgOriginale);
        containerImgTraitee.getChildren().addAll(new Text("Image traitée : "), imgTraitee);
        containerImages.getChildren().addAll(containerImgOriginal, containerImgTraitee, containerBiomes);
        principal.getChildren().addAll(menuBar, btnTraiter, containerImages);

        // Main
        Scene scene = new Scene(principal);

        // Ajout du CSS au cas où
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.getIcons().add(new Image("file:img/16x16.png"));
        stage.setTitle("Détection de biomes sur des exoplanètes");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {Application.launch(args);}
}