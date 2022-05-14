package main;

import java.awt.*;

public class UI {

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
        if(gp.gameState == GamePanel.pauseState){
            drawPauseScreen();
        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "Paused";
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text,x,y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}

