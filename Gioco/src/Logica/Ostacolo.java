/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Record.Punto;

/**
 *
 * @author user
 */
public class Ostacolo {

    public Punto posizione;
    public int altezza,lunghezza;
    
    public Ostacolo(Punto posizione, int altezza, int lunghezza) {
        this.posizione = posizione;
        this.altezza = altezza;
        this.lunghezza = lunghezza;
    }
    public boolean Hacolliso(Punto posizione){
        return this.posizione.x<=posizione.x&&posizione.x<this.posizione.x+lunghezza&&this.posizione.y<=posizione.y&&posizione.y<this.posizione.y+altezza;
    }
}
