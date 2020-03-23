package com.chobocho.command;

import com.chobocho.solitaire.Solitare;
import com.chobocho.util.CLog;

import java.util.HashMap;

public class CommandEngine {
    final static String TAG = "CommandEngine";
    Solitare game;
    HashMap<String, ComandFunction> functionMap;

    public CommandEngine(Solitare solitare) {
        this.game = solitare;
        this.functionMap = new HashMap<String, ComandFunction>();
        initFunction();
    }

    private void initFunction() {
        functionMap.put(PlayCommand.MOVE, new MoveFunction());
        functionMap.put(PlayCommand.OPEN, new OpenFunction());
        functionMap.put(PlayCommand.PLAY, new PlayFunction());
        functionMap.put(PlayCommand.PAUSE, new PauseFunction());
        functionMap.put(PlayCommand.IDLE, new IdleFunction());
        functionMap.put(PlayCommand.WIN, new WinFunction());
    }

    public boolean runCommand (PlayCommand command) {
        if (command == null) {
            CLog.i(TAG, "Command is null");
            return false;
        }
        CLog.i(TAG, command.toString());
        boolean result = functionMap.get(command.command).run(this.game, command.from, command.to, command.count);
        if (game.isFinishGame()) {
            return functionMap.get(PlayCommand.WIN).run(this.game, 0, 0, 0);
        }
        return result;
    }
}
