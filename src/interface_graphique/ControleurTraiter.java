package interface_graphique;

import filtres.FlouMoyenne;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ControleurTraiter implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurTraiter(Modele m) {
        this.m = m;
    }

    public void handle(ActionEvent actionEvent) {
        Button btnTraiter = (Button)actionEvent.getSource();

        // Remplacer l'image courante par une image
        // avec des biomes et en plus mettre à jour la légende
        if (btnTraiter.getText().equals("Repérer des biomes")) {
            String nouvFichier = m.getFichierCourant() + "-traitee" + ".jpg";
            m.traiter(nouvFichier);

            // Le premier true indique que l'on préserve le ratio de l'image
            Image image = new Image("file:"+nouvFichier, Constantes.TAILLE_MAX, Constantes.TAILLE_MAX, true, false);
            m.setImage(image, m.getImageTraitee());
        }
        m.getStage().sizeToScene(); // Adapter la fenêtre aux éléments présents
    }
}