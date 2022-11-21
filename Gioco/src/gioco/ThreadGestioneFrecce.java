/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Freccia;
import Logica.Giocatore;
import Logica.Mappa;
import Logica.Nemico;
import Record.Punto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ArmaGiocatore;

/**
 *
 * @author user
 */
public class ThreadGestioneFrecce extends Thread {

    @Override
    public void run() {
        while (true) {
            synchronized (Mappa.Init().syncfrecce) {
                for (int i = 0; i < Mappa.Init().FrecceInCampo.size(); i++) {
                    boolean muovi = true;
                    List<Freccia> f = Mappa.Init().FrecceInCampo;
                    Giocatore g = Mappa.Init().giocatore;
                    Punto p = f.get(i).PrediciMovimento();
                    int dimensioni = Mappa.Init().dimensioneCelle;
                    Punto pCelle = new Punto(p.x / dimensioni, p.y / dimensioni);
                    if (Mappa.Init().Controllacollisioni(pCelle)) {
                        f.remove(i--);
                        muovi = false;
                    } else if (f.get(i).Nemica) {
                        if (pCelle.DistanzaDa(g.posizione) < 1) {
                            if (!g.possoParare(f.get(i).direzione))
                                g.SubisciDanni(f.get(i).danno);
                            muovi = false;
                            f.remove(i--);
                        }
                    } else if (!f.get(i).Nemica) 
                        synchronized (Mappa.Init().syncNemici) {
                            for (Nemico n : Mappa.Init().nemici) 
                                if (pCelle.DistanzaDa(n.posizione) < 1) {
                                    if (!n.SubisciDanni(f.get(i).danno)) 
                                        Mappa.Init().nemici.remove(n);
                                    f.remove(i--);
                                    muovi = false;
                                    break;
                                }
                        }
                    if (muovi)
                        f.get(i).muovi(p);
                }
            }
            try {
                Thread.sleep(75);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadGestioneFrecce.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
