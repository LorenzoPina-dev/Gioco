/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Mappa;
import Logica.Nemico;
import Record.Punto;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Tasti  extends KeyAdapter {

    
    @Override
    public void keyPressed(KeyEvent e) {
        Mappa b=Mappa.Init();
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A){
            b.giocatore.muovi(new Punto(-1,0));
        }

        if (key == KeyEvent.VK_D) {
            b.giocatore.muovi(new Punto(1,0));
        }

        if (key == KeyEvent.VK_W){
            b.giocatore.muovi(new Punto(0,-1));
        }

        if (key == KeyEvent.VK_S){
            b.giocatore.muovi(new Punto(0,1));
        }
        if (key == KeyEvent.VK_Q){
            for(Nemico n:Mappa.Init().nemici)
                if(n.getDistanza(Mappa.Init().giocatore.posizione)<20 && n.inVita&& Mappa.Init().giocatore.posizione.Direzione(n.posizione)-Mappa.Init().giocatore.direzioneGuarda<Math.PI/12)
                {
                    n.SubisciDanni(Mappa.Init().giocatore.danniInflitti);
                    break;
                }
        }
    }
}
