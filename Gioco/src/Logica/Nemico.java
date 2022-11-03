/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

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
    int vita,maxVita,stamina,staminaPerCasella;
    public Punto posizione;
    int velocita;// n caselle percorse/secondo
    public boolean inVita;
    int scala;
    TipoNemico tipo;
    Long UltimoMovimento;
    public int danniInflitti;
    public Stack<Punto> prossimiPassi;
    public Object sync;
    public Nemico(int scala){
        init(new Punto(0,0),scala);
    }
    private void init(Punto p,int scala){
        maxVita=100;
        vita=maxVita;
        posizione=p;
        velocita=100;
        inVita=true;
        danniInflitti=10;
        prossimiPassi=new Stack<>();
        sync=new Object();
        this.scala=scala;
    }
    
    public Nemico(Punto p,int scala){
        init(p, scala);
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
                if(prossimiPassi.size()<=1)
                    return 1;
                return 0;
            case daLontano:
                if(prossimiPassi.size()<=6)
                    return 1;
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
            inVita=false;
        return inVita;
    }
    public double getDistanza(Punto p) {
        return posizione.DistanzaDa(p)*scala;
    }

}
