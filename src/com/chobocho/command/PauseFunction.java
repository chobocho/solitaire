package com.chobocho.command;

import com.chobocho.solitaire.Solitare;

public class PauseFunction implements ComandFunction {
    @Override
    public boolean run(Solitare game, int from, int to, int count) {
        return game.pause();
    }
}
