package game.ui;

import com.chobocho.solitaire.Solitare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public interface DrawEngine {
    void onDraw(Graphics g, BufferedImage[] cardImages, Solitare game, LinkedList<Integer> hideCard);
}
