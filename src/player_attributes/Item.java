package player_attributes;

import java.awt.image.BufferedImage;

public abstract class Item {
    BufferedImage texture;
    public String name;
    public int cost;
    abstract public void interact();
    abstract public void show();
}
