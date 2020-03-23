package game.ui;

import com.chobocho.solitaire.Solitare;
import game.WinLog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class PauseDrawEngineImpl implements DrawEngine {
    final static String TAG = "PauseDrawEngineImpl";
    final static int CARD_NONE_IMAGE = 53;
    int width = 100;
    int height = 150;
    int cardCap = 20;
    BufferedImage resumeImg;
    BufferedImage newGamgImg;

    public PauseDrawEngineImpl() {
        try {
            String imgName = "img\\resume.png";
            resumeImg = ImageIO.read(new File(imgName));
            WinLog.i(TAG, "Load image Success! " + imgName);

            String newGameImgName = "img\\newgame.png";
            newGamgImg = ImageIO.read(new File(newGameImgName));
            WinLog.i(TAG, "Load image Success! " + newGameImgName);
        } catch (IOException e) {
            WinLog.e(TAG, "Load image Error!!\n"+e);
        }

    }

    @Override
    public void onDraw(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        for (int i = 6; i >= 0; --i) {
            int cap = 0;
            for (int j = i, k = 0; j >= 0; --j, k++) {
                g.drawImage(cardImages[0], 10 + width * i + 10 * i, 20 + height + cap, null);
                cap += cardCap;
            }
        }

        g.drawImage(resumeImg, 300, 250, null);
        g.drawImage(newGamgImg, 300, 450, null);
    }
}
