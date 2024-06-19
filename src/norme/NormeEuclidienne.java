package norme;

import java.awt.*;

public class NormeEuclidienne implements NormeCouleurs {


    @Override
    public double distance(Color c1, Color c2) {
        int[] tab1 = OutilCouleur.getTabColor(c1.getRGB());
        int[] tab2 = OutilCouleur.getTabColor(c2.getRGB());
        int[] lab1 = OutilCouleur.rgb2lab(tab1[0], tab1[1], tab1[2]);
        int[] lab2 = OutilCouleur.rgb2lab(tab2[0], tab2[1], tab2[2]);
        return Math.sqrt(Math.pow(lab1[0] - lab2[0], 2) + Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2));
    }
}
