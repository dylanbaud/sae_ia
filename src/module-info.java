module interface_graphique {
    requires javafx.base;
    requires javafx.controls;
    requires java.desktop;

    opens interface_graphique to javafx.fxml;
    exports interface_graphique;
}