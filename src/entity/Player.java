package entity;

import main.GamePanel;
import main.GameState;
import main.KeyHandler;
import player_attributes.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends entity{
    GamePanel gp;
    KeyHandler keyH;
    char who;
    int inventoryNumber = 0;
    boolean isNPCCollision = false;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH, char who){
        this.gp = gp;
        this.keyH = keyH;
        this.who = who;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(8, 16, 32, 32);
        inventory = new Inventory(3);

        setDefaultValues();
        try {
            getPlayerImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues(){
        wodrldX = gp.tileSize*9;
        worldY = gp.tileSize*38;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() throws IOException {
        if (who == (char)0) {
            up0 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_up.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_up_leftLeg.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_up_rightLeg.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_down_leftLeg.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_down_rightLeg.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_right_go.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/textures/vika/vika_left_go.png"));
        } else if (who == (char)1){
            up0 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_up.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_up_leftLeg.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_up_rightLeg.png"));
            down0 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_down_leftLeg.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_down_rightLeg.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_right.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_right_go.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/textures/artem/artem_left_go.png"));
        }
    }

    public void update(){

        inventory.setPicked(inventoryNumber);

        if (keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.upPressed || gp.answerNum==-2) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            }

            collisionOn = false;
            int NPCCollisionNumber = gp.collisionM.checkOtherEntity(this);
            gp.collisionM.checkTile(this);

            isNPCCollision = NPCCollisionNumber != -1;

            if (NPCCollisionNumber!=-1 && gp.answerNum==-2){
                gp.gameState = GameState.dialogState;
                gp.NPCarr[NPCCollisionNumber].isDialog = true;
            }

            if (!collisionOn && gp.gameState!=GameState.dialogState){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        wodrldX += speed;
                        break;
                    case "left":
                        wodrldX -= speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else { spriteNum=0;}

    }
    public void draw(Graphics2D g2){
        /*g2.setColor(Color.white);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);*/
        BufferedImage img = null;
        switch (direction){
            case "up":
                if(spriteNum==1){
                    img = up1;
                }
                if (spriteNum == 2){
                    img = up2;
                }
                if (spriteNum==0) {
                    img = up0;
                }
                break;
            case "down":
                if(spriteNum==1){
                    img = down1;
                }
                if (spriteNum == 2){
                    img = down2;
                }
                if (spriteNum == 0) {
                    img = down0;
                }
                break;
            case "right":
                if(spriteNum==1 || spriteNum==0){
                    img = right1;
                }
                if (spriteNum == 2){
                    img = right2;
                }
                break;
            case "left":
                if(spriteNum==1 || spriteNum==0){
                    img = left1;
                }
                if (spriteNum == 2){
                    img = left2;
                }
                break;
        }
        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
        inventory.show(g2, gp);
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }
    public boolean getNpcCollision(){
        return isNPCCollision;
    }
}
