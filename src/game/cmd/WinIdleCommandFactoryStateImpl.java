package game.cmd;

import com.chobocho.command.*;
import com.chobocho.solitaire.PlayState;
import game.WinLog;

public class WinIdleCommandFactoryStateImpl extends IdleCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinIdleCommandFactoryStateImpl";
    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        WinLog.i(TAG, "Event:" + Integer.toString(event));
        if (event == CommandFactory.KEYPRESS_EVENT) {
            switch(x) {
                case 83:
                    return new PlayCommand(PlayCommand.PLAY, 0, 0);
            }
        }
        else if (event == CommandFactory.MOUSE_CLICK_EVENT) {
            for (ButtonPosition btn: buttons) {
                if (btn.isInRange(x, y)) {
                    if (btn.id.equals(PlayCommand.PLAY)) {
                        return new PlayCommand(btn.id, 0, 0);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        WinLog.i(TAG, "IdleState");
        return null;
    }

    @Override
    public void addButtons() {
        WinLog.i(TAG, "addButtons");
        buttons.push(new ButtonPosition(PlayCommand.PLAY, 300, 300, 300+200,300+100));
    }
}
