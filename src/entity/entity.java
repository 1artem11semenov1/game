package entity;

import player_attributes.Inventory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class entity {
    public int wodrldX, worldY;
    public int speed;
    public BufferedImage up0, up1, up2, down0, down1, down2, right1, right2, left1, left2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 10;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    Inventory inventory;
}
