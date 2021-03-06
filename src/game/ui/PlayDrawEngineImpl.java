package game.ui;

import com.chobocho.card.Card;
import com.chobocho.deck.Deck;
import com.chobocho.solitaire.Solitare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class PlayDrawEngineImpl implements DrawEngine {
    final static String TAG = "PlayDrawEngineImpl";
    final static int CARD_NONE_IMAGE = 53;
    int width = 100;
    int height = 150;
    int cardCap = 20;

    public PlayDrawEngineImpl() {

    }

    @Override
    public void onDraw(Graphics g, Solitare game, LinkedList<Integer> hideCard, BufferedImage[] cardImages, BufferedImage[] buttonImages) {
        onDrawBoardDeck(g, cardImages, game, hideCard);
        onDrawResultDeck(g, cardImages, game, hideCard);
        onDrawPlayDeck(g, cardImages, game, hideCard);
    }

    private void onDrawBoardDeck(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        Deck[] decks = new Deck[7];

        for (int i = 0; i < 7; i++) {
            decks[i] = game.getDeck(Solitare.BOARD_DECK_1+i);

            int cap = 0;
            for (int j = decks[i].size()-1, k = 0; j >= 0; --j, k++) {
                Card card = decks[i].get(j);
                if (card.isOpen()) {
                    int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();

                    if (!hideCard.contains(imgNumber)) {
                        g.drawImage(cardImages[imgNumber], 10 + width * i + 10 * i, 20 + height + cap, null);
                    }
                    cap += cardCap*2;
                } else {
                    g.drawImage(cardImages[0], 10 + width * i + 10 * i, 20 + height + cap, null);
                    cap += cardCap;
                }
            }
        }
    }

    private void onDrawResultDeck(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard) {
        //WinLog.i(TAG, "onDrawResultDeck");
        Deck[] decks = new Deck[4];

        for (int i = 0; i < 4; i++) {
            decks[i] = game.getDeck(Solitare.RESULT_DECK_1+i);

            if (!decks[i].isEmpty()) {
                Card card = decks[i].top();
                //WinLog.i(TAG, decks[i].toString());
                //WinLog.i(TAG, card.toString());
                int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
                if (!hideCard.contains(imgNumber)) {
                    g.drawImage(cardImages[imgNumber], 10 + width * i + 10 * i, 10, null);
                } else {
                    if (decks[i].size() > 1 ) {
                        Card preCard = decks[i].get(1);
                        int preImgNumber = (preCard.getFigure().getValue() - 1) * 13 + preCard.getNumber().getValue();
                        g.drawImage(cardImages[preImgNumber], 10 + width * i + 10 * i, 10, null);
                    }
                }
            }
        }
    }

    private void onDrawPlayDeck(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard) {

        // WinLog.i(TAG, game.getDeck(PlayState.PLAY_DECK).toString());
        //WinLog.i(TAG, game.getDeck(PlayState.OPENED_CARD_DECK).toString());

        //WinLog.i(TAG, "onDrawPlayDeck");
        Deck deck = game.getDeck(Solitare.PLAY_DECK);
        if (!deck.isEmpty() && deck.top().isOpen()) {
            Card card = deck.top();
            //WinLog.i(TAG, deck.toString());
            //WinLog.i(TAG, card.toString());
            int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
            g.drawImage(cardImages[imgNumber], 70+width*6, 10, null);
        } else if (deck.isEmpty()) {
            g.drawImage(cardImages[CARD_NONE_IMAGE], 70+width*6, 10, null);
        }

        Deck openedCardDeck = game.getDeck(Solitare.OPENED_CARD_DECK);

        if (!openedCardDeck.isEmpty() && openedCardDeck.top().isOpen()) {
            Card card = openedCardDeck.top();
            //WinLog.i(TAG, openedCardDeck.toString());
            //WinLog.i(TAG, card.toString());
            int imgNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
            if (!hideCard.contains(imgNumber)) {
                g.drawImage(cardImages[imgNumber], 60 + width * 5, 10, null);
            } else {
                if (openedCardDeck.size() > 1 ) {
                    Card preCard = openedCardDeck.get(1);
                    int preImgNumber = (preCard.getFigure().getValue() - 1) * 13 + preCard.getNumber().getValue();
                    g.drawImage(cardImages[preImgNumber], 60 + width * 5, 10, null);
                }
            }
        }
    }
}
