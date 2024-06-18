package outil;

public class OutilCouleur {

    public static int[] getTabColor(int c) {
        int blue = c & 0xff;
        int green = (c & 0xff00) >> 8;
        int red = (c & 0xff0000) >> 16;
        return new int[]{red, green, blue};
    }
}
