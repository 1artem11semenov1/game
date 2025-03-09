package entity;

import main.GamePanel;
import main.GameState;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class Npc extends entity{
    GamePanel gp;
    char who;
    public boolean isDialog = false;
    boolean isTrade = false;
    KeyHandler keyH;
    ArrayList<String[]> dialog;
    int lastPhraseNum = 0;
    int choosedAnswer = 0;
    boolean dialogEndFlag = false;

    public final int screenX;
    public final int screenY;

    Hashtable<Integer, Integer> dialogTable;

    public Npc(GamePanel gp, KeyHandler keyH, int wX, int wY, char who){
        this.gp = gp;
        this.who = who;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(0, 0, 48, 48);

        setDefaultValues(wX,wY);
        try {
            getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues(int wX, int wY){
        wodrldX = gp.tileSize*wX;
        worldY = gp.tileSize*wY;
    }

    public void getImage() throws IOException {
        if (who == (char)0 || who == (char)1) {
            down0 = ImageIO.read(getClass().getResourceAsStream("/textures/npc/testTexture.png"));
        }
    }

    String[] parseDialogString(String input){
        String[] res = new String[3];
        String tagIn = "";
        String tagOut = "";
        String dialogStr = "";
        int i = 0;
        while (input.charAt(i)!=')'){
            tagIn = tagIn.concat(Character.toString(input.charAt(i)));
            ++i;
        }
        tagIn = tagIn.concat(Character.toString(input.charAt(i)));
        ++i;
        while (input.charAt(i)!='('){
            dialogStr = dialogStr.concat(Character.toString(input.charAt(i)));
            ++i;
        }
        while (input.charAt(i)!=')'){
            tagOut = tagOut.concat(Character.toString(input.charAt(i)));
            ++i;
        }
        tagOut = tagOut.concat(Character.toString(input.charAt(i)));

        res[0] = tagIn;
        res[1] = dialogStr;
        res[2] = tagOut;
        return res;
    }
    ArrayList<String[]> parseDialogFile(InputStream dialogFile){
        ArrayList<String[]> res = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new InputStreamReader(dialogFile));
        String inputDialogString = "";
        while (!inputDialogString.equals("(end)")){
            try {
                inputDialogString = bf.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!inputDialogString.equals("(end)")) {
                String[] parsedString = parseDialogString(inputDialogString);
                res.add(parsedString);
            }
        }
        return res;
    }

    void showDialog(Graphics2D g2, int phrasePosition){

        g2.drawString(dialog.get(phrasePosition)[1], gp.tileSize*4 + gp.tileSize, gp.tileSize*2);

        int i = phrasePosition+1;
        while (i<dialog.size() && !dialog.get(i)[0].equals(dialog.get(phrasePosition)[2])){
            ++i;
            if (i>=dialog.size()){
                break;
            }
        }
        ArrayList<String> answers = new ArrayList<>();
        dialogTable = new Hashtable<>();
        int k = 1;
        while(i<dialog.size() && dialog.get(i)[0].equals(dialog.get(phrasePosition)[2])){
            answers.add(dialog.get(i)[1]);
            dialogTable.put(k, i);
            ++k;
            ++i;

            if (i>=dialog.size()){
                break;
            }
        }
        if (!answers.isEmpty()) {
            int step = (int) (gp.tileSize * 2.5) / answers.size();
            for (int j = 0; j < answers.size(); ++j) {
                g2.drawString((j + 1) + ") " + answers.get(j), gp.tileSize * 4 + gp.tileSize, gp.tileSize * 2 + step * (j + 1));
            }
        } else {
            dialogEndFlag = true;
        }
    }

    public void update(){
        if (gp.answerNum>0 && isDialog && !dialogEndFlag) {
            choosedAnswer = gp.answerNum;
            String nextNPCPhraseTag = dialog.get(dialogTable.get(choosedAnswer))[2];
            if (!nextNPCPhraseTag.equals("(trade)")) {
                int i = lastPhraseNum;
                while (!dialog.get(i)[0].equals(nextNPCPhraseTag)) {
                    ++i;
                }
                lastPhraseNum = i;
            } else {
                lastPhraseNum = 0;
                gp.answerNum = -1;
                isDialog = false;
                isTrade = true;
                gp.gameState = GameState.tradeState;
            }
        }
        if (gp.answerNum == 0){
            isDialog = false;
            dialogEndFlag = false;
            gp.gameState = GameState.playState;
            lastPhraseNum = 0;
        }
        gp.answerNum = -1;
    }

    public void draw(Graphics2D g2){
        BufferedImage img = down0;

        int screenX = wodrldX - gp.player.wodrldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if (isDialog){
            g2.setColor(new Color(0,0,0, 150));
            g2.fillRect(gp.tileSize*4, gp.tileSize, gp.tileSize*8, gp.tileSize*4);
            g2.setColor(new Color(255,255,255, 255));
            g2.drawRect(gp.tileSize*4, gp.tileSize, gp.tileSize*8, gp.tileSize*4);
            switch (who) {
                case ((char)0):
                    dialog = parseDialogFile(getClass().getResourceAsStream("/dialogs/testNPCDialog.txt"));
                    break;
                case((char)1):
                    dialog = parseDialogFile(getClass().getResourceAsStream("/dialogs/weaponMasterDialog.txt"));
                    break;
            }
            showDialog(g2, lastPhraseNum);
        }
        else if (isTrade){
            g2.setColor(new Color(0,0,0, 150));
            g2.fillRect(gp.tileSize*2, gp.tileSize, gp.tileSize*10, gp.tileSize*6);
            g2.setColor(new Color(255,255,255, 255));
            g2.drawRect(gp.tileSize*2, gp.tileSize, gp.tileSize*10, gp.tileSize*6);
        }
    }
}
