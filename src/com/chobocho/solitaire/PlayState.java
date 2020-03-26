package com.chobocho.solitaire;

import com.chobocho.card.Card;
import com.chobocho.deck.*;
import com.chobocho.util.CLog;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlayState extends GameState {
    final static String TAG = "PlayState";
    public static final int PLAY_DECK = 0;
    public static final int RESULT_DECK_1 = 1;
    public static final int RESULT_DECK_2 = 2;
    public static final int RESULT_DECK_3 = 3;
    public static final int RESULT_DECK_4 = 4;

    public static final int BOARD_DECK_1 = 5;
    public static final int BOARD_DECK_2 = 6;
    public static final int BOARD_DECK_3 = 7;
    public static final int BOARD_DECK_4 = 8;
    public static final int BOARD_DECK_5 = 9;
    public static final int BOARD_DECK_6 = 10;
    public static final int BOARD_DECK_7 = 11;

    public static final int OPENED_CARD_DECK = 12;
    public static final int INIT_DECK = 13;

    Deck[] resultDeck;
    Deck[] boardDeck;
    Deck playDeck;
    Deck opendCardDeck;
    Deck initDeck;
    ArrayList<Deck> deckList;

    public PlayState() {
        initVars();
        initDeckList();
        initGame();
    }

    private void initVars() {
        deckList = new ArrayList<Deck>();

        initDeck = new InitDeck();
        playDeck = new PlayDeck();
        opendCardDeck = new PlayDeck();

        ResultDeckCheckMethod resultDeckCheckMethod = new ResultDeckCheckMethod();

        resultDeck = new BoardDeck[4];
        for (int i = 0; i < resultDeck.length; i++) {
            resultDeck[i] = new BoardDeck(resultDeckCheckMethod);
        }

        boardDeck = new BoardDeck[7];
        BoardDeckCheckMethod boardDeckCheckMethod = new BoardDeckCheckMethod();
        for (int i = 0; i < boardDeck.length; i++) {
            boardDeck[i] = new BoardDeck(boardDeckCheckMethod);
        }
    }

    private void initDeckList() {
        // It should be sync with index from PLAYDECK
        deckList.add(playDeck);

        for (Deck deck: resultDeck) {
            deckList.add(deck);
        }

        for (Deck deck: boardDeck) {
            deckList.add(deck);
        }

        deckList.add(opendCardDeck);
        deckList.add(initDeck);
    }

    public void initGame() {
        initBoard();
    }

    private void initBoard() {
        initDeck.init();
        for (Deck deck : deckList) {
            deck.init();
        }

        runInitBoardCmd();
    }

    private void runInitBoardCmd() {
        moveCard(INIT_DECK, BOARD_DECK_1);
        moveCard(INIT_DECK, BOARD_DECK_2);
        moveCard(INIT_DECK, BOARD_DECK_3);
        moveCard(INIT_DECK, BOARD_DECK_4);
        moveCard(INIT_DECK, BOARD_DECK_5);
        moveCard(INIT_DECK, BOARD_DECK_6);
        moveCard(INIT_DECK, BOARD_DECK_7);

        moveCard(INIT_DECK, BOARD_DECK_2);
        moveCard(INIT_DECK, BOARD_DECK_3);
        moveCard(INIT_DECK, BOARD_DECK_4);
        moveCard(INIT_DECK, BOARD_DECK_5);
        moveCard(INIT_DECK, BOARD_DECK_6);
        moveCard(INIT_DECK, BOARD_DECK_7);

        moveCard(INIT_DECK, BOARD_DECK_3);
        moveCard(INIT_DECK, BOARD_DECK_4);
        moveCard(INIT_DECK, BOARD_DECK_5);
        moveCard(INIT_DECK, BOARD_DECK_6);
        moveCard(INIT_DECK, BOARD_DECK_7);

        moveCard(INIT_DECK, BOARD_DECK_4);
        moveCard(INIT_DECK, BOARD_DECK_5);
        moveCard(INIT_DECK, BOARD_DECK_6);
        moveCard(INIT_DECK, BOARD_DECK_7);

        moveCard(INIT_DECK, BOARD_DECK_5);
        moveCard(INIT_DECK, BOARD_DECK_6);
        moveCard(INIT_DECK, BOARD_DECK_7);

        moveCard(INIT_DECK, BOARD_DECK_6);
        moveCard(INIT_DECK, BOARD_DECK_7);

        moveCard(INIT_DECK, BOARD_DECK_7);

        while(!initDeck.isEmpty()) {
            moveCard(INIT_DECK, PLAY_DECK);
        }

        openCard(BOARD_DECK_1);
        openCard(BOARD_DECK_2);
        openCard(BOARD_DECK_3);
        openCard(BOARD_DECK_4);
        openCard(BOARD_DECK_5);
        openCard(BOARD_DECK_6);
        openCard(BOARD_DECK_7);
    }

    @Override
    public boolean moveCard(int from, int to) {
        //CLog.i(TAG,"Try to move card from " + from + " to " + to);
        if (deckList.get(from).isEmpty()) {
            CLog.e(TAG,"Deck " + from + " is empty!");
            return false;
        }
        Card card = deckList.get(from).top();
        boolean result = deckList.get(to).push(card);

        if (!result) {
            CLog.i(TAG,"Move card failed from " + from + " to " + to);
            return false;
        }

        deckList.get(from).pop();
        return true;
    }

    @Override
    public boolean moveCard(int from, int to, int count) {
        if (count == 1) {
            return moveCard(from, to);
        }

        Deck deck = new PlayDeck();

        for (int i = 0; i < count; i++) {
            deck.push(deckList.get(from).get(i));
        }

        Card card = deck.top();

        if (deckList.get(to).push(card)) {
            deck.pop();
            while(!deck.isEmpty()) {
                deckList.get(to).push(deck.pop());
            }
            for (int i = 0; i < count; i++) {
               deckList.get(from).pop();
            }
            return true;
        } else {
            CLog.i(TAG, deck.toString());
            CLog.i(TAG, deckList.get(from).toString());
        }
        return false;
    }

    @Override
    public boolean openCard(int deckNum) {
        CLog.i(TAG, "openCard " + deckNum);
        if (deckNum == PLAY_DECK) {
            return openPlayDecCard();
        }
        return deckList.get(deckNum).openTopCard();
    }

    private boolean openPlayDecCard() {
        if (playDeck.isEmpty()) {
            if (opendCardDeck.isEmpty()) {
                CLog.i(TAG, "There is no left card!");
                return false;
            }
            for (int i = opendCardDeck.size(); i > 0; i--) {
                Card card = opendCardDeck.pop();
                card.close();
                playDeck.push(card);
            }
            CLog.i(TAG, "Update card!");
            return true;
        }
        if (moveCard(PLAY_DECK, OPENED_CARD_DECK)) {
            return deckList.get(OPENED_CARD_DECK).openTopCard();
        }

        return false;
    }

    public Deck getDeck(int deck) {
        return deckList.get(deck);
    }

    public String toString() {
        StringBuffer result = new StringBuffer();

        for(int i = 0; i < deckList.size(); i++) {
            result.append(i + " size " + deckList.get(i).size() + ": " + deckList.get(i) + "\n");
        }
        return result.toString();
    }

    public int getState() { return PLAY_STATE; }

    @Override
    public boolean isPlayState() {
        return true;
    }

    @Override
    public boolean isFinishGame() {
        return (deckList.get(RESULT_DECK_1).size() == 13) &&
                (deckList.get(RESULT_DECK_2).size() == 13) &&
                (deckList.get(RESULT_DECK_3).size() == 13) &&
                (deckList.get(RESULT_DECK_4).size() == 13);
    }
}