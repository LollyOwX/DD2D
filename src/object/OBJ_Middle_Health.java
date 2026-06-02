package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Middle_Health extends SuperObject{
    GamePanel gp;
    public OBJ_Middle_Health(GamePanel gp) {
        this.gp = gp;
        name = "Middle_Health";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/bars/bar_health_middle_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/bars/bar_health_middle_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/bars/bar_health_middle_low.png"));
            image4 = ImageIO.read(getClass().getResourceAsStream("/bars/bar_all_middle_empty.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
            image4 = uTool.scaleImage(image4, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
