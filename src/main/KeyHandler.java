package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public boolean upPressed, downPressed, rightPressed, leftPressed;

    //DEBUG
    public boolean checkDrawTime;

    @Override
    public void keyTyped(KeyEvent e) {
        //UNUSED
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_T -> checkDrawTime = !checkDrawTime;

            case KeyEvent.VK_P -> gp.gameState = switch (gp.gameState) {
                case GamePanel.playState -> GamePanel.pauseState;
                case GamePanel.pauseState -> GamePanel.playState;
                default -> throw new IllegalStateException("Unexpected value: " + gp.gameState);
            };
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }
    }
}
