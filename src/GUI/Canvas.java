package GUI;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {


    private int squareW = 20;
    private int squareH = 20;

    private int borderStartX = 15;
    private int borderStartY = 10;
    private int borderX =  455;
    private int borderY =  255;
    Graphics g;

    public Canvas() {



    }


    private void drawHumanBeing(Double x,Long y){


    }
    private void drawBorder(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(borderStartX,borderStartY,this.borderX,this.borderY);
    }
    private void clear(Graphics g){
         g.clearRect(borderStartX,borderStartY,this.borderX,this.borderY);
    }
    private void cleanAnimation(Graphics g){

    }
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("ЧЕЛОВЕКИ:",borderStartX+1,borderStartY+1);
        drawBorder(g);
        cleanAnimation(g);
    }
}
