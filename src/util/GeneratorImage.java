package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class GeneratorImage {
    public static void main(String[] argv) throws Exception  {
        GeneratorImage imageMaker = new GeneratorImage();

        String[] figure = {"♣","♠","♥","◆"};
        String[] fileName = {"C", "S", "H", "D" };
        String[] numberName = {"N", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};


        for (int i = 0; i < figure.length; i++) {
            for (int j = 1; j <= 13; j++) {
                imageMaker.makeCardImage(fileName[i]+numberName[j] + ".png", figure[i], numberName[j]);
            }
        }

        imageMaker.makeCardImage("BG.png");
    }

    public void makeCardImage(String filename) throws Exception {
        int width = 100;
        int height = 150;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(new Color(82, 190, 128));
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(0, 0, 0));
        g2d.drawRect(0, 0, width-1, height-1);

        g2d.setColor(new Color(17, 122, 101));
        g2d.fillRect(10, 10, width-20, height-20);

        g2d.setColor(new Color(82, 190, 128));
        g2d.fillRect(15, 15, width-30, height-30);

        g2d.dispose();

        RenderedImage renderedImage = bufferedImage;

        File file = new File("src\\img\\" + filename);
        ImageIO.write(renderedImage, "png", file);
    }


    public void makeCardImage(String filename, String figure, String number) throws Exception {
        int width = 100;
        int height = 150;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(0, 0, 0));
        g2d.drawRect(0, 0, width-1, height-1);

        g2d.setColor(Color.black);
        g2d.drawRect(5, 5, width-10, height-10);

        if (filename.charAt(0)== 'S' || filename.charAt(0)== 'C') {
            g2d.setColor(Color.BLACK);
        } else {
            g2d.setColor(Color.RED);
        }
        int fontSize = 50;
        g2d.setFont(new Font("Purisa", Font.PLAIN, fontSize));
        g2d.drawString(figure, 55, fontSize);

        fontSize = 40;
        if (number.length() == 2) {
            fontSize=36;
        }
        g2d.setFont(new Font("Purisa", Font.PLAIN, fontSize));
        int pos = 10;
        g2d.drawString(number, pos, 45);

        fontSize = 80;
        g2d.setFont(new Font("Purisa", Font.PLAIN, fontSize));
        g2d.drawString(figure, 20, height-15);

        g2d.dispose();
        RenderedImage renderedImage = bufferedImage;

        File file = new File("src\\img\\" + filename);
        ImageIO.write(renderedImage, "png", file);
    }
}
