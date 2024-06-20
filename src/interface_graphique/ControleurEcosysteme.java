package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurEcosysteme implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurEcosysteme(Modele m) {
        this.m = m;
    }

    public void handle(ActionEvent actionEvent) {

    }
}