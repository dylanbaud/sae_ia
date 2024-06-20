package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class ControleurAlgorithmes implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurAlgorithmes(Modele m) {
        this.m = m;
    }

    public void handle(ActionEvent actionEvent) {
        ComboBox<String> algo = (ComboBox) actionEvent.getSource();
        m.setAlgorithme(algo.getValue());
    }
}