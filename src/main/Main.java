package main;

import algos.DBScan;
import norme.NormeRedmean;
import norme.OutilCouleur;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        /*
        HAC hac = new HAC(new NormeRedmean());
        int[][] data = OutilCouleur.convertTab("img/16x16.png");
        assert data != null;
        System.out.println(Arrays.toString(hac.run(data)));
         */

        DBScan dbScan=new DBScan(new NormeRedmean(), 8, 6);
        int[][] data=OutilCouleur.convertTab("img/planete5.jpg");
        System.out.println(Arrays.toString(dbScan.run(data)));
    }
}
