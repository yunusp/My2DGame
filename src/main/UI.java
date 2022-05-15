package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    public String currentDialogue;
    Graphics2D g2;
    GamePanel gp;
    Font arial_40, arial_80B;
//    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";

    public boolean gameFinished = false;


    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

//        OBJ_Key key = new OBJ_Key();
//        keyImage = key.image;
    }

    public void showMessage(String msg) {
        message = msg;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        //PAUSED STATE
        if (gp.gameState == GamePanel.pauseState) {
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if (gp.gameState == GamePanel.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawDialogueScreen() {
        //WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentDialogue, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);

        g2.fillRoundRect(x, y, width, height, 35, 35);

        //border
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 35, 35);

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "Paused";
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}

