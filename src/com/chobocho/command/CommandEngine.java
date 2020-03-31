package com.chobocho.command;

import com.chobocho.solitaire.Solitare;
import com.chobocho.util.CLog;

import java.util.HashMap;

public class CommandEngine {
    final static String TAG = "CommandEngine";
    Solitare game;
    HashMap<String, ComandFunction> functionMap;
    boolean isRunning = false;

    public CommandEngine(Solitare solitare) {
        isRunning= false;
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
        functionMap.put(PlayCommand.BACK, new RevertFunction());
    }

    public boolean runCommand (PlayCommand command) {
        if (command == null) {
            CLog.i(TAG, "Command is null");
            return false;
        }
        CLog.i(TAG, command.toString());
        if (isRunning) {
            CLog.i(TAG, "Previous command is running!");
            return false;
        }
        isRunning = true;
        boolean result = functionMap.get(command.command).run(this.game, command.from, command.to, command.count);
        if (game.isFinishGame()) {
            isRunning = false;
            return functionMap.get(PlayCommand.WIN).run(this.game, 0, 0, 0);
        }
        isRunning = false;
        return result;
    }
}
