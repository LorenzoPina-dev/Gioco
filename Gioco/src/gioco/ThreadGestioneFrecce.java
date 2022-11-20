/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Freccia;
import Logica.Mappa;
import Record.Punto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ThreadGestioneFrecce extends Thread{
    @Override
    public void run(){
        while(true)
        {
            synchronized(Mappa.Init().syncfrecce)
            {
                for(int i=0;i<Mappa.Init().FrecceInCampo.size();i++)
                {
                    Punto p=Mappa.Init().FrecceInCampo.get(i).PrediciMovimento();
                    int dimensioni=Mappa.Init().dimensioneCelle;
                    Punto giocatore=Mappa.Init().giocatore.posizione;
                    if(Mappa.Init().Controllacollisioni(new Punto(p.x/dimensioni,p.y/dimensioni)))
                        Mappa.Init().FrecceInCampo.remove(i--);
                    else if(p.DistanzaDa(new Punto(giocatore.x*dimensioni,giocatore.y*dimensioni))<8)
                    {
                        Mappa.Init().giocatore.SubisciDanni(Mappa.Init().FrecceInCampo.get(i).danno);
                        Mappa.Init().FrecceInCampo.remove(i--);
                    }
                    else
                        Mappa.Init().FrecceInCampo.get(i).muovi(p);
                }
            }
            try {
                Thread.sleep(75 );
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGestioneFrecce.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
