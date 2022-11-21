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
    public boolean Nemica;
    public Freccia(){}
    public Freccia(Punto posizione,double direzione,int danno){
        velocita=1;
        this.danno=danno;
        int dimensioni=Mappa.Init().dimensioneCelle;
        this.posizione=new Punto(posizione.x*dimensioni+dimensioni/2,posizione.y*dimensioni+dimensioni/2);
        UltimoMovimento=null;
        this.direzione=direzione;
        Nemica=true;
    }
    public Freccia(Punto posizione,double direzione,int danno,boolean team){
        velocita=1;
        this.danno=danno;
        int dimensioni=Mappa.Init().dimensioneCelle;
        this.posizione=new Punto(posizione.x*dimensioni+dimensioni/2,posizione.y*dimensioni+dimensioni/2);
        UltimoMovimento=null;
        this.direzione=direzione;
        Nemica=team;
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
