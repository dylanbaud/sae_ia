package algos;

import norme.NormeCouleurs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HAC {

    private NormeCouleurs normeCouleurs;
    private List<List<int[]>> history;

    public HAC(NormeCouleurs normeCouleurs) {
        this.normeCouleurs = normeCouleurs;
        this.history = new ArrayList<>();
    }

    public int[] run(int[][] data) {
        // Initialisation
        int n = data.length;
        List<int[]> clusters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            clusters.add(new int[]{i});
        }

        // Historique des clusters
        history.add(new ArrayList<>(clusters));

        // Matrice des distances
        double[][] distances = initialDistance(data);

        // Boucle principale
        while (clusters.size() > 1) {
            // Trouver la paire la plus proche
            int[] pair = findPair(clusters, distances);

            // Fusion des deux clusters
            int[] newCluster = merge(clusters.get(pair[0]), clusters.get(pair[1]));

            // Mise à jour de la liste des clusters
            clusters.remove(pair[1]);
            clusters.set(pair[0], newCluster);

            // Sauvegarde de l'historique
            history.add(new ArrayList<>(clusters));

            // Mise à jour de la matrice des distances
            updateDistance(distances, clusters, pair[0], pair[1]);
        }
        return clusters.get(0);
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

    private void updateDistance(double[][] distances, List<int[]> clusters, int idx1, int idx2) {
        for (int i = 0; i < clusters.size(); i++) {
            if (i != idx1 && i != idx2) {
                double newDistance = calculateDistance(clusters.get(idx1), clusters.get(i), distances);
                distances[idx1][i] = distances[i][idx1] = newDistance;
            }
        }
    }

    private double calculateDistance(int[] cluster1, int[] cluster2, double[][] distances) {
        double distance = 0;
        for (int i : cluster1) {
            for (int j : cluster2) {
                distance += distances[i][j];
            }
        }
        return distance / (cluster1.length * cluster2.length);
    }

    private int[] findPair(List<int[]> clusters, double[][] distances) {
        int[] pair = new int[2];
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                double distance = calculateDistance(clusters.get(i), clusters.get(j), distances);
                if (distance < minDistance) {
                    minDistance = distance;
                    pair[0] = i;
                    pair[1] = j;
                }
            }
        }
        return pair;
    }

    private int[] merge(int[] cluster1, int[] cluster2) {
        int[] newCluster = new int[cluster1.length + cluster2.length];
        System.arraycopy(cluster1, 0, newCluster, 0, cluster1.length);
        System.arraycopy(cluster2, 0, newCluster, cluster1.length, cluster2.length);
        return newCluster;
    }

    private Color createColor(int[] data) {
        return new Color(data[0], data[1], data[2]);
    }

    public List<int[]> decoupe(int k) {
        for (int i = history.size() - 1; i >= 0; i--) {
            if (history.get(i).size() == k) {
                return history.get(i);
            }
        }
        return null;
    }
}
