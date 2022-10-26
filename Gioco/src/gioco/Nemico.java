/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gioco;

import java.util.Date;

/**
 *
 * @author user
 */
public class Nemico {
    Punto posizione;
    int danniInflitti;
    int velocita,vita;
    boolean inVita;
    public Nemico(){
        vita=100;
        posizione=new Punto(0,0);
        velocita=200;
        inVita=true;
        danniInflitti=10;
    }
    
    public Nemico(Punto p){
        vita=100;
        posizione=p;
        velocita=40;
        inVita=true;
        danniInflitti=10;
    }
    public void muovi(double angolo){
        System.out.println("angolo:"+angolo);
        if(PrediciMovimento(angolo)!=null)
            posizione=PrediciMovimento(angolo);
    }
    public void muovi(Punto punto){
        posizione=punto;
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
            long last=Board.Init().last;
            if((nuovo-last)<1000)
                ris= Punto.add(posizione,(int)( velocita*((nuovo-last)/1000f)),angolo);
            System.out.println(nuovo-last+" "+ris);
            return ris;
        }
        return null;
    }
}
