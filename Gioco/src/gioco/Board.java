/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Ostacolo;
import Logica.Nemico;
import Logica.Giocatore;
import Logica.Mappa;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class Board  extends JPanel  {
    
    private  int B_WIDTH = 300;
    private  int B_HEIGHT = 300;
    public boolean inGame=true;
    public long last;
    private static Board instance=null;
    public static Board Init(){
        if(instance==null)
            instance=new Board();
        return instance;
    }
    private Board(){
        initBoard();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        last=new Date().getTime();
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if(Mappa.Init().giocatore.inVita)
            Mappa.Init().render(g);
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

        addKeyListener(new Tasti());
        setBackground(Color.WHITE);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    }


}
