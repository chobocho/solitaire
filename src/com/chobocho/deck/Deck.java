package com.chobocho.deck;

import com.chobocho.card.Card;

public interface Deck {
    public boolean push(Card card);
    public Card pop();
    public boolean openTopCard();
    public Card get(int n);
    public Card top();
    public void clear();
    public void init();
    public int size();
}