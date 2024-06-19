package main;

import algos.DBScan;
import algos.HAC;
import flous.Gausien;
import flous.FlouMoyenne;
import norme.NormeRedmean;
import norme.OutilCouleur;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        /*
        HAC hac = new HAC(new NormeRedmean());
        int[][] data = OutilCouleur.convertTab("img/16x16.png");
        assert data != null;
        System.out.println(Arrays.toString(hac.run(data)));
         */

        DBScan dbScan=new DBScan(new NormeRedmean(), 2, 15);
        int[][] data=OutilCouleur.convertTab("img/antoine.png");
        System.out.println(Arrays.toString(dbScan.run(data)));
    }
}
