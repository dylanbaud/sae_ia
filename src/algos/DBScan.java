package algos;

import norme.NormeCouleurs;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBScan implements Algorithme {

    private NormeCouleurs norme;
    private double eps;
    private int minPts;

    private int[] result;

    private boolean[] traites;

    public DBScan(NormeCouleurs n, double e, int mp){
        norme = n;
        eps = e;
        minPts = mp;
    }

    public int[] run(int[][] tabCouleurs){
        int cluster = 0;
        result = new int[tabCouleurs.length];
        traites = new boolean[tabCouleurs.length];

        for(int n = 0; n < tabCouleurs.length; n++){
            if(!traites[n]){
                traites[n] = true;
                ArrayList<Integer> region = regionQuery(tabCouleurs, n);
                if(region.size() >= minPts){
                    cluster += 1;
                    expandCluster(tabCouleurs, n, cluster, region);
                } else {
                    result[n] = -1;
                }
            }
        }

        return result;
    }

    private void expandCluster(int[][] tabCouleurs, int n, int cluster, ArrayList<Integer> region){
        result[n] = cluster;
        ArrayList<Integer> newPoints = new ArrayList<>(region);
        for(int i = 0; i < newPoints.size(); i++){
            int pointIndex = newPoints.get(i);
            if(!traites[pointIndex]){
                traites[pointIndex] = true;
                ArrayList<Integer> regionI = regionQuery(tabCouleurs, pointIndex);

                if(regionI.size() >= minPts){
                    for(int ind : regionI){
                        if(!newPoints.contains(ind)){
                            newPoints.add(ind);
                        }
                    }
                }
            }

            if(result[pointIndex] == 0 || result[pointIndex] == -1){
                result[pointIndex] = cluster;
            }
        }
    }

    private ArrayList<Integer> regionQuery(int[][] tabCouleurs, int n){
        ArrayList<Integer> region = new ArrayList<>();
        int[] point1 = tabCouleurs[n];

        Color c1 = new Color(point1[0], point1[1], point1[2]);

        for(int i = 0; i < tabCouleurs.length; i++){
            if(i != n){

                int[] point2 = tabCouleurs[i];

                if(distance(point1, point2)<=eps) {
                    Color c2 = new Color(point2[0], point2[1], point2[2]);
                    if (norme.distance(c1, c2) <= eps) {
                        region.add(i);
                    }
                }
            }
        }
        return region;
    }
    private double distance(int[] point1, int[] point2){
        return Math.pow(point1[3] - point2[3], 2) + Math.pow(point1[4] - point2[4], 2);
    }
}

