package norme;

import java.awt.*;

public class NormeRedmean implements NormeCouleurs {

    public double distance(Color c1, Color c2) {
        int[] tab1 = OutilCouleur.getTabColor(c1.getRGB());
        int[] tab2 = OutilCouleur.getTabColor(c2.getRGB());
        double rmean = (double) (tab1[0] + tab2[0]) / 2;
        double deltaR = tab1[0] - tab2[0];
        double deltaG = tab1[1] - tab2[1];
        double deltaB = tab1[2] - tab2[2];
        return Math.sqrt((2 + rmean / 256) * Math.pow(deltaR, 2) + 4 * Math.pow(deltaG, 2) + (2 + (255 - rmean) / 256) * Math.pow(deltaB, 2));
    }
}
