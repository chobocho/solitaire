package com.chobocho.solitaire;

import com.chobocho.card.Card;
import com.chobocho.deck.*;
import com.chobocho.util.CLog;

import java.util.ArrayList;

public class PlayState extends GameState {
    final static String TAG = "PlayState";

    Deck[] resultDeck;
    Deck[] boardDeck;
    Deck playDeck;
    Deck opendCardDeck;
    Deck initDeck;
    ArrayList<Deck> deckList;
    private int moveCount;

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
        moveCount = 0;
        initDeck.init();
        for (Deck deck : deckList) {
            deck.init();
        }

        runInitBoardCmd();
    }

    private void runInitBoardCmd() {
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_1);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_2);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_3);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_4);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_5);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_6);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_2);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_3);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_4);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_5);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_6);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_3);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_4);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_5);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_6);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_4);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_5);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_6);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_5);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_6);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_6);
        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        moveCard(Solitare.INIT_DECK, Solitare.BOARD_DECK_7);

        while(!initDeck.isEmpty()) {
            moveCard(Solitare.INIT_DECK, Solitare.PLAY_DECK);
        }

        openCard(Solitare.BOARD_DECK_1);
        openCard(Solitare.BOARD_DECK_2);
        openCard(Solitare.BOARD_DECK_3);
        openCard(Solitare.BOARD_DECK_4);
        openCard(Solitare.BOARD_DECK_5);
        openCard(Solitare.BOARD_DECK_6);
        openCard(Solitare.BOARD_DECK_7);
    }

    private boolean moveCard(int from, int to) {
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
        boolean result = false;

        if (to == Solitare.PLAY_DECK || to == Solitare.OPENED_CARD_DECK) {
            return false;
        }

        if (count == 1) {
            return moveCard(from, to);
        }

        if (to >= Solitare.RESULT_DECK_1 && to <= Solitare.RESULT_DECK_4) {
            return false;
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
        if (deckNum == Solitare.PLAY_DECK) {
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
        if (moveCard(Solitare.PLAY_DECK, Solitare.OPENED_CARD_DECK)) {
            return deckList.get(Solitare.OPENED_CARD_DECK).openTopCard();
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
        return (deckList.get(Solitare.RESULT_DECK_1).size() == 13) &&
                (deckList.get(Solitare.RESULT_DECK_2).size() == 13) &&
                (deckList.get(Solitare.RESULT_DECK_3).size() == 13) &&
                (deckList.get(Solitare.RESULT_DECK_4).size() == 13);
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }
}