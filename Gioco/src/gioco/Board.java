/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class Board  extends JPanel  {
    
    private  int B_WIDTH = 300;
    private  int B_HEIGHT = 300;
    public boolean inGame=true;
    long last;
    private static Board instance=null;
    Giocatore giocatore;
    Nemico n=new Nemico();
    public static Board Init(){
        if(instance==null)
            instance=new Board();
        return instance;
    }
    private Board(){
        giocatore=new Giocatore(new Punto(100,100));
        initBoard();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        last=new Date().getTime();
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if(inGame)
        {
            if(giocatore.inVita)
                g.drawRect(giocatore.posizione.x, giocatore.posizione.y, 10, 10);
            else
            {
                System.out.println("PERSO");
                inGame=false;
                gameOver(g);
            }
            if(n.inVita)
                g.fillRect(n.posizione.x, n.posizione.y, 10, 10);
        }
        else
            gameOver(g);
    }

    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
    
    private void initBoard() {

        addKeyListener(new Tasti(this));
        setBackground(Color.WHITE);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    }

}
