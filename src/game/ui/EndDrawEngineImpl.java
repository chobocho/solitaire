package game.ui;

import com.chobocho.card.Card;
import com.chobocho.deck.Deck;
import com.chobocho.deck.PlayDeck;
import com.chobocho.solitaire.PlayState;
import com.chobocho.solitaire.Solitare;
import game.WinLog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class EndDrawEngineImpl implements DrawEngine {
    final static String TAG = "IdleDrawEngineImpl";
    final static int CARD_NONE_IMAGE = 53;
    int width = 100;
    int height = 150;
    int cardCap = 20;
    BufferedImage newGameImg;

    public EndDrawEngineImpl() {
        try {
            String imgName = "img\\newgame.png";
            newGameImg = ImageIO.read(new File(imgName));
            WinLog.i(TAG, "Load image Success! " + imgName);
        } catch (IOException e) {
            WinLog.e(TAG, "Load image Error!!\n" + e);
        }

    }

    @Override
    public void onDraw(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        onDrawResultDeck(g, cardImages, game);
        g.drawImage(newGameImg, 300, 300, null);
    }

    private void onDrawResultDeck(Graphics g, BufferedImage[] cardImages, Solitare game) {
        //WinLog.i(TAG, "onDrawResultDeck");
        Deck deck = new PlayDeck();

        for (int i = 0; i < 4; i++) {
            deck = game.getDeck(0);

            if (deck.size() > 0) {
                Card card = deck.get(i);
                if (card == null) {
                    continue;
                }
                int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
                g.drawImage(cardImages[imgNumber], 10 + width * i + 10 * i, 10, null);
            }
        }
    }
}
