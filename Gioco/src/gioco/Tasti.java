/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Freccia;
import Logica.Mappa;
import Logica.Nemico;
import Record.Punto;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import util.ArmaGiocatore;

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
            Mappa.Init().giocatore.attacca();
        }
        if(key==KeyEvent.VK_Z)
        {
            switch (Mappa.Init().giocatore.armaAttuale) {
                case Spada:
                    Mappa.Init().giocatore.armaAttuale=ArmaGiocatore.Arco;
                    break;
                case Arco:
                    Mappa.Init().giocatore.armaAttuale=ArmaGiocatore.Scudo;
                    break;
                case Scudo:
                    Mappa.Init().giocatore.armaAttuale=ArmaGiocatore.Spada;
                    break;
            }
        }
    }
}
