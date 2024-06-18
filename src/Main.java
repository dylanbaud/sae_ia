import flou.FlouMoyenne;
import flou.Gausien;

public class Main {

    public static void main(String[] args) {
        FlouMoyenne flouMoyenne = new FlouMoyenne("img/planete1.jpg");
        flouMoyenne.flouter(3, 3);

        Gausien.flouter("img/planete1.jpg", "img/result/gausien1.jpg", 5);
    }
}
