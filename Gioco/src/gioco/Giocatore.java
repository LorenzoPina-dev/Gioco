/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import java.security.Timestamp;
import java.util.Date;


/**
 *
 * @author user
 */
public class Giocatore {
    int vita,stamina,staminaPerSecondo;
    Punto posizione;
    int velocita;// n pixel percorsi/secondo
    boolean inVita;
    public Giocatore(){
        vita=100;
        stamina=500;
        staminaPerSecondo=100;
        posizione=new Punto(0,0);
        velocita=100;
        inVita=true;
    }
    
    public Giocatore(Punto p){
        vita=100;
        stamina=1000;
        staminaPerSecondo=100;
        posizione=p;
        velocita=200;
        inVita=true;
    }
    public void muovi(double angolo){
        posizione=PrediciMovimento(angolo);
    }
    public void muovi(Punto punto){
        posizione=punto;
    }
    public boolean SubisciDanni(int danni){
        System.out.println("TI ATTACCO");
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
            long last=Board.Init().last;
            if(stamina>staminaPerSecondo*((nuovo-last)/1000f))
            {
                stamina-=staminaPerSecondo*((nuovo-last)/1000f);
                ris= Punto.add(posizione,(int)( velocita*((nuovo-last)/1000f)),angolo);
            }
            return ris;
        }
        return null;
    }

    void AumentaStamina() {
        long nuovo=new Date().getTime();
        long last=Board.Init().last;
        stamina+=staminaPerSecondo/2*((nuovo-last)/1000f);
    }
}
