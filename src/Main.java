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
        int[][] data = OutilCouleur.convertTab("img/16x16.png");
        assert data != null;
        System.out.println(Arrays.toString(hac.run(data)));
    }
}
