package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class ControleurFlouter implements EventHandler<ActionEvent> {
    private Modele m;

    public ControleurFlouter(Modele m) {
        this.m = m;
    }

    public void handle(ActionEvent actionEvent) {
        CheckBox cbFlouter = (CheckBox) actionEvent.getSource();
        m.setFlouter(cbFlouter.isSelected());
    }
}