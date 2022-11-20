/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Record.Punto;
import gioco.Board;
import java.security.Timestamp;
import java.util.Date;


/**
 *
 * @author user
 */

public class Giocatore {
    int MaxVita,vita,stamina,Maxstamina,staminaPerCasella,scala;
    public Punto posizione;
    int velocita;// n caselle percorse/secondo
    public boolean inVita;
    Long UltimoMovimento;
    public int danniInflitti;
    public double direzioneGuarda;
    public Giocatore(int scala){
        init(new Punto(0,0),scala);
    }
    private void init(Punto p,int scala){
        MaxVita=300;
        vita=MaxVita;
        Maxstamina=100;
        stamina=Maxstamina;
        staminaPerCasella=5;
        posizione=p;
        velocita=20;
        inVita=true;
        UltimoMovimento=null;
        this.scala=scala;
        danniInflitti=10;
    }
    
    public Giocatore(Punto p,int scala){
        init(p, scala);
    }
    public void muovi(double angolo){
        posizione=PrediciMovimento(angolo);
    }
    public void muovi(Punto punto){
        posizione=PrediciMovimento(punto);
        if(inVita)
            Mappa.Init().AggiornaPercorsi();
    }
    public boolean SubisciDanni(int danni){
        vita-=danni;
        if(vita<0)
        {
            inVita=false;
            return false;
        }
        return true;
    }
    
    public Punto PrediciMovimento(double angolo){
        if(inVita)
        {
            long nuovo=new Date().getTime();
            Punto ris=posizione;
            if(UltimoMovimento==null||nuovo-UltimoMovimento>=velocita/1000l &&stamina>staminaPerCasella)
            {
                UltimoMovimento=nuovo;
                ris=Punto.add(posizione, 1,angolo);
                stamina-=staminaPerCasella;
            }
            return ris;
        }
        return null;
    }

    public Punto PrediciMovimento(Punto p){
        if(inVita)
        {
            long nuovo=new Date().getTime();
            Punto last=posizione;
            if(UltimoMovimento==null||nuovo-UltimoMovimento>=velocita/1000l &&stamina>staminaPerCasella)
            {
                UltimoMovimento=nuovo;
                posizione=Punto.add(posizione,p);
                stamina-=staminaPerCasella;
            }
            if(!Mappa.Init().Controllacollisioni())
                return posizione;
            else
                return last;
        }
        return null;
    }
    public void AumentaStamina() {
        long nuovo=new Date().getTime();
        if(stamina<Maxstamina && UltimoMovimento!=null && nuovo-UltimoMovimento>200)
            stamina+=staminaPerCasella/5d;
    }
    public double getDistanza(Punto p) {
        return posizione.DistanzaDa(p)*scala;
    }

    public void AumentaVita() {
       vita+=20;       
       if(vita>MaxVita)
           vita=MaxVita;
    }
    public void AumentaDanno() {
       danniInflitti+=5;      
    }
}
