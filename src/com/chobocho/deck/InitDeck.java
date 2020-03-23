package com.chobocho.deck;

import com.chobocho.card.Card;

import java.util.Collections;
import java.util.LinkedList;

public class InitDeck implements Deck {
    private LinkedList<Card> deck;

    public InitDeck() {
        this.deck = new LinkedList<Card>();
        init();
    }

    public void init() {
        this.deck.clear();

        for (Card.FIGURE figure: Card.FIGURE.values()) {
            for(Card.NUMBER number: Card.NUMBER.values()) {
                if ((figure == Card.FIGURE.NONE) ||
                        (number == Card.NUMBER.NONE)) {
                    continue;
                }
                deck.push(new Card(figure, number));
            }
        }
        Collections.shuffle(deck);
    }

    public void clear() {
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
        /* Do nothing */
        return false;
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
