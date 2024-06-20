package norme;

import java.awt.*;

public class Palette {

    private final Color[] couleurs;

    public Palette(Color[] couleurs) {
        this.couleurs = couleurs;
    }

    public Color getPlusProche(Color c, NormeCouleurs normeCouleurs) {
        Color minColor = couleurs[0];
        double distanceMin = normeCouleurs.distance(c, minColor);
        for (int i = 1; i < couleurs.length; i++) {
            double distance = normeCouleurs.distance(c, couleurs[i]);
            if (distance < distanceMin) {
                minColor = couleurs[i];
                distanceMin = distance;
            }
        }
        return minColor;
    }
}
