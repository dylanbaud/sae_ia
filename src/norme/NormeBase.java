import java.awt.*;

public class NormeBase implements NormeCouleurs {

    public double distance(Color c1, Color c2) {
        int[] tab1 = OutilCouleur.getTabColor(c1.getRGB());
        int[] tab2 = OutilCouleur.getTabColor(c2.getRGB());
        return Math.pow(tab1[0] - tab2[0], 2) + Math.pow(tab1[1] - tab2[1], 2) + Math.pow(tab1[2] - tab2[2], 2);
    }
}
