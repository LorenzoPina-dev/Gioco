/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Tasti  extends KeyAdapter {
    Board b;

    public Tasti(Board b) {
        this.b=b;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A){
            b.giocatore.muovi(Math.PI);
        }

        if (key == KeyEvent.VK_D) {
            b.giocatore.muovi(0);
        }

        if (key == KeyEvent.VK_W){
            b.giocatore.muovi(Math.PI/2);
        }

        if (key == KeyEvent.VK_S){
            b.giocatore.muovi(Math.PI/2*3);
        }
    }
}
