package com.chobocho.command;

import com.chobocho.solitaire.Solitare;

public class PlayFunction implements ComandFunction {
    @Override
    public boolean run(Solitare game, int from, int to, int count) {
        return game.play();
    }
}
