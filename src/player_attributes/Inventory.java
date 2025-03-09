package player_attributes;

import main.GamePanel;

import java.awt.*;

public class Inventory {
    Item[] inventory;
    public int picked = 0;
    public Inventory(int length){
        inventory = new Item[length];
        for (int i = 0; i<inventory.length; ++i){
            inventory[i] = new DefaultItem();
        }
    }
    public void show (Graphics2D g2, GamePanel gp){
        for (int i = inventory.length-1; i>=0; --i){
            if (inventory[i].name.equals("DA")){
                g2.setColor(new Color(0,0,0, 150));
                g2.fillRect(gp.tileSize*(gp.maxScreenCol - (inventory.length-i)), 0 , gp.tileSize, gp.tileSize);
            }

            if (i != picked) {
                g2.setColor(Color.darkGray);
                g2.drawRect(gp.tileSize*(gp.maxScreenCol -  (inventory.length-i)), 0 , gp.tileSize, gp.tileSize);
            }
        }

        g2.setColor(Color.white);
        g2.drawRect(gp.tileSize*(gp.maxScreenCol - (inventory.length-picked)), 0 , gp.tileSize, gp.tileSize);

    }

    public void setPicked(int picked) {
        if (picked<inventory.length) {
            this.picked = picked;
        }
    }
}
