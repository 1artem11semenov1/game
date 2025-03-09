package main;

import entity.Npc;
import entity.Player;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol;
    public final int screenHeight = tileSize*maxScreenRow;

    // world settings
    public final int maxWorldCol = 32;
    public final int maxWorldRow = 46;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH,(char)0);
    public CollisionManager collisionM = new CollisionManager(this);

    // NPCs
    public Npc testNPC = new Npc(this, keyH,14, 33, (char)1);
    public Npc testNPC1 = new Npc(this, keyH,20, 34, (char)0);
    public Npc testNPC2 = new Npc(this, keyH,20, 25, (char)0);
    public Npc[] NPCarr = new Npc[3];
    public short answerNum = -1;

    //game state
    public GameState gameState;
    /*public final int titleState = 0;
    public final int playState = 1;*/

    //title screen command
    public char titleScreenCommand;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        gameState = GameState.titleState;
        titleScreenCommand = 0;
        NPCarr[0] = testNPC;
        NPCarr[1] = testNPC1;
        NPCarr[2] = testNPC2;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000/FPS;
        double nextDrawTime = System.currentTimeMillis() + drawInterval;
        //double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread!=null){
            //long currentTime = System.currentTimeMillis();

            update();
            repaint();

           try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();
                //double remainingTime = nextDrawTime - System.nanoTime();
                //remainingTime = remainingTime/1000000;

                if (remainingTime>0) {
                    Thread.sleep((long) remainingTime);
                    //remainingTime=0;
                }
                //Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        for (int i=0; i< NPCarr.length; ++i) {
            if (NPCarr[i].isDialog) {
                NPCarr[i].update();
            }
        }
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (gameState == GameState.titleState){
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,35F));
            g2.setColor(Color.white);
            g2.drawString("Выберите персонажа", tileSize*4, tileSize*3);

            try {
                g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika.png")), tileSize*3, tileSize*5, tileSize*4, tileSize*4, Color.gray, null);
                g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem.png")), tileSize*9, tileSize*5, tileSize*4, tileSize*4, Color.gray, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
            g2.drawString("Нажмите 1", tileSize*4, tileSize*10);
            g2.drawString("Нажмите 2", tileSize*10, tileSize*10);

            if (titleScreenCommand == (char)1){
                player = new Player(this, keyH,(char)0);
                gameState = GameState.playState;
            } else if (titleScreenCommand == (char)2){
                player = new Player(this, keyH,(char)1);
                gameState = GameState.playState;
            }
        } else if (gameState == GameState.playState || gameState == GameState.dialogState){
            tileM.draw(g2);
            for (Npc npc : NPCarr) {
                npc.draw(g2);
            }
            player.draw(g2);
            g2.dispose();
        } else if (gameState == GameState.tradeState){
            tileM.draw(g2);
            player.draw(g2);
            for (int i=0; i< NPCarr.length; ++i) {
                NPCarr[i].draw(g2);
            }
            g2.dispose();
        }
    }
}
