package Logica;

import Record.Punto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public class Mappa {
    private static Mappa instance=null;
    public Giocatore giocatore;
    public List<Nemico> nemici;
    public List<Ostacolo> ostacoli,cure;
    public Ostacolo spada;
    public int dimensioneCelle=10;
    public int maxRighe=30,maxColonne=30;
    private Mappa(){
        giocatore=new Giocatore(new Punto(5,5),dimensioneCelle);
        InitMappa();
    }
    private void InitMappa(){
        
        nemici=new ArrayList<>();
        cure=new ArrayList<>();
        spada=new Ostacolo(new Punto((int)(Math.random()*maxRighe),(int)(Math.random()*maxColonne)),1,1);
        for(int i=0;i<(int)(Math.random()*5)+5;i++)
            cure.add(new Ostacolo(new Punto((int)(Math.random()*maxRighe),(int)(Math.random()*maxColonne)),1,1));
        for(int i=0;i<3;i++)
            nemici.add(new Nemico(new Punto((int)(Math.random()*maxRighe),(int)(Math.random()*maxColonne)),dimensioneCelle));
        for(Nemico n:nemici)
            n.tipo=TipoNemico.values()[(int)(Math.random()*3)];
        //nemici.add(new Nemico(new Punto(29,29),dimensioneCelle));
        //nemici.add(new Nemico(new Punto(0,29),dimensioneCelle));
        ostacoli=new ArrayList<>();
        for(int i=0;i<maxRighe;i++)
            for(int j=0;j<maxColonne;j++){
                Punto p=new Punto(i,j);
                if(Math.random()<0.15&&!p.equals(giocatore.posizione))
                {
                    boolean vaBene=true;
                    for(Nemico n:nemici)
                        if(p.equals(n.posizione))
                            vaBene=false;
                    if(p.equals(spada.posizione))
                        vaBene=false;
                    for(Ostacolo n:cure)
                        if(p.equals(n.posizione))
                            vaBene=false;
                    if(vaBene)
                        ostacoli.add(new Ostacolo(p,1,1));
                }
            }
    }
    public static Mappa Init(){
        if(instance==null)
            instance=new Mappa();
        return instance;
    }
    public void render(Graphics g){
        g.setColor(Color.green);
        if(giocatore.inVita)
            try {
                g.drawImage(ImageIO.read(new File("giocatore.png")) , giocatore.posizione.x*dimensioneCelle-dimensioneCelle/2, giocatore.posizione.y*dimensioneCelle-dimensioneCelle/2,dimensioneCelle*2,dimensioneCelle*2, null);
            } catch (IOException ex) {
                Logger.getLogger(Mappa.class.getName()).log(Level.SEVERE, null, ex);
            }
        else
        {
            System.out.println("PERSO");
        }
        g.setColor(Color.black);
        for(Ostacolo o:ostacoli)
            g.fillRect(o.posizione.x*dimensioneCelle,o.posizione.y*dimensioneCelle, o.altezza*dimensioneCelle, o.lunghezza*dimensioneCelle);
        g.setColor(Color.green);
        for(Ostacolo o:cure)
            try {
                g.drawImage(ImageIO.read(new File("Heart.png")) , o.posizione.x*dimensioneCelle, o.posizione.y*dimensioneCelle,o.altezza*dimensioneCelle*3/2,o.lunghezza*dimensioneCelle*3/2, null);
            } catch (IOException ex) {
                Logger.getLogger(Mappa.class.getName()).log(Level.SEVERE, null, ex);
            }
        for(Nemico n: nemici)
            if(n.inVita)
            {
                g.setColor(Color.orange);
                g.fillRect(n.posizione.x*dimensioneCelle, n.posizione.y*dimensioneCelle, dimensioneCelle, dimensioneCelle);
                g.setColor(Color.white);
                g.fillRect(n.posizione.x*dimensioneCelle-5, n.posizione.y*dimensioneCelle-5, 20, 5);
                g.setColor(Color.black);
                g.drawRect(n.posizione.x*dimensioneCelle-5, n.posizione.y*dimensioneCelle-5, 20, 5);
                g.setColor(Color.red);
                g.fillRect(n.posizione.x*dimensioneCelle-5, n.posizione.y*dimensioneCelle-5, (int)(n.vita/(float)n.maxVita*20), 5);
            }
        if(spada!=null)
            try {
                g.drawImage(ImageIO.read(new File("Sword.png")) , spada.posizione.x*dimensioneCelle, spada.posizione.y*dimensioneCelle,spada.altezza*dimensioneCelle*3/2,spada.lunghezza*dimensioneCelle*3/2, null);
            } catch (IOException ex) {
                Logger.getLogger(Mappa.class.getName()).log(Level.SEVERE, null, ex);
            }
        g.setColor(Color.white);
        g.fillRect(0,0, 150, 20);
        g.setColor(Color.black);
        g.drawRect(0,0, 150, 20);
        g.setColor(Color.red);
        g.fillRect(0, 0, (int)(giocatore.vita/(float)giocatore.MaxVita*150), 20);
        g.setColor(Color.white);
        g.fillRect(0,20, 150, 20);
        g.setColor(Color.black);
        g.drawRect(0,20, 150, 20);
        g.setColor(Color.yellow);
        g.fillRect(0, 20,(int)(giocatore.stamina/(float)giocatore.Maxstamina*150), 20);
    }
    
    public boolean Controllacollisioni() {
        if(giocatore.posizione.x<0||giocatore.posizione.x>=maxRighe||giocatore.posizione.y<0||giocatore.posizione.y>=maxColonne){
            InitMappa();
            return true;
        }
        for(Ostacolo o:ostacoli)
            if(o.Hacolliso(giocatore.posizione))
                return true;
        for(int i=0;i<cure.size();i++)
            if(cure.get(i).Hacolliso(giocatore.posizione)&&giocatore.vita<giocatore.MaxVita)
            {
                giocatore.AumentaVita();
                cure.remove(i);
            }
        if(spada !=null &&spada.Hacolliso(giocatore.posizione))
        {giocatore.AumentaDanno();
        spada=null;
        }
        return false;
    }
    public boolean Controllacollisioni(Punto punto) {
        if(punto.x<0||punto.x>=maxRighe||punto.y<0||punto.y>=maxColonne)
            return true;
        for(Ostacolo o:ostacoli)
            if(o.Hacolliso(punto))
                return true;
        return false;
    }
    public void AggiornaPercorsi(){
        for(Nemico n:nemici)
            if(n.getDistanza(giocatore.posizione)<60)
                n.RicalcolaPercorso(giocatore.posizione);
    }
}
