package algos;

import norme.NormeCouleurs;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBScan implements Algorithme {

    private Set<Integer> traites;
    private NormeCouleurs norme;
    private double eps;
    private int minPts;

    public DBScan(NormeCouleurs n, double e, int m) {
        norme = n;
        eps = e;
        minPts = m;
        traites = new HashSet<>();
    }

    public int[] run(int[][] tabCouleurs) {
        traites.clear();
        int cluster = 0;
        int[] result = new int[tabCouleurs.length];

        for (int i = 0; i < tabCouleurs.length; i++) {
            if (!traites.contains(i)) {
                traites.add(i);
                List<Integer> region = regionQuery(tabCouleurs, i);
                if (region.size() >= minPts) {
                    cluster++;
                    expandCluster(tabCouleurs, i, region, cluster, result);
                } else {
                    result[i] = -1;
                }
            }
        }
        return result;
    }

    private List<Integer> regionQuery(int[][] tabCouleurs, int indice) {
        List<Integer> region = new ArrayList<>();
        int[] point1 = tabCouleurs[indice];
        Color c1 = new Color(point1[0], point1[1], point1[2]);

        for (int i = 0; i < tabCouleurs.length; i++) {
            if (i != indice) {
                int[] point2 = tabCouleurs[i];
                Color c2 = new Color(point2[0], point2[1], point2[2]);
                double distance = norme.distance(c1, c2);

                if (distance <= eps) {
                    region.add(i);
                }
            }
        }

        return region;
    }

    private void expandCluster(int[][] tabCouleurs, int indice, List<Integer> region, int cluster, int[] result) {
        result[indice] = cluster;
        List<Integer> newPoints = new ArrayList<>(region);

        while (!newPoints.isEmpty()) {
            int indiceCourant = newPoints.remove(0);

            if (!traites.contains(indiceCourant)) {
                traites.add(indiceCourant);
                List<Integer> regionI = regionQuery(tabCouleurs, indiceCourant);

                if (regionI.size() >= minPts) {
                    for (int ind : regionI) {
                        if (!newPoints.contains(ind)) {
                            newPoints.add(ind);
                        }
                    }
                }
            }

            if (result[indiceCourant] == 0) {
                result[indiceCourant] = cluster;
            }
        }
    }
}
