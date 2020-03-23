package game;

import com.chobocho.card.Card;
import com.chobocho.command.*;
import com.chobocho.solitaire.GameObserver;
import com.chobocho.solitaire.GameState;
import com.chobocho.solitaire.PlayState;
import com.chobocho.solitaire.Solitare;
import game.cmd.DeckPositoinManagerImpl;
import game.ui.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardGameGui extends JPanel implements GameObserver {
    static final String TAG = "CardGameGui";
    JLabel statusbar;

    private Image screenBuffer = null;
    private Graphics graphicsBuffer = null;

    private Solitare solitare;
    private CommandEngine cmdEngine;
    private DrawEngine drawEngine;
    private DrawEngine idleDrawEngine;
    private DrawEngine playDrawEngine;
    private DrawEngine pauseDrawEngine;
    private DrawEngine endDrawEngine;
    private CommandFactory commandFactory;
    private DeckPositoinManager deckPositoinManager;

    BufferedImage[] cardImages = null;

    public final static int CARD_BG_IMAGE = 0;
    public final static int CARD_NONE_IMAGE = 53;
    private CardGameMouseAdapter cardGameMouseAdapter;

    String[] imageName = {
            "src\\img\\BG.png",
            "src\\img\\CA.png",
            "src\\img\\C2.png",
            "src\\img\\C3.png",
            "src\\img\\C4.png",
            "src\\img\\C5.png",
            "src\\img\\C6.png",
            "src\\img\\C7.png",
            "src\\img\\C8.png",
            "src\\img\\C9.png",
            "src\\img\\C10.png",
            "src\\img\\CJ.png",
            "src\\img\\CQ.png",
            "src\\img\\CK.png",
            "src\\img\\DA.png",
            "src\\img\\D2.png",
            "src\\img\\D3.png",
            "src\\img\\D4.png",
            "src\\img\\D5.png",
            "src\\img\\D6.png",
            "src\\img\\D7.png",
            "src\\img\\D8.png",
            "src\\img\\D9.png",
            "src\\img\\D10.png",
            "src\\img\\DJ.png",
            "src\\img\\DQ.png",
            "src\\img\\DK.png",
            "src\\img\\HA.png",
            "src\\img\\H2.png",
            "src\\img\\H3.png",
            "src\\img\\H4.png",
            "src\\img\\H5.png",
            "src\\img\\H6.png",
            "src\\img\\H7.png",
            "src\\img\\H8.png",
            "src\\img\\H9.png",
            "src\\img\\H10.png",
            "src\\img\\HJ.png",
            "src\\img\\HQ.png",
            "src\\img\\HK.png",
            "src\\img\\SA.png",
            "src\\img\\S2.png",
            "src\\img\\S3.png",
            "src\\img\\S4.png",
            "src\\img\\S5.png",
            "src\\img\\S6.png",
            "src\\img\\S7.png",
            "src\\img\\S8.png",
            "src\\img\\S9.png",
            "src\\img\\S10.png",
            "src\\img\\SJ.png",
            "src\\img\\SQ.png",
            "src\\img\\SK.png",
            "src\\img\\none.png"
    };

    public CardGameGui(CardGameMain parent,  Solitare solitare, CommandEngine cmdEngine) {
        loadImage();
        this.solitare = solitare;
        this.cmdEngine = cmdEngine;
        this.idleDrawEngine = new IdleDrawEngineImpl();
        this.playDrawEngine = new PlayDrawEngineImpl();
        this.pauseDrawEngine = new PauseDrawEngineImpl();
        this.endDrawEngine = new EndDrawEngineImpl();
        this.deckPositoinManager = new DeckPositoinManagerImpl();

        drawEngine = this.idleDrawEngine;

        commandFactory = new WindowCommandFactory();
        this.solitare.register(commandFactory);

        statusbar = parent.getStatusBar();
        setFocusable(true);
        addKeyListener(new CardGameKeyAdapter());
        cardGameMouseAdapter = new CardGameMouseAdapter();
        addMouseListener(cardGameMouseAdapter);
        addMouseMotionListener(new CardGameMouseMoveAdapter());
    }


    public void update() {
        repaint();
    }

    public void updateState(int state) {
        WinLog.i(TAG, "STATE: " + state);
        switch (state) {
            case GameState.IDLE_STATE:
                drawEngine = idleDrawEngine;
                break;
            case GameState.PLAY_STATE:
                drawEngine = playDrawEngine;
                break;
            case GameState.PAUSE_STATE:
                drawEngine = pauseDrawEngine;
                break;
            case GameState.END_STATE:
                drawEngine = endDrawEngine;
                break;
            default:
                break;
        }
        updateStatusBar(state);
        repaint();
    }

    private void updateStatusBar(int state) {
        switch (state) {
            case GameState.IDLE_STATE:
                statusbar.setText("Press S to start game!");
                break;
            case GameState.PLAY_STATE:
                statusbar.setText("Press ESC or P to pause game!");
                break;
            case GameState.PAUSE_STATE:
                statusbar.setText("Press S or R to resume game!");
                break;
            case GameState.END_STATE:
                statusbar.setText("Press S to start game!");
                break;
            default:
                break;
        }
    }

    public void start()
    {
        statusbar.setText("Press S to start game!");
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Dimension size = getSize();

        int width = (int)size.getWidth();
        int height = (int)size.getHeight();

        if (screenBuffer == null) {
            screenBuffer = createImage(width, height);
        }

        graphicsBuffer = screenBuffer.getGraphics();
        onDrawCommon(graphicsBuffer, width, height);

        drawEngine.onDraw(graphicsBuffer, cardImages, solitare, cardGameMouseAdapter.hideCard);

        if (cardGameMouseAdapter.isMovingCard) {
            for(int i = 0; i < cardGameMouseAdapter.hideCard.size(); i++) {
                graphicsBuffer.drawImage(cardImages[cardGameMouseAdapter.hideCard.get(i)], cardGameMouseAdapter.mouseX - 50, cardGameMouseAdapter.mouseY - 75 + i * 40, null);
            }
        }
        g.drawImage(screenBuffer, 0, 0, null);

        screenBuffer = null;
    }

    class CardGameKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            WinLog.i(TAG, Integer.toString(keycode));
            switch(keycode) {
                case KeyEvent.VK_1:
                case KeyEvent.VK_2:
                case KeyEvent.VK_3:
                case KeyEvent.VK_4:
                case KeyEvent.VK_S:
                case KeyEvent.VK_O:
                case KeyEvent.VK_A:
                case KeyEvent.VK_D:
                case KeyEvent.VK_F:
                case KeyEvent.VK_G:
                case KeyEvent.VK_H:
                case KeyEvent.VK_J:
                case KeyEvent.VK_P:
                case KeyEvent.VK_ESCAPE:
                    PlayCommand cmd = commandFactory.CreateCommand(CommandFactory.KEYPRESS_EVENT, keycode, 0);
                    if (cmdEngine.runCommand(cmd)){
                        repaint();
                    }
                    break;

                case KeyEvent.VK_Q:
                case KeyEvent.VK_W:
                case KeyEvent.VK_E:
                case KeyEvent.VK_R:
                case KeyEvent.VK_T:
                case KeyEvent.VK_Y:
                case KeyEvent.VK_U:
                    for (int i = 0; i < 4; i++) {
                        PlayCommand moveCmd = commandFactory.CreateCommand(CommandFactory.KEYPRESS_EVENT, keycode, i+PlayState.RESULT_DECK_1);
                        if (cmdEngine.runCommand(moveCmd)) {
                            repaint();
                            break;
                        }
                    }
                    break;
            }
        }
    }

    class CardGameMouseAdapter extends MouseAdapter {
        public CardPosition StartPos;
        public CardPosition EndPos;
        private boolean isMovingCard = false;
        public LinkedList<Integer> hideCard = new LinkedList<Integer>();
        private int mouseX;
        private int mouseY;

        public void mouseClicked(MouseEvent e) {
            WinLog.i(TAG, "Mouse Clicked " + e.getX() + ":" + e.getY());

            deckPositoinManager.initCardPosition(solitare);

            int mx = e.getX();
            int my = e.getY();

            PlayCommand cmd = commandFactory.CreateCommand(CommandFactory.MOUSE_CLICK_EVENT, mx, my);
            if (cmdEngine.runCommand(cmd)) {
                repaint();
                return;
            }

            CardPosition pos = deckPositoinManager.getCardInfo(mx, my);
            if (pos == null) {
                return;
            }
            if (pos.deck == PlayState.RESULT_DECK_1 ||
                    pos.deck == PlayState.RESULT_DECK_2 ||
                    pos.deck == PlayState.RESULT_DECK_3 ||
                    pos.deck == PlayState.RESULT_DECK_4 ||
                    pos.deck == PlayState.PLAY_DECK) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                PlayCommand moveCmd = commandFactory.CreateCommand(pos.deck, 0, i + PlayState.RESULT_DECK_1, 0);
                if (cmdEngine.runCommand(moveCmd)) {
                    repaint();
                    break;
                }
            }


        }
        public void mouseEntered(MouseEvent e) {
            WinLog.i(TAG, "Mouse Entered" + e.getX() + ":" + e.getY());
            //isMovingCard = false;
        }
        public void mouseExited(MouseEvent e) {
            WinLog.i(TAG, "Mouse Exited");
            //if (isMovingCard) {
            //    repaint();
           // }
        }
        public void mousePressed(MouseEvent e) {
            WinLog.i(TAG, "Mouse Pressed " + e.getX() + ":" + e.getY());
            mouseX = e.getX();
            mouseY = e.getY();

            deckPositoinManager.initCardPosition(solitare);

            StartPos = deckPositoinManager.getCardInfo(mouseX, mouseY);

            if (StartPos != null) {
                if (StartPos.deck == PlayState.PLAY_DECK) {
                    isMovingCard = false;
                } else {
                    makeHideCardList();
                    isMovingCard = true;
                }
                WinLog.i(TAG,"StartDeck :" + StartPos.toString());
            }
        }

        private void makeHideCardList() {
            hideCard.clear();

           if( drawEngine != playDrawEngine) {
               return;
           }

            int deck = StartPos.deck;
            int moveCount = StartPos.position + 1;

            WinLog.i(TAG, "paint " + Integer.toString(deck) + ":" + Integer.toString(moveCount));

            for (int i = 0; i < moveCount; i++) {
                Card card = solitare.getDeck(deck).get(i);
                if (card != null) {
                    // WinLog.i(TAG, card.toString());
                    int cardNumber = (card.getFigure().getValue() - 1) * 13 + card.getNumber().getValue();
                    hideCard.push(cardNumber);
                    // WinLog.i(TAG, card.toString() + " : " + Integer.toString(imageNumber));
                } else {
                    WinLog.i(TAG, "Card is null!");
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            WinLog.i(TAG, "Mouse Released "+ e.getX() + ":" + e.getY());

            if (StartPos == null) {
                return;
            }

            mouseX = e.getX();
            mouseY = e.getY();

            if (isMovingCard) {
            //    mouseX += 50;
            //    mouseY += 75;
            }

            hideCard.clear();

            EndPos = deckPositoinManager.getCardInfo(mouseX, mouseY);

            if (EndPos == null) {
                if (isMovingCard) {
                    repaint();
                }
                isMovingCard = false;
                return;
            }
            
            WinLog.i(TAG,"RelesedDeck :" + EndPos.deck);
            WinLog.i(TAG, "Mouse Released "+ StartPos.toString());
            WinLog.i(TAG, "Mouse Released "+ EndPos.toString());

            // WinLog.i(TAG, deckPositoinManager.toString());
            PlayCommand cmd = commandFactory.CreateCommand(StartPos.deck, StartPos.position, EndPos.deck, EndPos.position);

            if (cmdEngine.runCommand(cmd) || isMovingCard) {
                isMovingCard = false;
                repaint();
            }

        }
    }

    class CardGameMouseMoveAdapter extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
           // WinLog.i(TAG, "mouseDragged" + e.getX() + ":" + e.getY());
            if (cardGameMouseAdapter.isMovingCard) {
                cardGameMouseAdapter.mouseX = e.getX();
                cardGameMouseAdapter.mouseY = e.getY();
                repaint();
            }
        }

        public void mouseMoved(MouseEvent e) {
           // WinLog.i(TAG, "mouseMoved" + e.getX() + ":" + e.getY()););
        }
    }

    private void loadImage() {
        cardImages = new BufferedImage[imageName.length+1];
        try {
            for (int i = 0; i < imageName.length; i++) {
                cardImages[i] = ImageIO.read(new File(imageName[i]));
                WinLog.i(TAG, "Load image Success! " + imageName[i]);
            }
            WinLog.i(TAG, "Load image Success!");
        } catch (IOException e) {
            WinLog.e(TAG, "Load image Error!!\n"+e);
        }
    }

    private void onDrawCommon(Graphics g, int screenW, int screenH) {
        graphicsBuffer.setColor(new Color(88, 214, 141 ));
        graphicsBuffer.fillRect(0, 0, screenW, screenH);

        int width = 100;
        int height = 150;

        // Result deck
        g.drawImage(cardImages[CARD_NONE_IMAGE], 10, 10, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 20+width, 10, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 30+width*2, 10, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 40+width*3, 10, null);

        g.drawImage(cardImages[0], 70+width*6, 10, null);

        g.drawImage(cardImages[CARD_NONE_IMAGE], 10, 20+height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 20+width, 20+height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 30+width*2, 20+height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 40+width*3, 20+height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 50+width*4, 20+height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 60+width*5, 20+height, null);
        g.drawImage(cardImages[CARD_NONE_IMAGE], 70+width*6, 20+height, null);
    }


}