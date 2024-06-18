import java.awt.*;

public class NormeCielabAmelioree implements NormeCouleurs {
    @Override
    public double distanceCouleur(Color c1, Color c2) {
        int[] tab1 = OutilCouleur.getTabColor(c1.getRGB());
        int[] tab2 = OutilCouleur.getTabColor(c2.getRGB());
        int[] lab1 = Main.rgb2lab(tab1[0], tab1[1], tab1[2]);
        int[] lab2 = Main.rgb2lab(tab2[0], tab2[1], tab2[2]);

        double deltaL = Math.pow(lab1[0] - lab2[0], 2);
        double deltaC = Math.sqrt(Math.pow(lab1[1], 2) + Math.pow(lab1[2], 2) - Math.pow(lab2[1], 2) - Math.pow(lab2[2], 2));
        double deltaH = Math.sqrt(Math.pow(lab1[1] - lab2[1], 2) + Math.pow(lab1[2] - lab2[2], 2) - Math.pow(deltaC, 2));
        double C1 = Math.sqrt(Math.pow(lab1[1], 2) + Math.pow(lab1[2], 2));
        double C2 = Math.sqrt(Math.pow(lab2[1], 2) + Math.pow(lab2[2], 2));
        double SC = 1 + 0.045 * C1;
        double SH = 1 + 0.015 * C1;

        return Math.sqrt(Math.pow(deltaL, 2) + Math.pow(deltaC / SC, 2) + Math.pow(deltaH / SH, 2));
    }
}