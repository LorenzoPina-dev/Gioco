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
public class ThreadRender extends Thread{
    private int DELAY = 140;
    public ThreadRender(){
    }
    @Override
    public void run(){
        while(Board.Init().inGame)
        {
            try {
                Board.Init().repaint();
                Board.Init().giocatore.AumentaStamina();
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadRender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
