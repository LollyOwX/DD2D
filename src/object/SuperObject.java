package object;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import main.GamePanel;
import main.UtilityTool;
import java.awt.Rectangle;

public class SuperObject {
	public BufferedImage image, image2, image3, image4;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
		int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
}
