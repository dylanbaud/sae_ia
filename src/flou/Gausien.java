import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gausien{
    public void flouter(String fileName, String fileOutputName, double ecartType){
        File file=new File(fileName);

        BufferedImage bufferedImage=null;

        try {
            bufferedImage=ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int lengthX=bufferedImage.getWidth();
        int lengthY=bufferedImage.getHeight();

        BufferedImage bufferedOutput= new BufferedImage(lengthX, lengthY, BufferedImage.TYPE_3BYTE_BGR);


        int[] pixelCentral={lengthX/2, lengthY/2};


        for(int y=0; y<lengthY; y++){
            for(int x=0; x<lengthX; x++){
                double distX=Math.sqrt(Math.pow(x-pixelCentral[0], 2));
                double distY=Math.sqrt(Math.pow(y-pixelCentral[1], 2));

                double coef=genererCoef(distX, distY, ecartType);

                int newRGB=(int)(bufferedImage.getRGB(x, y)*coef);
            }
        }

        try {
            ImageIO.write(bufferedOutput, "PNG", new File(fileOutputName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double genererCoef(double x, double y, double ecartType){
        double res=Math.pow( 
            (1/(2*Math.PI*Math.pow(ecartType, 2))),
            Math.exp(  
                -( 
                    (Math.pow(x,2)+ Math.pow(y, 2))/
                    (2*Math.pow(ecartType,2))
                )
            )
        );

        return res;
    }
}