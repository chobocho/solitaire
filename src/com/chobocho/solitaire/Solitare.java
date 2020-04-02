package com.chobocho.solitaire;

import com.chobocho.deck.Deck;

/**
 * 
 */
public interface Solitare {
    int PLAY_DECK = 0;
    int RESULT_DECK_1 = 1;
    int RESULT_DECK_2 = 2;
    int RESULT_DECK_3 = 3;
    int RESULT_DECK_4 = 4;
    int BOARD_DECK_1 = 5;
    int BOARD_DECK_2 = 6;
    int BOARD_DECK_3 = 7;
    int BOARD_DECK_4 = 8;
    int BOARD_DECK_5 = 9;
    int BOARD_DECK_6 = 10;
    int BOARD_DECK_7 = 11;
    int OPENED_CARD_DECK = 12;
    int INIT_DECK = 13;

    public Deck getDeck(int deck);
    public boolean moveCard(int from, int to, int count);
    public boolean openDeck(int deck);
    public boolean revert();
    public boolean play();
    public boolean pause();
    public boolean winState();
    public boolean idle();
    public void register(GameObserver observer);

    public boolean isPlayState();
    public boolean isFinishGame();
    public int getMoveCount();

    boolean isMovableDeck(int deck);
}