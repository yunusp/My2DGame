package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

//Our main game screen
public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16;    //16x16 default tile size
    final int scale = 3;                //we must scale, 16x16 is tiny on 1080p

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;    //768px
    public final int screenHeight = tileSize * maxScreenRow;   //576px

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    public final int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;


    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    /**
     * 10 Objects at the same time on the screen
     */
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    //GAME STATE
    public int gameState;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int dialogueState = 3;
    public static final int titleState = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);   //better performance

        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNpc();
//        playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == playState)
            player.update();
        for (Entity e : npc) {
            if (e != null) {
                e.update();
            }
        }
        if (gameState == pauseState) {
            System.out.println("In Pause");
            // TODO: 14-05-2022 add handler
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //DEBUG
        long drawStart = 0L;
        if (keyH.checkDrawTime)
            drawStart = System.nanoTime();
        //DEBUG

        //TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);

            //OBJECT
            for (SuperObject superObject : obj) {
                if (superObject != null)
                    superObject.draw(g2, this);
            }

            //NPCS
            for (Entity e : npc) {
                if (e != null) {
                    e.draw(g2);
                }
            }

            //PLAYER
            player.draw(g2);

            //UI
            ui.draw(g2);
        }

        //TILE


        //DEBUG
        if (keyH.checkDrawTime) {
            long drawEnd;
            drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passedTime, 10, 400);
            System.out.println("Draw Time: " + passedTime);
        }
        //DEBUG END

        g2.dispose();
    }

    public void playMusic(int i) {
//        music.setFile(i);
//        music.play();
//        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
