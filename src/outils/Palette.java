import java.awt.*;

public class Palette {
    public Color[] colors;
    public NormeCouleurs norme;

    public Palette(Color[] c, NormeCouleurs n) {
        colors = c;
        norme = n;
    }

    public Color getPlusProcheColor(Color c, Color[] tabColor) {
        Color couleurLaPlusProche = tabColor[0];
        for (Color color : tabColor) {
            if (norme.distanceCouleur(c, color) < norme.distanceCouleur(c, couleurLaPlusProche)) {
                couleurLaPlusProche = color;
            }
        }
        return couleurLaPlusProche;
    }
}
