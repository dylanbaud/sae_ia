package main;

import algos.DBScan;
import algos.HAC;
import filtres.ClusterImage;
import norme.NormeBase;
import norme.NormeEuclidienne;
import norme.NormeRedmean;
import norme.OutilCouleur;

import java.awt.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        /*
        HAC hac = new HAC(new NormeRedmean());
        int[][] data = OutilCouleur.convertTab("img/16x16.png");
        assert data != null;
        System.out.println(Arrays.toString(hac.run(data)));
         */

        String filename2 = "img/16x16.png";
        HAC hac = new HAC(new NormeBase(), 2);
        int[][] data2 = OutilCouleur.convertTab(filename2);
        assert data2 != null;
        int[] result2 = hac.run(data2);
        ClusterImage.afficherClusters(result2, filename2, "img/16x16_hac.png");

    }
}
