package filtres;

import java.awt.*;

public enum Biome {
    TUNDRA(new int[]{71, 70, 61}),
    TAIGA(new int[]{43, 50, 35}),
    FORET_TEMPEREE(new int[]{59, 66, 43}),
    FORET_TROPICALE(new int[]{46, 64, 34}),
    SAVANE(new int[]{84, 106, 70}),
    PRAIRIE(new int[]{104, 95, 82}),
    DESERT(new int[]{152, 140, 120}),
    GLACIER(new int[]{200, 200, 200}),
    EAU_PEU_PROFONDE(new int[]{49, 83, 100}),
    EAU_PROFONDE(new int[]{12, 31, 47});

    private final int[] values;

    Biome(int[] values) {
        this.values = values;
    }

    public int[] getValues() {
        return values;
    }

    public Color getColor() {
        return new Color(values[0], values[1], values[2]);
    }
}