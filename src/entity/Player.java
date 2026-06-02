    package entity;

    import java.awt.Graphics2D;
    import java.awt.Rectangle;
    import main.GamePanel;
    import main.KeyHandler;
    import java.awt.image.BufferedImage;
    import java.awt.*;


    public class Player extends Entity{
        KeyHandler KeyH;
        public final int screenX;
        public final int screenY;
        //Player Stats
        public String playerClass = "Warrior";

        public Player(GamePanel gp, KeyHandler KeyH) {
            super(gp);
            this.gp = gp;
            this.KeyH = KeyH;
            screenX = gp.screenWidth/2 - (gp.tileSize/2);
            screenY = gp.screenHeight/2 - (gp.tileSize/2);
            solidArea = new Rectangle();
            solidArea.x = 8;
            solidArea.y = 16;
            solidAreaDefaultX = solidArea.x;
            solidAreaDefaultY = solidArea.y;
            solidArea.width = 30;
            solidArea.height = 24;
            setDefaultValues();
            getPlayerImage();
        }

        public void setDefaultValues() {
            worldX = gp.tileSize * 23;
            worldY = gp.tileSize * 21;
            speed = gp.worldWidth/600; //4
            idleDirection = "down";
            //Player Status
            maxLife = 10;
            life = maxLife;
        }
        public void getPlayerImage () {
            //Base
            up1 = setup("/player/" + playerClass + "_up_1");
            up2 = setup("/player/" + playerClass + "_up_2");
            down1 = setup("/player/" + playerClass + "_down_1");
            down2 = setup("/player/" + playerClass + "_down_2");
            left1 = setup("/player/" + playerClass + "_left_1");
            left2 = setup("/player/" + playerClass + "_left_2");
            right1 = setup("/player/" + playerClass + "_right_1");
            right2 = setup("/player/" + playerClass + "_right_2");
            //Idle
            downIdle1 = setup("/player/" + playerClass + "_downidle_1");
            downIdle2 = setup("/player/" + playerClass + "_downidle_2");
            upIdle1 = setup("/player/" + playerClass + "_upidle_1");
            upIdle2 = setup("/player/" + playerClass + "_upidle_2");
            leftIdle1 = setup("/player/" + playerClass + "_leftidle_1");
            leftIdle2 = setup("/player/" + playerClass + "_leftidle_2");
            rightIdle1 = setup("/player/" + playerClass + "_rightidle_1");
            rightIdle2 = setup("/player/" + playerClass + "_rightidle_2");
            //diagonal
            rightUp1 = setup("/player/" + playerClass + "_rightup_1");
            rightUp2 = setup("/player/" + playerClass + "_rightup_2");
            rightDown1 = setup("/player/" + playerClass + "_rightdown_1");
            rightDown2 = setup("/player/" + playerClass + "_rightdown_2");
            leftUp1 = setup("/player/" + playerClass + "_leftup_1");
            leftUp2 = setup("/player/" + playerClass + "_leftup_2");
            leftDown1 = setup("/player/" + playerClass + "_leftdown_1");
            leftDown2 = setup("/player/" + playerClass + "_leftdown_2");
        }
        public void update() {
            if (KeyH.upPressed || KeyH.downPressed || KeyH.leftPressed || KeyH.rightPressed) {

                double dx = 0;
                double dy = 0;

                if (KeyH.upPressed) {
                    dy -= 1;
                    direction = "up";
                    idleDirection = "idle_up";
                }
                if (KeyH.downPressed) {
                    dy += 1;
                    direction = "down";
                    idleDirection = "idle_down";
                }
                if (KeyH.leftPressed) {
                    dx -= 1;
                    direction = "left";
                    idleDirection = "idle_left";
                }
                if (KeyH.rightPressed) {
                    dx += 1;
                    direction = "right";
                    idleDirection = "idle_right";
                }

                if (dx != 0 || dy != 0) {
                    double len = Math.sqrt(dx * dx + dy * dy);
                    dx /= len;
                    dy /= len;

                    int moveX = (int)Math.round(dx * speed);
                    int moveY = (int)Math.round(dy * speed);

                    collisionOn = false;
                    worldX += moveX;
                    gp.cChecker.checkTile(this);
                    gp.cChecker.checkObject(this, true);
                    gp.cChecker.checkEntity(this, gp.npc);
                    if (collisionOn) worldX -= moveX;

                    collisionOn = false;
                    worldY += moveY;
                    gp.cChecker.checkTile(this);
                    gp.cChecker.checkObject(this, true);
                    gp.cChecker.checkEntity(this, gp.npc);
                    if (collisionOn) worldY -= moveY;
                }

                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
                gp.eHandler.checkEvent();
                gp.KeyH.enterPressed = false;
                spriteCounter++;
                if (spriteCounter > 13) {
                    spriteNum = spriteNum == 1 ? 2 : 1;
                    spriteCounter = 0;
                }

            } else {
                direction = idleDirection;
                idleSpriteCounter++;
                if (idleSpriteCounter > 32) {
                    spriteNum = spriteNum == 1 ? 2 : 1;
                    idleSpriteCounter = 0;
                }
            }
        }

        public void interactNPC(int i) {
            if(i != 999) {
                if(gp.KeyH.enterPressed == true) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
                }
            }
        }

        public void draw(Graphics2D g2) {
            BufferedImage image = null;
            boolean up = KeyH.upPressed;
            boolean down = KeyH.downPressed;
            boolean left = KeyH.leftPressed;
            boolean right = KeyH.rightPressed;

            if (up && right) {
                if (spriteNum == 1) image = rightUp1;
                else if (spriteNum == 2) image = rightUp2;

            } else if (up && left) {
                if (spriteNum == 1) image = leftUp1;
                else if (spriteNum == 2) image = leftUp2;

            } else if (down && right) {
                if (spriteNum == 1) image = rightDown1;
                else if (spriteNum == 2) image = rightDown2;

            } else if (down && left) {
                if (spriteNum == 1) image = leftDown1;
                else if (spriteNum == 2) image = leftDown2;

            } else {

                String dir = (direction != null) ? direction : (idleDirection != null ? idleDirection : "down");

                switch(dir) {
                    // MOVIMENTO
                    case "up":
                        if (spriteNum == 1) image = up1;
                        else image = up2;
                        break;
                    case "down":
                        if (spriteNum == 1) image = down1;
                        else image = down2;
                        break;
                    case "left":
                        if (spriteNum == 1) image = left1;
                        else image = left2;
                        break;
                    case "right":
                        if (spriteNum == 1) image = right1;
                        else image = right2;
                        break;
                    // IDLE DIREZIONALE
                    case "idle_up":
                        if (spriteNum == 1) image = upIdle1;
                        else image = upIdle2;
                        break;
                    case "idle_down":
                        if (spriteNum == 1) image = downIdle1;
                        else image = downIdle2;
                        break;
                    case "idle_left":
                        if (spriteNum == 1) image = leftIdle1;
                        else image = leftIdle2;
                        break;
                    case "idle_right":
                        if (spriteNum == 1) image = rightIdle1;
                        else image = rightIdle2;
                        break;
                    // FALLBACK
                    default:
                        if (spriteNum == 1) image = downIdle1;
                        else image = downIdle2;
                        break;
                }
            }
            g2.drawImage(image, screenX, screenY, null);
        }
        public void pickUpObject(int i) {
            if(i != 999) {
            }
        }
    }