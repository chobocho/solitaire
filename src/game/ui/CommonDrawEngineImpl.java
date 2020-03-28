package game.ui;

import com.chobocho.solitaire.Solitare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class CommonDrawEngineImpl implements DrawEngine {
    @Override
    public void onDraw(Graphics g, Solitare game, LinkedList<Integer> hideCard, BufferedImage[] cardImages, BufferedImage[] buttonImages) {
        onDrawCommon(g, cardImages, buttonImages);
    }

    private void onDrawCommon(Graphics g, BufferedImage[] cardImages, BufferedImage[] buttonImages) {
        int screenW = 800;
        int screenH = 800;

        int CARD_NONE_IMAGE = 53;

        int width = 100;
        int height = 150;


        g.setColor(new Color(88, 214, 141));
        g.fillRect(0, 0, screenW, screenH);

        // Result deck
        g.drawImage(cardImages[CARD_NONE_IMAGE], 10, 10, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 20 + width, 10, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 30 + width * 2, 10, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 40 + width * 3, 10, null);

        g.drawImage(cardImages[0], 70 + width * 6, 10, null);

        g.drawImage(cardImages[CARD_NONE_IMAGE], 10, 20 + height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 20 + width, 20 + height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 30 + width * 2, 20 + height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 40 + width * 3, 20 + height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 50 + width * 4, 20 + height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 60 + width * 5, 20 + height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 70 + width * 6, 20 + height, null);
    }
}
