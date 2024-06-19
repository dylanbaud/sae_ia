package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ControleurOuvrir implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurOuvrir(Modele m) {
        this.m = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        MenuItem miOuvrir = (MenuItem)actionEvent.getSource();

        if (miOuvrir.getText().equals("Ouvrir")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionnez une image d'exoplanète");
            fileChooser.setInitialDirectory(new File("img"));

            File fichier = fileChooser.showOpenDialog(m.getStage());
            if (fichier != null) {
                // Affiche l'image visuellement
                InputStream stream = null;
                try {
                    String chemin = fichier.getAbsolutePath();
                    stream = new FileInputStream(chemin);
                    m.setFichierCourant(chemin);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Image image = new Image(stream);
                m.getImageView().setImage(image);
            }
        }
        m.getStage().sizeToScene();
    }
}