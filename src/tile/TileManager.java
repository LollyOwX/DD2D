package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
    public int[][] mapTileNum;

	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[100];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        if (tile == null) throw new IllegalStateException("tile array is null");
        if (index < 0 || index >= tile.length) throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        String fullPath = "/tiles/" + imagePath + ".png";
        String altPath = ("tiles/" + imagePath + ".png");
        try (InputStream is = getClass().getResourceAsStream(fullPath) != null ? getClass().getResourceAsStream(fullPath) : Thread.currentThread().getContextClassLoader().getResourceAsStream(altPath)) {
            if (is == null) throw new IllegalArgumentException("Resource not found on classpath: " + fullPath);
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(is);
            if (tile[index].image == null) throw new IOException("ImageIO returned null for: " + fullPath);
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tile image: " + fullPath, e);
        }
    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
        setup(6, "water_rightUp", true);
        setup(7, "water_rightDown", true);
        setup(8, "water_leftUp", true);
        setup(9, "water_leftDown", true);
        setup(10, "water_centerRight", true);
        setup(11, "water_centerLeft", true);
        setup(12, "water_centerUp", true);
        setup(13, "water_centerDown", true);
        setup(14, "water_island_rightUp", true);
        setup(15, "water_island_rightDown", true);
        setup(16, "water_island_leftUp", true);
        setup(17, "water_island_leftDown", true);
        setup(18, "sand_rightUp", false);
        setup(19, "sand_rightDown", false);
        setup(20, "sand_leftUp", false);
        setup(21, "sand_leftDown", false);
        setup(22, "sand_centerRight", false);
        setup(23, "sand_centerLeft", false);
        setup(24, "sand_centerUp", false);
        setup(25, "sand_centerDown", false);
    }

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e) {
		}
	}
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			double screenX = worldX - gp.player.worldX + gp.player.screenX;
			double screenY = worldY - gp.player.worldY + gp.player.screenY;
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, null);
			}
			worldCol++;
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
				
			}
		}
	}
}















