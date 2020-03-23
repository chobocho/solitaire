package com.chobocho.deck;

import com.chobocho.card.Card;
import com.chobocho.util.CLog;

import java.util.LinkedList;

public class PlayDeck implements Deck {
    final static String TAG = "PlayDeck";
    private LinkedList<Card> deck;

    public PlayDeck() {
        this.deck = new LinkedList<Card>();
    }

    public void init() {
        this.deck.clear();
    }

    public boolean push(Card card) {
        this.deck.push(card);
        return true;
    }

    public Card pop() {
        return deck.pop();
    }

    public Card top() {
        return deck.peek();
    }

    public boolean openTopCard() {
        return deck.get(0).open();
    }

    public void clear() {
        this.deck.clear();
    }

    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    public String toString() {
        StringBuffer result = new StringBuffer();

        for(Card card : deck) {
            result.append(card + " ");
        }

        return result.toString();
    }

    public Card get(int n) {
        if(deck.isEmpty()) {
            return null;
        }
        return deck.get(n);
    }

    public int size() {
        return this.deck.size();
    }
}
