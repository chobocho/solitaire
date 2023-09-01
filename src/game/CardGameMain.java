package game;

import com.chobocho.command.CommandEngine;
import com.chobocho.solitaire.Solitare;
import com.chobocho.solitaire.SolitareImpl;

import javax.swing.*;
import java.awt.*;

public class CardGameMain extends JFrame {
    String TAG = "CardGameMain";
    String Version = "V0.1216.BI1";
    JLabel statusbar;

    public CardGameMain() {
        WinLog.i(TAG, "CardGameMain: " + Version);
        statusbar = new JLabel("Press S to play game");
        statusbar.setFont(new Font(statusbar.getFont().getFontName(), Font.PLAIN, 18));
        add(statusbar, BorderLayout.SOUTH);

        Solitare solitare = new SolitareImpl(new WinLog());
        CommandEngine cmdEngine = new CommandEngine(solitare);
        CardGameGui cardGameGui = new CardGameGui(this, solitare, cmdEngine);
        solitare.register(cardGameGui);
        add(cardGameGui);

        cardGameGui.start();

        setSize(800, 800);
        setTitle("Solitaire " + Version);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JLabel getStatusBar() {
        return statusbar;
    }

    public static void main(String[] args) {
        CardGameMain cardGame = new CardGameMain();
        cardGame.setLocationRelativeTo(null);
        cardGame.setVisible(true);
    }
}