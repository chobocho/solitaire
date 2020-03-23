package game.ui;

import com.chobocho.solitaire.Solitare;
import game.WinLog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class IdleDrawEngineImpl implements DrawEngine {
    final static String TAG = "IdleDrawEngineImpl";
    final static int CARD_NONE_IMAGE = 53;
    int width = 100;
    int height = 150;
    int cardCap = 20;
    BufferedImage startImg;

    public IdleDrawEngineImpl() {
        try {
            String imgName = "img\\start.png";
            startImg = ImageIO.read(new File(imgName));
            WinLog.i(TAG, "Load image Success! " +imgName);
        } catch (IOException e) {
            WinLog.e(TAG, "Load image Error!!\n"+e);
        }

    }

    @Override
    public void onDraw(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        onDrawBoardDeck(g, cardImages, game);
        g.drawImage(startImg, 300, 300, null);
    }

    private void onDrawBoardDeck(Graphics g, BufferedImage[] cardImages, Solitare game) {
        for (int i = 6; i >= 0; --i) {
            int cap = 0;
            for (int j = i, k = 0; j >= 0; --j, k++) {
                g.drawImage(cardImages[0], 10 + width * i + 10 * i, 20 + height + cap, null);
                cap += cardCap;
            }
        }
    }

}
