/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Mappa;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ThreadRender extends Thread{
    private int DELAY = 10;
    public ThreadRender(){
    }
    @Override
    public void run(){
        while(Board.Init().inGame)
        {
            try {
                Board.Init().repaint();
                Mappa.Init().giocatore.AumentaStamina();
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadRender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
