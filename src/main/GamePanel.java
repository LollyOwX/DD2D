package main;	

import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import java.awt.*;
import entity.Entity;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int OriginalTileSize = 16; //16x16 tile
    final int scale = 3;
    public int tileSize = OriginalTileSize * scale;
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; //768
    public int screenHeight = tileSize * maxScreenRow; //576

    //World
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler KeyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public Player player = new Player(this, KeyH);

	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread;

	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int cinematicState = 4;
	public final int titleState = 5;

	
	public GamePanel() {	
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		aSetter.setObject();
		aSetter.setNpc();
		playMusic(0);
		gameState = titleState;
	}
	public void zoomInOut(int i) {
		int oldWorldWidth = tileSize * maxWorldCol; //2400 1200 = 0.5
		tileSize += i;
		double newWorldWidth = tileSize * maxWorldCol; //2350
		player.speed = (int) (newWorldWidth/600);
		double multiplier = (double) newWorldWidth/oldWorldWidth;
		System.out.println("tileSize:"+tileSize);
		System.out.println("player World X:"+player.worldX);
		double newPlayerWorldX = player.worldX * multiplier;
		double newPlayerWorldY = player.worldY * multiplier;
		player.worldX = newPlayerWorldX;
		player.worldY = newPlayerWorldY;
	}
	public void startGameThread() {		
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void run() {		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		
		while(gameThread != null) {	
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
			if(timer >= 1000000000) {
				timer = 0;
			}
		}
	}	
	public void update() {
        if(gameState == playState) {
			player.update();
            for(int i = 0; i < obj.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
		}
		if(gameState == pauseState) {
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
        if(gameState == titleState) {
            ui.draw(g2);
        }
        else {
            tileM.draw(g2);
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }
            player.draw(g2);
            ui.draw(g2);
        }
		g2.dispose();
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}