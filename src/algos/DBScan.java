package algos;

import norme.NormeCouleurs;

import java.awt.*;
import java.util.ArrayList;

public class DBScan {
    private NormeCouleurs norme;

    /**
     * taille du rayon voisinage
     */
    private double eps;

    /**
     * nombre minimum d'info dans le voisinage pour être considéré comme core point
     */
    private int minPts;

    public DBScan(NormeCouleurs n, double e, int m){
        norme=n;
        eps=e;
        minPts=m;
    }
    public int[] run(int[][] tabCouleurs){
        int cluster=0;
        int[] result=new int[tabCouleurs.length];
        for(int i=0; i< tabCouleurs.length; i++) {
            if(result[i]==0) {
                ArrayList<Integer> region = regionQuery(tabCouleurs, i);
                if (region.size() >= minPts) {
                    cluster += 1;
                    expandCluster(tabCouleurs, i, region, cluster, result);
                } else {
                    result[i] = -1;
                }
            }
        }
        return result;
    }

    private ArrayList<Integer> regionQuery(int[][] tabCouleurs, int indice){
        ArrayList<Integer> region=new ArrayList<>();


        int[] point1=tabCouleurs[indice];
        Color c1=new Color(point1[0], point1[1], point1[2]);

        for(int i=0; i<tabCouleurs.length; i++){
            if(i!=indice) {
                int[] point2 = tabCouleurs[i];
                Color c2=new Color(point2[0], point2[1], point2[2]);
                double distance=norme.distance(c1, c2);

                if(distance<=eps){
                    region.add(i);
                }
            }
        }

        return region;
    }


    void expandCluster(int[][] tabCouleurs, int indice, ArrayList<Integer> region, int cluster, int[] result){
        result[indice]=cluster;



        for (int i=0; i<region.size(); i++){
            int indiceCourant=region.get(i);

            if(result[indiceCourant]==0){
                result[indiceCourant]=-1;

                ArrayList<Integer> regionI=regionQuery(tabCouleurs, indiceCourant);

                if(regionI.size()>minPts){
                    for(int ind : regionI){
                        region.add(ind);
                    }
                }
            }

            if(result[indiceCourant]==0 || result[indiceCourant]==-1){
                result[indiceCourant]=cluster;
            }
        }
    }
}
