package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Right_Health extends Entity {
    GamePanel gp;
    public OBJ_Right_Health(GamePanel gp) {
        super(gp);
        name = "Left_Health";
        image = setup("/bars/bar_health_right_full.png");
        image2 = setup("/bars/bar_health_right_half.png");
        image3 = setup("/bars/bar_health_right_low.png");
        image4 = setup("/bars/bar_all_right_empty.png");
    }
}
