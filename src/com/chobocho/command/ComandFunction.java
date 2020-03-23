package com.chobocho.command;

import com.chobocho.solitaire.Solitare;

public interface ComandFunction {
    public boolean run(Solitare game, int from, int to, int count);
}
