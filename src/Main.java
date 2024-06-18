<<<<<<< HEAD
import flou.FlouMoyenne;
import flou.Gausien;
=======
import flous.FlouMoyenne;
>>>>>>> b267fc5a492393189342c607c41742f4634f61e8

public class Main {

    public static void main(String[] args) {
        //FlouMoyenne flouMoyenne = new FlouMoyenne("img/planete1.jpg", "img/flouMoyenne.jpg");
        //flouMoyenne.flouter(21);

        Gausien gausien = new Gausien("img/planete1.jpg", "img/flouGausien2.jpg");
        gausien.flouter(27, 20);
    }
}
