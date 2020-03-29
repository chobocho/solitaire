package com.chobocho.solitaire;

public interface HistoryManager {
    void clear();
    BoardState get(int idx);
    boolean isEmpty();
    BoardState pop();
    void push(BoardState boardState);
    int size();
}
