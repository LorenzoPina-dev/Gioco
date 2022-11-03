/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Mappa;
import Logica.Nemico;
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
        boolean termina=false;
        while(!termina)
        {
            termina=true;
            for(Nemico n:Mappa.Init().nemici)
            {
                if(Mappa.Init().giocatore.inVita){
                    termina=false;  
                    if(n.inVita)
                        if(n.getDistanza(Mappa.Init().giocatore.posizione)<20)
                            Mappa.Init().giocatore.SubisciDanni(n.danniInflitti);
                        else
                        //if(n.posizione.DistanzaDa(Mappa.Init().giocatore.posizione)<50)
                            try{
                            n.muovi(n.GetProssimoPasso());
                            }catch (Exception ex) {
                            }
                }
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadNemico.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
