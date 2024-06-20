package interface_graphique;

import filtres.Biome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

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
            Image image = new Image("file:"+nouvFichier, Modele.TAILLE_MAX, Modele.TAILLE_MAX, true, false);
            m.setImage(image, m.getImageTraitee());

            // Met à jour la liste de biomes également
            HashMap<String, Color> biomes = new HashMap<>();

            for (Biome t : Biome.values()) {
                int[] rgb = t.getValues();
                biomes.put(t.name(), Color.rgb(rgb[0], rgb[1], rgb[2], 1));
            }
            m.setBiomes(biomes);
        }
        m.getStage().sizeToScene(); // Adapter la fenêtre aux éléments présents
    }
}