package interface_graphique;

import interface_graphique.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

public class ControleurRetour implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurRetour(Modele m) {
        this.m = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String nouvFichier = m.getFichierCourant() + "-traitee" + ".jpg";
        Image image = new Image("file:"+nouvFichier, Modele.TAILLE_MAX, Modele.TAILLE_MAX, true, false);
        m.setImage(image, m.getImageTraitee());
    }
}