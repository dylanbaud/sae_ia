package main;

import algos.DBScan;
import algos.HAC;
import algos.KMeans_v2;
import filtres.Biome;
import filtres.ClusterImage;
import norme.*;

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

        String filename = "img/flouGausien.jpg";
        int[][] data = OutilCouleur.convertTab(filename);
        assert data != null;
        KMeans_v2 kmeans = new KMeans_v2(10);
        int[] clusters = kmeans.run(data);

        Palette palette = new Palette(new Color[]{Biome.TUNDRA.getColor(), Biome.TAIGA.getColor(), Biome.FORET_TEMPEREE.getColor(), Biome.FORET_TROPICALE.getColor(), Biome.SAVANE.getColor(), Biome.PRAIRIE.getColor(), Biome.DESERT.getColor(), Biome.GLACIER.getColor(), Biome.EAU_PEU_PROFONDE.getColor(), Biome.EAU_PROFONDE.getColor()});

        ClusterImage.afficherClustersBiome(clusters, filename, "img/kmeans.jpg", palette);
        ClusterImage.afficherBiome(clusters, filename, "img/kmeans_savane.jpg", palette, Biome.SAVANE);
    }
}
