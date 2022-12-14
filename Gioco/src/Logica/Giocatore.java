/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Record.Punto;
import gioco.Board;
import java.security.Timestamp;
import java.util.Date;
import util.ArmaGiocatore;


/**
 *
 * @author user
 */

public class Giocatore {
    int MaxVita,vita,stamina,Maxstamina,staminaPerCasella,scala;
    public Punto posizione;
    int velocita;// n caselle percorse/secondo
    public boolean inVita;
    Long UltimoMovimento,ultimaFreccia;
    public int danniInflitti;
    public double direzioneGuarda;
    public ArmaGiocatore armaAttuale;
    public Giocatore(int scala){
        init(new Punto(0,0),scala);
    }
    private void init(Punto p,int scala){
        armaAttuale=ArmaGiocatore.Spada;
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

    public Punto PrediciMovimento(Punto punto){
        if(inVita)
        {
            long nuovo=new Date().getTime();
            Punto last=posizione;
            if(UltimoMovimento==null||nuovo-UltimoMovimento>=velocita/1000l &&stamina>staminaPerCasella)
            {
                UltimoMovimento=nuovo;
                posizione=Punto.add(posizione,punto);
                stamina-=staminaPerCasella;
            }
            int ris=Mappa.Init().Controllacollisioni();
            if(ris==0)
                return posizione;
            else if(ris==1)
                return last;
            else
            {
                Punto p=new Punto();
                if(posizione.x==-1)
                    p= Punto.add(posizione,new Punto(2,0));
                else if(posizione.x==Mappa.Init().maxColonne)
                    p= Punto.add(posizione,new Punto(-2,0));
                else if(posizione.y==-1)
                    p= Punto.add(posizione,new Punto(0,2));
                else if(posizione.y==Mappa.Init().maxRighe)
                    p= Punto.add(posizione,new Punto(0,-2));
                return p;
            }
        }
        return null;
    }
    public void AumentaStamina() {
        long nuovo=new Date().getTime();
        if(stamina<Maxstamina && UltimoMovimento!=null && nuovo-UltimoMovimento>200)
            stamina+=staminaPerCasella/2d;
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
    public boolean possoSparare(){
        return ultimaFreccia==null|| new Date().getTime()-ultimaFreccia>=100;
    }
    public void Spara(){
        synchronized(Mappa.Init().syncfrecce)
        {
            Mappa.Init().FrecceInCampo.add(new Freccia(Mappa.Init().giocatore.posizione,Mappa.Init().giocatore.direzioneGuarda,Mappa.Init().giocatore.danniInflitti,false));
        }
        ultimaFreccia=new Date().getTime();
    }
    public boolean possoParare(double direzione){
        if(armaAttuale==ArmaGiocatore.Scudo)
            System.out.println("Scudo");
        return !(armaAttuale!=ArmaGiocatore.Scudo || Math.abs(((direzione+Math.PI)%(2*Math.PI))-Math.abs(direzioneGuarda))>Math.PI/8);
    }
    public void attacca(){
        switch (armaAttuale) {
                case Spada:
                    synchronized (Mappa.Init().syncNemici) {
                        for(Punto p:Mappa.Init().nemici.keySet())
                            if(getDistanza(Mappa.Init().nemici.get(p).posizione)<20&& posizione.Direzione(Mappa.Init().nemici.get(p).posizione)-direzioneGuarda<Math.PI/12)
                            {
                                if(!Mappa.Init().nemici.get(p).SubisciDanni(danniInflitti))
                                    Mappa.Init().nemici.remove(p);
                                break;
                            }
                    }
                    break;
                case Arco:
                    if(possoSparare())
                        Spara();
                    break;
            }
    }
}
