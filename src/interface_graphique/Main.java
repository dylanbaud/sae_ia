package interface_graphique;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main extends Application {

    final String IMAGE_DEFAUT = "img/planete1.jpg";

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // Création containers
        VBox principal = new VBox();
        HBox container = new HBox();
        VBox biomes = new VBox();

        // Barre de menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Fichier");
        MenuItem ouvrir = new MenuItem("Ouvrir");
        menu.getItems().add(ouvrir);
        menuBar.getMenus().add(menu);

        // Image
        InputStream stream = new FileInputStream(IMAGE_DEFAUT);
        Image image = new Image(stream);
        ImageView iv = new ImageView();

        // Modele
        Modele modele = new Modele(stage, iv, biomes, IMAGE_DEFAUT);
        iv.setFitWidth(700);
        iv.setPreserveRatio(true);
        iv.setImage(image);

        // Controleurs
        Button btnTraiter = new Button("Repérer des biomes");

        ControleurOuvrir contrOuvrir = new ControleurOuvrir(modele);
        ControleurTraiter contrTraiter = new ControleurTraiter(modele);
        ouvrir.setOnAction(contrOuvrir);
        btnTraiter.setOnAction(contrTraiter);

        // Ajout des widgets
        biomes.getChildren().add(new Text("Biomes repérés : "));

        container.getChildren().addAll(iv, biomes);
        principal.getChildren().addAll(menuBar, btnTraiter, container);

        // main.Main
        Scene scene = new Scene(principal);

        // Ajout du CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Détection de biomes sur des exoplanètes");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {Application.launch(args);}
}