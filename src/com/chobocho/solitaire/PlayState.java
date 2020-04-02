package com.chobocho.solitaire;

import com.chobocho.card.Card;
import com.chobocho.deck.*;
import com.chobocho.util.CLog;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlayState extends GameState {
    final static String TAG = "PlayState";

    Deck[] resultDeck;
    Deck[] boardDeck;
    Deck playDeck;
    Deck opendCardDeck;
    Deck initDeck;
    ArrayList<Deck> deckList;
    HistoryManager history;

    int moveCount = 0;

    public PlayState() {
        initVars();
        initDeckList();
        initGame();
    }

    private void initVars() {
        history = new HistoryManagerImpl();
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
        history.clear();
        // CLog.i(TAG,this.toString());
        pushHistory();
        moveCount = 0;
        // CLog.i(TAG,history.toString());
    }

    private void initBoard() {
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
            result =  moveCard(from, to);
            if (result) {
                pushHistory();
            }
            return result;
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
            pushHistory();
            return true;
        } else {
            CLog.i(TAG, deck.toString());
            CLog.i(TAG, deckList.get(from).toString());
        }
        return false;
    }

    private void moveCardFroced(int from, int to, int count) {
        //TODO
    }
    @Override
    public boolean openCard(int deckNum) {
        boolean result = false;
        CLog.i(TAG, "openCard " + deckNum);
        if (deckNum == Solitare.PLAY_DECK) {
            result =  openPlayDecCard();
            if (result) {
                pushHistory();
            }
            return result;
        }
        result =  deckList.get(deckNum).openTopCard();
        if (result) {
            pushHistory();
        }
        return result;
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

        result.append("\n");
        for(int i = 0; i < deckList.size(); i++) {
            result.append(i + " size " + deckList.get(i).size() + ": " + deckList.get(i) + "\n");
        }

        result.append("History: \n");
        for(int i = 0; i < history.size(); i++) {
            result.append(history.get(i).toString() + "\n");
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
    public boolean revert() {
        if (history.isEmpty())  {
            CLog.i(TAG,"revert fail - history is empty");
            return false;
        }

        BoardState prevBoard = history.pop();

        //CLog.i(TAG, prevBoard.toString());
        Short[] source = prevBoard.decks;

        for (int i = Solitare.PLAY_DECK; i <= Solitare.OPENED_CARD_DECK; i++) {
            deckList.get(i).clear();
        }

        for (int j = prevBoard.decks.length-1; j >= 0; --j) {
            short card = prevBoard.decks[j];
            int deck = (card >> 8);
            deckList.get(deck).forcePush(new Card(card & 0xff));
        }

        moveCount++;
        return true;
    }

    private void pushHistory() {
        CLog.i(TAG,"pushHistory");
        history.push(new BoardState(getBoardState()));
        // CLog.i(TAG,history.toString());
        moveCount++;
    }

    private short[] getBoardState() {
        short[] result = new short[53];
        int k = 0;
        for (int i = Solitare.PLAY_DECK; i <= Solitare.OPENED_CARD_DECK; i++) {
            for (int j = 0; j < deckList.get(i).size(); j++) {
                result[k]  = (short)(i << 8 | deckList.get(i).get(j).toInt());
                k++;
            }
        }
        return result;
    }

    public int getMoveCount() {
        return moveCount;
    }

    boolean isMovableDeck(int deck) {
        return (deck != Solitare.PLAY_DECK);
    }
}