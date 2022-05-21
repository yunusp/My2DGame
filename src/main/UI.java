package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    public String currentDialogue;
    Graphics2D g2;
    GamePanel gp;
    Font maruMonica, purisaB;
    public int commandNum = 0;


    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            assert is != null;
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException ffe) {
            throw new RuntimeException(ffe);
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        if (gp.gameState == GamePanel.titleState) {
            drawTitleScreen();
        }

        //PAUSED STATE
        if (gp.gameState == GamePanel.pauseState) {
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if (gp.gameState == GamePanel.dialogueState) {
            drawDialogueScreen();
        }
    }

    private void drawTitleScreen() {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        String text = "Game Big Game";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96));
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        //SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);

        //TITLE NAME
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        //PLAYER IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2; //center him
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));
        text = "New game";
        x = getXForCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text,x,y);
        if(commandNum == 0) g2.drawString(">", x - gp.tileSize, y);

        text = "Load game";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1) g2.drawString(">", x - gp.tileSize, y);

        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2) g2.drawString(">", x - gp.tileSize, y);


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
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
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
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}

