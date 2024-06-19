package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurTraiter implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurTraiter(Modele m) {
        this.m = m;
    }

    public void handle(ActionEvent actionEvent) {
        Button btnTraiter = (Button)actionEvent.getSource();

        if (btnTraiter.getText().equals("Repérer des biomes")) {
            System.out.println("à faire");
            // TODO
            // Remplacer l'image courante par une image
            // avec des biomes et en plus mettre à jour la légende
        }
    }
}