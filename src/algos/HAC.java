package algos;

import norme.NormeCouleurs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HAC implements Algorithme {

    private NormeCouleurs normeCouleurs;
    private List<int[]> history;
    private int nbDecoupe;

    public HAC(NormeCouleurs normeCouleurs, int nbDecoupe) {
        this.normeCouleurs = normeCouleurs;
        this.history = new ArrayList<>();
        this.nbDecoupe = nbDecoupe;
    }

    public int[] run(int[][] data) {
        // Initialisation
        int n = data.length;
        int[] clusters = new int[n];
        for (int i = 0; i < n; i++) {
            clusters[i] = i + 1;
        }

        // Matrice des distances
        double[][] distances = initialDistance(data);

        // Boucle principale
        while (Arrays.stream(clusters).distinct().count() > 1) {

            // Trouver la paire la plus proche
            int[] pair = findPair(clusters, distances);

            // Fusion des deux clusters
            int newCluster = Math.min(pair[0], pair[1]);
            int oldCluster = Math.max(pair[0], pair[1]);

            for (int i = 0; i < clusters.length; i++) {
                if (clusters[i] == oldCluster) {
                    clusters[i] = newCluster;
                }
            }

            // Mise à jour de la matrice des distances
            updateDistances(clusters, distances, newCluster, oldCluster);

            // Sauvegarde de l'état
            history.add(Arrays.copyOf(clusters, clusters.length));
        }
        return decoupe(nbDecoupe);
    }

    private double[][] initialDistance(int[][] data) {
        int n = data.length;
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances[i][j] = distances[j][i] = normeCouleurs.distance(createColor(data[i]), createColor(data[j]));
            }
        }
        return distances;
    }

    private void updateDistances(int[] clusters, double[][] distances, int newCluster, int oldCluster) {
        for (int i = 0; i < clusters.length; i++) {
            if (clusters[i] == newCluster) {
                for (int j = 0; j < clusters.length; j++) {
                    if (clusters[j] != newCluster) {
                        distances[i][j] = distances[j][i] = Math.min(distances[i][j], distances[Math.max(i, j)][Math.min(i, j)]);
                    }
                }
            }
        }
    }

    private int[] findPair(int[] clusters, double[][] distances) {
        int[] pair = new int[2];
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < clusters.length; i++) {
            for (int j = i + 1; j < clusters.length; j++) {
                if (distances[i][j] < minDistance && clusters[i] != clusters[j]) {
                    minDistance = distances[i][j];
                    pair[0] = clusters[i];
                    pair[1] = clusters[j];
                }
            }
        }
        return pair;
    }

    private Color createColor(int[] data) {
        return new Color(data[0], data[1], data[2]);
    }

    private int[] decoupe(int k) {
        return history.get(history.size() - k);
    }


}
