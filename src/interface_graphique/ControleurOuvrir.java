package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class ControleurOuvrir implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurOuvrir(Modele m) {
        this.m = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        MenuItem miOuvrir = (MenuItem)actionEvent.getSource();

        // Remplace l'image courante par une autre, sans la traiter
        if (miOuvrir.getText().equals("Ouvrir")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionnez une image d'exoplanète");
            fileChooser.setInitialDirectory(new File("img"));

            File fichier = fileChooser.showOpenDialog(m.getStage());
            if (fichier != null) {
                // Affiche l'image visuellement, et change l'image courante
                String chemin = fichier.getAbsolutePath();
                m.setFichierCourant(chemin);

                // Le premier true indique que l'on préserve le ratio de l'image
                Image image = new Image("file:"+chemin, Modele.TAILLE_MAX, Modele.TAILLE_MAX, true, false);
                m.setImage(image, m.getImageOriginale());
            }
        }
        m.getStage().sizeToScene(); // Adapter la fenêtre aux éléments présents
    }
}