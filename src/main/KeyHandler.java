package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {this.gp = gp;}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == GameState.titleState){
            if (code == KeyEvent.VK_1) {
                gp.titleScreenCommand = (char)1;
            }
            if (code == KeyEvent.VK_2) {
                gp.titleScreenCommand = (char)2;
            }
        }
        else if (gp.gameState == GameState.playState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER && gp.player.getNpcCollision()){
                gp.answerNum=-2;
                upPressed=false;
                downPressed = false;
                rightPressed = false;
                leftPressed = false;
            }
            switch (code) {
                case (KeyEvent.VK_1):
                    gp.player.setInventoryNumber(0);
                    break;
                case (KeyEvent.VK_2):
                    gp.player.setInventoryNumber(1);
                    break;
                case (KeyEvent.VK_3):
                    gp.player.setInventoryNumber(2);
                    break;
                case (KeyEvent.VK_4):
                    gp.player.setInventoryNumber(3);
                    break;
                case (KeyEvent.VK_5):
                    gp.player.setInventoryNumber(4);
                    break;
                case (KeyEvent.VK_6):
                    gp.player.setInventoryNumber(5);
                    break;
                case (KeyEvent.VK_7):
                    gp.player.setInventoryNumber(6);
                    break;
                case (KeyEvent.VK_8):
                    gp.player.setInventoryNumber(7);
                    break;
                case (KeyEvent.VK_9):
                    gp.player.setInventoryNumber(8);
                    break;
            }
        }
        else if (gp.gameState == GameState.dialogState){
            switch (code) {
                case (KeyEvent.VK_1):
                    gp.answerNum = 1;
                    break;
                case (KeyEvent.VK_2):
                    gp.answerNum = 2;
                    break;
                case (KeyEvent.VK_3):
                    gp.answerNum = 3;
                    break;
                case (KeyEvent.VK_4):
                    gp.answerNum = 4;
                    break;
                case (KeyEvent.VK_5):
                    gp.answerNum = 5;
                    break;
                case (KeyEvent.VK_6):
                    gp.answerNum = 6;
                    break;
                case (KeyEvent.VK_7):
                    gp.answerNum = 7;
                    break;
                case (KeyEvent.VK_8):
                    gp.answerNum = 8;
                    break;
                case (KeyEvent.VK_9):
                    gp.answerNum = 9;
                    break;
                case (KeyEvent.VK_ENTER):
                    gp.answerNum = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == GameState.playState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
        } /*else if (gp.gameState == GameState.dialogState){
            gp.answerNum = -1;
        }*/
    }
}
