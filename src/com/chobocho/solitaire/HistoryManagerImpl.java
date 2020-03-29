package com.chobocho.solitaire;

import com.chobocho.deck.BoardDeck;

import java.util.LinkedList;

public class HistoryManagerImpl implements HistoryManager {
    LinkedList<BoardState> history;
    BoardState prevState;

    public HistoryManagerImpl() {
        history = new LinkedList<BoardState>();
        prevState = null;
    }

    @Override
    public void push(BoardState boardState) {
        if (prevState != null) {
            history.push(prevState);
        }
        prevState = boardState;
    }

    @Override
    public BoardState pop() {
        if (history.isEmpty()) {
            return null;
        }

        prevState = history.pop();

        return prevState;
    }

    @Override
    public boolean isEmpty() {
        return history.isEmpty();
    }

    @Override
    public void clear() {
        prevState = null;
        history.clear();
    }

    @Override
    public int size() {
        return history.size();
    }

    @Override
    public BoardState get(int idx) {
        if (history.isEmpty()) {
            return null;
        }
        return history.get(idx);
    }

    public String toString() {
        StringBuffer result = new StringBuffer();

        result.append("History: \n");
        for(int i = 0; i < history.size(); i++) {
            result.append( i + ": " + history.get(i).toString() + "\n");
        }
        return result.toString();
    }
}
