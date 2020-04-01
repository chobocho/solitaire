package game.cmd;

import com.chobocho.command.*;
import com.chobocho.solitaire.Solitare;
import game.WinLog;

public class WinPlayCommandFactoryStateImpl extends PlayCommandFactoryStateImpl implements CommandFactoryState {
    final static String TAG = "WinPlayCommandFactoryStateImpl";
    int width = 100;
    int heigth = 150;

    int playDeckX = 70 + width * 6;
    int playDeckY = 10;

    @Override
    public PlayCommand createCommand(int fromDeck, int fromPos, int toDeck, int toPos) {
        if (fromDeck == toDeck) {
            return new PlayCommand(PlayCommand.OPEN, fromDeck, fromDeck);
        }
        else {
            if (fromDeck == Solitare.PLAY_DECK) {
                return null;
            }
            int count = fromPos+1;
            return new PlayCommand(PlayCommand.MOVE, fromDeck, toDeck, count);
        }
    }

    @Override
    public PlayCommand createCommand(int event, int x, int y) {
        WinLog.i(TAG, "Event:" + Integer.toString(event));
        if (event == CommandFactory.KEYPRESS_EVENT) {
            switch(x) {
                case 49: // 1
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.RESULT_DECK_1);
                case 50: // 2
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.RESULT_DECK_2);
                case 51: // 3
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.RESULT_DECK_3);
                case 52: // 4
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.RESULT_DECK_4);

                case 65: // A
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_1);
                case 83: // S
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_2);
                case 68: // D
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_3);
                case 70: // F
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_4);
                case 71: // G
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_5);
                case 72: // H
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_6);
                case 74: // Jj
                    return new PlayCommand(PlayCommand.MOVE, Solitare.OPENED_CARD_DECK, Solitare.BOARD_DECK_7);

                case 81: // Q
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_1, y);
                case 87: // W
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_2, y);
                case 69: // E
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_3, y);
                case 82: // R
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_4, y);
                case 84: // T
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_5, y);
                case 89: // Y
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_6, y);
                case 85: // U
                    return new PlayCommand(PlayCommand.MOVE, Solitare.BOARD_DECK_7, y);
                case 79: // O
                case 44: // ,
                    return new PlayCommand(PlayCommand.OPEN, Solitare.PLAY_DECK, Solitare.PLAY_DECK);

                case 66: // B
                    return new PlayCommand(PlayCommand.BACK, 0, 0);

                case 27: // ESC
                case 80: // P
                    return new PlayCommand(PlayCommand.PAUSE, 0, 0);
            }
        } else if (event == CommandFactory.MOUSE_CLICK_EVENT) {

        } else {
            WinLog.i(TAG, "Unknown Event:" + Integer.toString(x) + " : " + Integer.toString(y));
        }
        return null;
    }
	
	@Override
    public void addButtons() {
        WinLog.i(TAG, "addButtons");
    }
}
