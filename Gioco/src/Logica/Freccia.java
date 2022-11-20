/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Record.Punto;
import java.util.Date;

/**
 *
 * @author user
 */
public class Freccia {
    public double direzione;
    public Punto posizione;
    public int danno;
    public int velocita;
    public Long UltimoMovimento;
    public Freccia(Punto posizione,double direzione){
        velocita=1;
        danno=5;
        int dimensioni=Mappa.Init().dimensioneCelle;
        this.posizione=new Punto(posizione.x*dimensioni+dimensioni/4,posizione.y*dimensioni+dimensioni/4);
        UltimoMovimento=null;
        this.direzione=direzione;
    }
    public void muovi(Punto p){
        posizione=p;
    }
    public Punto PrediciMovimento(){
        long nuovo=new Date().getTime();
        Punto ris=posizione;
        if(UltimoMovimento==null||nuovo-UltimoMovimento>=velocita)
        {
            UltimoMovimento=nuovo;
            ris=Punto.add(posizione, 10,direzione);
        }
        return ris;
    }
}
