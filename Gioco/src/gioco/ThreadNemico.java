/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ThreadNemico extends Thread{
    private int DELAY = 150;
    @Override
    public void run(){
        while(Board.Init().n.inVita)
        {
            if(Board.Init().n.posizione.DistanzaDa(Board.Init().giocatore.posizione)<10)
                Board.Init().giocatore.SubisciDanni(Board.Init().n.danniInflitti);
            else
                Board.Init().n.muovi(Board.Init().n.posizione.Direzione(Board.Init().giocatore.posizione));
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadNemico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
