import flou.FlouMoyenne;

public class Main {

    public static void main(String[] args) {
        FlouMoyenne flouMoyenne = new FlouMoyenne("img/planete1.jpg", "img/flouMoyenne.jpg");
        flouMoyenne.flouter(21);
    }
}
