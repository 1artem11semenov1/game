package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadMap();
        getTileImage();
    }

    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/floor.png"));
            tile[1] = new Tile();
            tile[1].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/wall.png"));
            tile[1].collision = true;
            tile[2] = new Tile();
            tile[2].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/floor_friendly.png"));
            tile[3] = new Tile();
            tile[3].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/floor_gradToFriendly.png"));
            tile[4] = new Tile();
            tile[4].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/floor_bossBlack.png"));
            tile[5] = new Tile();
            tile[5].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/floor_bossWhite.png"));
            tile[6] = new Tile();
            tile[6].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/door_left.png"));
            tile[7] = new Tile();
            tile[7].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/door_right.png"));
            tile[8] = new Tile();
            tile[8].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/console1.png"));
            tile[8].collision = true;
            tile[9] = new Tile();
            tile[9].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/woodPlanking.png"));
            tile[9].collision = true;
            tile[10] = new Tile();
            tile[10].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/woodFloor.png"));
            tile[11] = new Tile();
            tile[11].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/roofRed-bgFloor.png"));
            tile[11].collision = true;
            tile[12] = new Tile();
            tile[12].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/roofRed-bgPlanking.png"));
            tile[12].collision = true;
            tile[13] = new Tile();
            tile[13].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/titleWeapon-we.png"));
            tile[13].collision = true;
            tile[14] = new Tile();
            tile[14].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/titleWeapon-ap.png"));
            tile[14].collision = true;
            tile[15] = new Tile();
            tile[15].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/titleWeapon-on.png"));
            tile[15].collision = true;
            tile[16] = new Tile();
            tile[16].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/weaponTable1.png"));
            tile[16].collision = true;
            tile[17] = new Tile();
            tile[17].img = ImageIO.read(getClass().getResourceAsStream("/textures/tiles/weaponTable2.png"));
            tile[17].collision = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMap(){
        InputStream is = getClass().getResourceAsStream("/maps/map.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        for (int row = 0; row<gp.maxWorldRow; ++row){
            try {
                String[] line = br.readLine().split(" ");

                for (int col = 0; col< gp.maxWorldCol; ++col){
                    mapTileNum[col][row] = Integer.parseInt(line[col]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void draw (Graphics2D g2){
        int panelSizeX = gp.maxScreenCol*gp.scale*gp.originalTileSize;
        int panelSizeY = gp.maxScreenRow*gp.scale*gp.originalTileSize;
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol<gp.maxWorldCol && worldRow< gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.wodrldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.wodrldX - gp.player.screenX && worldX - gp.tileSize < gp.player.wodrldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            ++worldCol;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }

    }
}
