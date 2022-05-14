package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;

//    public int hasKey = 0;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
        /*
            (- gp.tileSize) / 2 because coords are for top left.
            we must center the player
         */
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1     = setupPackageAgnosticEntity("/player/boy_up_1");
        up2     = setupPackageAgnosticEntity("/player/boy_up_2");
        down1   = setupPackageAgnosticEntity("/player/boy_down_1");
        down2   = setupPackageAgnosticEntity("/player/boy_down_2");
        right1  = setupPackageAgnosticEntity("/player/boy_right_1");
        right2  = setupPackageAgnosticEntity("/player/boy_right_2");
        left1   = setupPackageAgnosticEntity("/player/boy_left_1");
        left2   = setupPackageAgnosticEntity("/player/boy_left_2");
    }


    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else direction = "right";

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            handleNpcInteraction(npcIndex);
            //IF COLLISION = FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    private void handleNpcInteraction(int i) {
        if(i != 999){
            System.out.println("You hit an NPC");
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            System.out.println("Player.pickUpObject");
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image;

        image = getImageByDirection();
        //move the world around the player, so the player doesn't move
        g2.drawImage(image, screenX, screenY, null);
    }
}
