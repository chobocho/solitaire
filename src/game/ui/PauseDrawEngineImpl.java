package game.ui;

import com.chobocho.solitaire.Solitare;
import game.CardGameGui;
import game.WinLog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class PauseDrawEngineImpl implements DrawEngine {
    final static String TAG = "PauseDrawEngineImpl";
    int width = 100;
    int height = 150;
    int cardCap = 20;

    public PauseDrawEngineImpl() {
    }

    @Override
    public void onDraw(Graphics g, Solitare game, LinkedList<Integer> hideCard, BufferedImage[] cardImages, BufferedImage[] buttonImages) {
        for (int i = 6; i >= 0; --i) {
            int cap = 0;
            for (int j = i, k = 0; j >= 0; --j, k++) {
                g.drawImage(cardImages[0], 10 + width * i + 10 * i, 20 + height + cap, null);
                cap += cardCap;
            }
        }

        g.drawImage(buttonImages[CardGameGui.RESME_GAME_IMAGE], 300, 250, null);
        g.drawImage(buttonImages[CardGameGui.NEW_GAME_IMAGE], 300, 450, null);
    }
}
