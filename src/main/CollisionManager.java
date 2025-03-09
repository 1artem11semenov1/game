package main;

import entity.entity;

public class CollisionManager {

    GamePanel gp;

    public CollisionManager(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(entity entity){
        int entityLeftWorldX = entity.wodrldX + entity.solidArea.x;
        int entityRightWorldX = entity.wodrldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY+entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
        }
    }

    public int checkOtherEntity(entity entity){
        int entityLeftWorldX = entity.wodrldX + entity.solidArea.x;
        int entityRightWorldX = entity.wodrldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        for (int i = 0; i<gp.NPCarr.length; ++i) {
            int NPCLeftWorldX = gp.NPCarr[i].wodrldX + gp.NPCarr[i].solidArea.x;
            int NPCRightWorldX = gp.NPCarr[i].wodrldX + gp.NPCarr[i].solidArea.x + gp.NPCarr[i].solidArea.width;
            int NPCTopWorldY = gp.NPCarr[i].worldY + gp.NPCarr[i].solidArea.y;
            int NPCBottomWorldY = gp.NPCarr[i].worldY + gp.NPCarr[i].solidArea.y + gp.NPCarr[i].solidArea.height;

            switch (entity.direction) {
                case "up":
                    if (entityTopWorldY<NPCBottomWorldY+entity.solidArea.x && entityBottomWorldY>NPCTopWorldY && entityLeftWorldX<NPCRightWorldX-entity.speed && entityRightWorldX>NPCLeftWorldX+entity.speed){
                        entity.collisionOn = true;
                        return i;
                    }
                    break;
                case "down":
                    if (entityTopWorldY<NPCBottomWorldY && entityBottomWorldY>=NPCTopWorldY && entityLeftWorldX<NPCRightWorldX-entity.speed && entityRightWorldX>NPCLeftWorldX+entity.speed){
                        entity.collisionOn = true;
                        return i;
                    }
                    break;
                case "right":
                    if (entityTopWorldY<NPCBottomWorldY && entityBottomWorldY>NPCTopWorldY && entityLeftWorldX<NPCRightWorldX-entity.speed && entityRightWorldX>NPCLeftWorldX){
                        entity.collisionOn = true;
                        return i;
                    }
                    break;
                case "left":
                    if (entityTopWorldY<NPCBottomWorldY && entityBottomWorldY>NPCTopWorldY && entityLeftWorldX<NPCRightWorldX && entityRightWorldX>NPCLeftWorldX+entity.speed){
                        entity.collisionOn = true;
                        return i;
                    }
                    break;
            }
        }
        return -1;
    }
}
