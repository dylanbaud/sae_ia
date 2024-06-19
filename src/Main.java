import algos.HAC;
import flous.Gausien;
import flous.FlouMoyenne;
import norme.NormeRedmean;
import norme.OutilCouleur;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        HAC hac = new HAC(new NormeRedmean());
        int[][] data = OutilCouleur.convertTab("img/flouMoyenne.jpg");
    }
}
