package interface_graphique;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

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
        Image image = new Image("file:"+Modele.IMAGE_DEFAUT, Modele.TAILLE_MAX, Modele.TAILLE_MAX, true, false);
        Rectangle imgTraitee = new Rectangle(Modele.TAILLE_MAX, Modele.TAILLE_MAX);
        Rectangle imgOriginale = new Rectangle(Modele.TAILLE_MAX, Modele.TAILLE_MAX);

        // Modele
        Modele modele = new Modele(stage, imgTraitee, imgOriginale, containerBiomes, Modele.IMAGE_DEFAUT);

        // Affiche l'image par défaut
        modele.setImage(image, modele.getImageOriginale());
        modele.setImage(image, modele.getImageTraitee());

        // Contrôleurs
        CheckBox cbFlouter = new CheckBox("Pré flouter l'image");
        cbFlouter.setIndeterminate(false);

        ComboBox<String> comboBoxAlgos = new ComboBox<>(FXCollections.observableArrayList(Modele.ALGORITHMES));
        Button btnTraiter = new Button("Repérer des biomes");

        Text labelTemps = new Text("(Peut prendre du temps selon la taille de l'image)");
        labelTemps.setFont(Font.font (12));

        ControleurOuvrir contrOuvrir = new ControleurOuvrir(modele);
        ControleurTraiter contrTraiter = new ControleurTraiter(modele);
        ControleurAlgorithmes contrAlgorithmes = new ControleurAlgorithmes(modele);
        ControleurFlouter contrFlouter = new ControleurFlouter(modele);
        ouvrir.setOnAction(contrOuvrir);
        btnTraiter.setOnAction(contrTraiter);
        comboBoxAlgos.setOnAction(contrAlgorithmes);
        cbFlouter.setOnAction(contrFlouter);

        // CSS pour ceux qui ne savent pas en faire
        principal.setSpacing(10);
        principal.setAlignment(Pos.CENTER);
        containerImgOriginal.setSpacing(15);
        containerImgTraitee.setSpacing(15);
        containerImages.setSpacing(20);
        containerImages.setPadding(new Insets(20));

        // Ajout des widgets
        modele.setBiomes(new HashMap<>());
        containerImgOriginal.getChildren().addAll(new Text("Image originale"), imgOriginale);
        containerImgTraitee.getChildren().addAll(new Text("Image traitée"), imgTraitee);
        containerImages.getChildren().addAll(containerImgOriginal, containerImgTraitee, containerBiomes);
        principal.getChildren().addAll(menuBar, comboBoxAlgos, cbFlouter, btnTraiter, labelTemps, containerImages);

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