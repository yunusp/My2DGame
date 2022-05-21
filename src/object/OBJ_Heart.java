package object;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    UtilityTools utool = new UtilityTools();
    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        name = "Heart";

        try {
            image =     ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));
            image2 =    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image3 =    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_blank.png")));
            image = utool.scaleImage(image,gp.tileSize, gp.tileSize);
            image2 = utool.scaleImage(image2,gp.tileSize, gp.tileSize);
            image3 = utool.scaleImage(image3,gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
