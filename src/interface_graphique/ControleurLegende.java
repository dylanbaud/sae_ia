package interface_graphique;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ControleurLegende implements EventHandler<MouseEvent> {
    private Modele m;

    public ControleurLegende(Modele m) {
        this.m = m;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        HBox hb = (HBox) mouseEvent.getSource();

        Rectangle couleur = (Rectangle) hb.getChildren().get(0);
        Text nom = (Text) hb.getChildren().get(1);

        System.out.println(couleur.getFill());
        System.out.println(nom.getText());

        // N'afficher que celui-là
    }
}