package main;

import algos.DBScan;
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


        String filename="img/150x150.png";
        DBScan dbScan=new DBScan(new NormeRedmean(), 10, 8);

        int[][] data=OutilCouleur.convertTab(filename);
        int[] result=dbScan.run(data);

        ClusterImage.afficherClusters(result, filename, "img/DBSCAN_IMAGE.png");

        System.out.println(Arrays.toString(result));
    }
}
