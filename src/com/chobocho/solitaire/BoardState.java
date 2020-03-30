package com.chobocho.solitaire;

import com.chobocho.card.Card;

public class BoardState {
    final public static String TAG ="BoardState";
    public Short[] decks;

    public BoardState() { }

    public BoardState(short [] list) {
        decks = new Short[52];
        for (int i = 0; i < 52; i++) {
            decks[i] = list[i];
        }
    }

    public String toString() {
        if (decks == null) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        result.append("History: \n");
        for(int i = 0; i < 52; i++) {
            Card card = new Card(decks[i] & 0xff);
            result.append(i + ": deck " + (decks[i] >> 8) + " " + card.toString()  + "\n");
        }
        return result.toString();
    }
}
