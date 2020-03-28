package com.chobocho.solitaire;

import com.chobocho.deck.Deck;

/**
 * 
 */
public interface Solitare {
    public Deck getDeck(int deck);
    public boolean moveCard(int from, int to, int count);
    public boolean openDeck(int deck);
    public boolean play();
    public boolean pause();
    public boolean winState();
    public boolean idle();
    public void register(GameObserver observer);

    public boolean isPlayState();
    public boolean isFinishGame();
    public int getMoveCount();
}