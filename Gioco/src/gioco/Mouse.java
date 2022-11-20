/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import Logica.Mappa;
import Record.Punto;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author user
 */
public class Mouse  implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Mappa.Init().giocatore.direzioneGuarda=Mappa.Init().giocatore.posizione.Direzione(new Punto(e.getX()/Mappa.Init().dimensioneCelle,e.getY()/Mappa.Init().dimensioneCelle));
    }
}
