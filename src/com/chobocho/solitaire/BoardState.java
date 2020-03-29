package com.chobocho.solitaire;

import com.chobocho.card.Card;
import com.chobocho.deck.Deck;
import com.chobocho.deck.PlayDeck;
import com.chobocho.util.CLog;

import java.util.ArrayList;

public class BoardState {
    final public static String TAG ="BoardState";
    public ArrayList<Deck> decks = new ArrayList<Deck>();

    public BoardState() { }

    public BoardState(ArrayList<Deck> deckList) {
        for (int i = Solitare.PLAY_DECK; i <= Solitare.OPENED_CARD_DECK; i++) {
            Deck target = new PlayDeck();
            Deck source = deckList.get(i);
            if (!source.isEmpty()) {
                // CLog.i(TAG,  "" + i + " " + source.size());
                for (int j = source.size() - 1; j >= 0; --j) {
                    Card card = new Card(source.get(j));
                    target.push(card);
                }
            }
            decks.add(target);
        }
    }

    public String toString() {
        StringBuffer result = new StringBuffer();

        result.append("History: \n");
        for(int i = 0; i < decks.size(); i++) {
            result.append(i + ": " + decks.get(i).toString() + "\n");
        }
        return result.toString();
    }
}
