import flous.Gausien;
import flous.FlouMoyenne;

public class Main {

    public static void main(String[] args) {
        //FlouMoyenne flouMoyenne = new FlouMoyenne("img/planete1.jpg", "img/flouMoyenne.jpg");
        //flouMoyenne.flouter(21);

        Gausien gausien = new Gausien("img/planete1.jpg", "img/flouGausien2.jpg");
        gausien.flouter(27, 20);
    }
}
