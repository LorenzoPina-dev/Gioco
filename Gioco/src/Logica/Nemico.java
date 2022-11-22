/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import util.TipoNemico;
import PathFinding.AStar;
import Record.Punto;
import gioco.Board;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author user
 */
public class Nemico {
    public boolean boss;
    int vita,maxVita,stamina,staminaPerCasella;
    public Punto posizione;
    int velocita;// n caselle percorse/secondo
    int scala;
    public TipoNemico tipo;
    Long UltimoMovimento;
    public int danniInflitti;
    public Stack<Punto> prossimiPassi;
    public Object sync;
    public Long UltimaFreccia;
    public Nemico(int scala,int livello){
        init(new Punto(0,0),scala,livello);
    }
    private void init(Punto p,int scala,int livello){
        boss=false;
        maxVita=(int)(100*(1+livello/50f));
        if(boss)
            maxVita*=2;
        vita=maxVita;
        posizione=p;
        velocita=200;
        danniInflitti=(int)(10*(1+livello/50f));
        if(boss)
            danniInflitti*=2;
        prossimiPassi=new Stack<>();
        sync=new Object();
        this.scala=scala;
    }
    
    public Nemico(){
    }
    public Nemico(Punto p,int scala,int livello){
        init(p, scala,livello);
    }
    public Punto GetProssimoPasso(){
        Punto ris=posizione;
        long nuovo=new Date().getTime();
        if(UltimoMovimento==null||nuovo-UltimoMovimento>=velocita/1000d)
            synchronized (this) {
                switch(GestisciDistanze()){
                    case 1:
                        RicalcolaPercorso(Mappa.Init().giocatore.posizione);
                    case 0:
                        ris=prossimiPassi.pop();
                        break;
                    case -1:
                        ris=posizione;
                        break;
                }
            }
        return ris;
    }
    private int GestisciDistanze(){
        switch (tipo) {
            case stazionario:
                return -1;
            case tank:
                if(prossimiPassi.size()<2)
                    return 1;
                return 0;
            case daLontano:
                if(prossimiPassi.size()<=1)
                    return 1;
                if(prossimiPassi.size()<=6 && !Mappa.Init().ControllaOstacoli(Mappa.Init().giocatore,this))
                    return -1;
                return 0;
        }
        return 0;
    }
    
    public void RicalcolaPercorso(Punto destinazione){
        synchronized (this) {
            prossimiPassi=AStar.printPath(AStar.findPath(posizione, destinazione));
        }
    }
    public void muovi(Punto punto){
        posizione=punto;
    }
    public boolean SubisciDanni(int danni){
        vita-=danni;
        if(vita<0)
             return false;
        return true;
    }
    public double getDistanza(Punto p) {
        return posizione.DistanzaDa(p)*scala;
    }
    public boolean possoAttaccare(){
        if(GestisciDistanze()!=-1)
            return false;
        switch (tipo) {
            case stazionario:
                if(getDistanza(Mappa.Init().giocatore.posizione)<100 )
                    return true;
                break;
            case tank:
                if(getDistanza(Mappa.Init().giocatore.posizione)<20 )
                    return true;
                break;
            case daLontano:
                if(getDistanza(Mappa.Init().giocatore.posizione)<80 )
                    return true;
                break;
        }
        return false;
    }

    public boolean possoSparare(){
        return UltimaFreccia==null|| new Date().getTime()-UltimaFreccia>=3000;
    }
    public void lanciaFreccia(){
        synchronized(Mappa.Init().syncfrecce)
        {
            Mappa.Init().FrecceInCampo.add(new Freccia(posizione,posizione.Direzione(Mappa.Init().giocatore.posizione),danniInflitti));
        }
        UltimaFreccia=new Date().getTime();
    }
    public void attacca(){
        switch(tipo){
            case stazionario:
                if(possoSparare())
                    lanciaFreccia();
                break;
            case tank:
                if(possoSparare())
                    Mappa.Init().giocatore.SubisciDanni(danniInflitti);
                break;
            case daLontano:
                if(possoSparare())
                    lanciaFreccia();
                break;
        }
    }
}
