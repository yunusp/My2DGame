package entity;

import main.GamePanel;
import main.UtilityTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Super Class of all Entities: players, npcs, etc.
 */
public abstract class Entity {
    protected final GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //default solid area
    public Rectangle solidArea = new Rectangle(0, 0, 48, 40);
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Returns the Scaled to {@code gp.tileSize} image after loading it from {@code imagePath}
     * <p> {@code imagePath} must have the full name of the package
     * <br>e.g. {@code "/player/boy_up_1"}</p>
     *
     * @param imagePath name of the image
     * @return scaledImage: A scaled version of {@code imagePath}
     */
    BufferedImage setupPackageAgnosticEntity(String imagePath) {
        UtilityTools uTool = new UtilityTools();
        BufferedImage scaledImage;
        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(imagePath + ".png")));
            scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }

    public void setAction() {}
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
//        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkPlayer(this);
        //entity may move if not colliding
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

    public void draw(Graphics2D g2) {
        BufferedImage image;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            image = getImageByDirection();
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage getImageByDirection() {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
            }
            case "down" -> {
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
            }
            case "right" -> {
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
            }
            case "left" -> {
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
            }
        }
        return image;
    }
}
