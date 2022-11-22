/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Freccia;
import Logica.Mappa;
import Logica.Nemico;
import Logica.Ostacolo;
import Record.Punto;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ThreadNemico extends Thread{
    private int DELAY = 100;
    @Override
    public void run(){
        boolean termina=false;
        while(!termina)
        {
            termina=true;
            try{
                for(Nemico n:Mappa.Init().nemici.values())
                {
                    if(Mappa.Init().giocatore.inVita){
                        termina=false;  
                        if(n.possoAttaccare() )
                            n.attacca();
                        else
                            //if(n.posizione.DistanzaDa(Mappa.Init().giocatore.posizione)<50)
                                try{
                                    n.muovi(n.GetProssimoPasso());
                                }catch (Exception ex) {
                                }
                    }
                }
            }catch (Exception ex) {
                        termina=false;  
            }
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadNemico.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        Map<Punto,Ostacolo> temp=new HashMap();
        temp.putAll(Mappa.Init().ostacoli);
        for(Punto p:Mappa.Init().ostacoli.keySet())
        {
            if(p.x==0&& p.y>Mappa.Init().maxRighe/2-2&&p.y<Mappa.Init().maxRighe/2+2)
                temp.remove(p);
            else if(p.y==0&& p.x>Mappa.Init().maxColonne/2-2&&p.x<Mappa.Init().maxColonne/2+2)
                temp.remove(p);
            else if(p.x==Mappa.Init().maxColonne-1&& p.y>Mappa.Init().maxRighe/2-2&&p.y<Mappa.Init().maxRighe/2+2)
                temp.remove(p);
            else if(p.y==Mappa.Init().maxRighe-1&& p.x>Mappa.Init().maxColonne/2-2&&p.x<Mappa.Init().maxColonne/2+2)
                temp.remove(p);
        }
        Mappa.Init().ostacoli.clear();
        Mappa.Init().ostacoli.putAll(temp);
    }
}