package algos;

import norme.NormeCouleurs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HAC {

    private NormeCouleurs normeCouleurs;

    public HAC(NormeCouleurs normeCouleurs) {
        this.normeCouleurs = normeCouleurs;
    }

    public int[] run(int[][] data) {
        //Initialisation
        int n = data.length;
        List<int[]> clusters = new ArrayList<>();
        for (int i = 1; i < n + 1; i++) {
            clusters.add(new int[]{i});
        }

        //Matrice des distances
        double[][] distances = initialDistance(data);

        //Boucle principale
        while (clusters.size() > 1) {
            //Trouver la paire la plus proche
            int[] pair = findPair(data, clusters);

            //Fusion des deux clusters
            int[] newClusters = merge(clusters.get(pair[0]), clusters.get(pair[1]));

            //Mise à jour de la liste des clusters et des distances
            clusters.remove(pair[1]);
            clusters.remove(pair[0]);
            clusters.add(newClusters);

            //Mise à jour de la matrice des distances
        }
        return clusters.get(0);
    }


    private double[][] initialDistance(int[][] data) {
        int n = data.length;
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances[i][j] = normeCouleurs.distance(createColor(data[i]), createColor(data[j]));
            }
        }
        System.out.println(Arrays.toString(distances[0]));
        return distances;
    }

    private int[] findPair(int[][] data, List<int[]> clusters) {
        double minDistance = Double.MAX_VALUE;
        int[] closestPair = new int[2];
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                double dist = normeCouleurs.distance(createColor(data[i]), createColor(data[j]));
                if (dist < minDistance) {
                    minDistance = dist;
                    closestPair[0] = i;
                    closestPair[1] = j;
                }
            }
        }
        return closestPair;
    }

    private int[] merge(int[] cluster1, int[] cluster2) {
        int[] newClusters = new int[cluster1.length + cluster2.length];
        System.arraycopy(cluster1, 0, newClusters, 0, cluster1.length);
        System.arraycopy(cluster2, 0, newClusters, cluster1.length, cluster2.length);
        return newClusters;
    }

    private Color createColor(int[] data) {
        return new Color(data[0], data[1], data[2]);
    }
}
