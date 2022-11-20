/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Freccia;
import Logica.Mappa;
import Logica.Nemico;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ThreadNemico extends Thread{
    private int DELAY = 150;
    Long lastShot;
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
                        if(n.possoAttaccare() )
                            switch(n.tipo){
                                case stazionario:
                                    lanciaFreccia(n);
                                    break;
                                case tank:
                                    Mappa.Init().giocatore.SubisciDanni(n.danniInflitti);
                                    break;
                                case daLontano:
                                    if(n.getDistanza(Mappa.Init().giocatore.posizione)<20 )
                                    {
                                        long now=new Date().getTime();
                                        if(n.UltimaFreccia==null ||now-n.UltimaFreccia>2000)
                                        {
                                            Mappa.Init().giocatore.SubisciDanni(n.danniInflitti);
                                            n.UltimaFreccia=now;
                                        }
                                    }
                                    else
                                        lanciaFreccia(n);
                                    break;
                            }
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
    public void lanciaFreccia(Nemico n){
        long now=new Date().getTime();
        if(n.UltimaFreccia==null ||now-n.UltimaFreccia>2000)
        {    
            synchronized(Mappa.Init().syncfrecce)
            {
                Mappa.Init().FrecceInCampo.add(new Freccia(n.posizione,n.posizione.Direzione(Mappa.Init().giocatore.posizione)));
            }
            n.UltimaFreccia=now;
        }
    }
}