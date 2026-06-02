package main;
import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }
    public void checkEvent() {
        if (hit(23, 10, "up") == true) {damagePit(gp.dialogueState);}
        if (hit(19, 14, "up") == true) {healingPool(gp.dialogueState);};
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = (int) gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = (int) gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;
        return hit;
    }
    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You falled into a Pit!";
        gp.player.life -= 1;
    }
    public void healingPool(int gameState) {
        if (gp.KeyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "Drinking from the Lake,\nyour vital energy got restored";
            gp.player.life = gp.player.maxLife;
        }
    }
}
