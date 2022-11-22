package Logica;

import util.TipoNemico;
import Record.Punto;
import gioco.Board;
import gioco.ThreadNemico;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import util.UtilGrafica;

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
    public Map<Punto,Nemico> nemici;
    public Map<Punto,Ostacolo> ostacoli,cure;
    public Ostacolo spada;
    public int dimensioneCelle=10;
    public int maxRighe=30,maxColonne=30;
    public List<Freccia> FrecceInCampo;
    public Object syncfrecce,syncNemici;
    int livello;
    private Mappa(){
        livello=0;
        giocatore=new Giocatore(new Punto(5,5),dimensioneCelle);
        InitMappa();
    }
    private void InitMappa(){
        livello++;
        syncfrecce=new Object();
        syncNemici=new Object();
        FrecceInCampo=new ArrayList();
        nemici=new HashMap<>();
        cure=new HashMap();
        ostacoli=new HashMap();
        for(int i=0;i<maxColonne;i++)
        {
            Punto inizio=new Punto(0,i),fine=new Punto(maxRighe-1,i);
            ostacoli.put(inizio,new Ostacolo(inizio,1,1));
            ostacoli.put(fine,new Ostacolo(fine,1,1));
        }
        for(int i=0;i<maxRighe;i++)
        {
            Punto inizio=new Punto(i,0),fine=new Punto(i,maxColonne-1);
            ostacoli.put(inizio,new Ostacolo(inizio,1,1));
            ostacoli.put(fine,new Ostacolo(fine,1,1));
        }
        boolean vaBene=true;
        while(vaBene)
        {
            Punto p=new Punto((int)(Math.random()*maxRighe),(int)(Math.random()*maxColonne));
            vaBene=true;
            if(!ostacoli.containsKey(p))
            {
                spada=new Ostacolo(p,1,1);
                vaBene=false;
            }
        }
        int NCure=0,maxCure=(int)(Math.random()*5)+3;
        while(NCure<maxCure)
        {
            Punto p=new Punto((int)(Math.random()*(maxRighe-5))+5,(int)(Math.random()*(maxColonne-5))+5);
            if(!ostacoli.containsKey(p))
            {
                cure.put(p,new Ostacolo(p,1,1));
                NCure++;
            }
        }
        InitNemici();
        if(livello%10==0){
            for(int i=0;i<maxRighe;i++)
                for(int j=0;j<maxColonne;j++){
                    Punto p=new Punto(i,j);
                    if(Math.random()<0.15&&!p.equals(giocatore.posizione))
                    {
                        vaBene=true;
                        if(nemici.containsKey(p))
                            vaBene=false;
                        if(p.equals(spada.posizione))
                            vaBene=false;
                        if(cure.containsKey(p))
                            vaBene=false;
                        if(ostacoli.containsKey(p))
                            vaBene=false;
                        if(vaBene)
                            ostacoli.put(p,new Ostacolo(p,1,1));
                    }
                }
        }
        else
        {
            int dimRiga=maxRighe/5,dimColonne=maxColonne/5;
            for(int i=0;i<dimRiga;i++)
            {
                Punto p=new Punto(dimColonne/2,dimRiga*2+i);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne*4+dimColonne/2-1,dimRiga*2+i);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne,dimRiga+i);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne*4-1,dimRiga+i);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne,dimRiga*3+i);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne*4-1,dimRiga*3+i);
                ostacoli.put(p,new Ostacolo(p,1,1));
            }
            for(int i=0;i<dimColonne;i++)
            {
                Punto p=new Punto(dimColonne*2+i,dimRiga/2);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne*2+i,dimRiga*4+dimRiga/2-1);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne+i,dimRiga);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne+i,dimRiga*4-1);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne*3+i,dimRiga);
                ostacoli.put(p,new Ostacolo(p,1,1));
                p=new Punto(dimColonne*3+i,dimRiga*4-1);
                ostacoli.put(p,new Ostacolo(p,1,1));
            }
            
        }
        new ThreadNemico().start();
    }
    public void InitNemici(){
        if(livello%10!=0)
        {
            int Nnemici=0,maxNemici=3+livello/5;
            while(Nnemici<maxNemici)
            {
                Punto p=new Punto((int)(Math.random()*(maxRighe-5))+5,(int)(Math.random()*(maxColonne-5))+5);
                if(!ostacoli.containsKey(p)&& !cure.containsKey(p))
                {
                    nemici.put(p,new Nemico(p,dimensioneCelle,livello));
                    Nnemici++;
                }
            } 
        }
        for(Nemico n:nemici.values())
            n.tipo=TipoNemico.values()[(int)(Math.random()*3)];
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
            System.out.println("PERSO");
        g.setColor(Color.black);
        for(Ostacolo o:ostacoli.values())
            g.fillRect(o.posizione.x*dimensioneCelle,o.posizione.y*dimensioneCelle, o.altezza*dimensioneCelle, o.lunghezza*dimensioneCelle);
        g.setColor(Color.green);
        for(Ostacolo o:cure.values())
            try {
                g.drawImage(ImageIO.read(new File("Heart.png")) , o.posizione.x*dimensioneCelle, o.posizione.y*dimensioneCelle,o.altezza*dimensioneCelle*3/2,o.lunghezza*dimensioneCelle*3/2, null);
            } catch (IOException ex) {
                Logger.getLogger(Mappa.class.getName()).log(Level.SEVERE, null, ex);
            }
        for(Nemico n: nemici.values())
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
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
        g.drawString(livello+"", maxColonne*dimensioneCelle-30,30);
        Punto Posizionegiocatore=new Punto(giocatore.posizione.x*dimensioneCelle,giocatore.posizione.y*dimensioneCelle);
        Graphics2D g2d = (Graphics2D)g;
        try {
            g2d.translate(giocatore.posizione.x*dimensioneCelle+dimensioneCelle/2,giocatore.posizione.y*dimensioneCelle+dimensioneCelle/2);
            g2d.rotate((giocatore.direzioneGuarda+Math.PI/4)*-1);
            switch (giocatore.armaAttuale) {
                case Spada:
                    g.drawImage(UtilGrafica.rotateImageByDegrees(ImageIO.read(new File("Sword.png")), 90), 0,0,dimensioneCelle*3/2,dimensioneCelle*3/2, null);
                    break;
                case Arco:
                    g.drawImage(UtilGrafica.rotateImageByDegrees(ImageIO.read(new File("Bow.png")), 90), 0,0,dimensioneCelle*3/2,dimensioneCelle*3/2, null);
                    break;
                case Scudo:
                    g.drawImage(UtilGrafica.rotateImageByDegrees(ImageIO.read(new File("Shield.png")), 90), 0,0,dimensioneCelle*3/2,dimensioneCelle*3/2, null);
                    break;
            }
            g2d.rotate(giocatore.direzioneGuarda+Math.PI/4);
            g2d.translate((giocatore.posizione.x*dimensioneCelle+dimensioneCelle/2)*-1,(giocatore.posizione.y*dimensioneCelle+dimensioneCelle/2)*-1);
        } catch (IOException ex) {
            Logger.getLogger(Mappa.class.getName()).log(Level.SEVERE, null, ex);
        }
        synchronized(Mappa.Init().syncfrecce)
        {
            for(Freccia f:FrecceInCampo)
                try {
                    g2d.translate(f.posizione.x,f.posizione.y);
                    g2d.rotate((f.direzione-Math.PI/4)*-1);
                    g.drawImage(ImageIO.read(new File("Arrow.png")) , -dimensioneCelle/2,-dimensioneCelle/2,dimensioneCelle,dimensioneCelle, null);
                    g2d.rotate(f.direzione-Math.PI/4);
                    g2d.translate((f.posizione.x)*-1,(f.posizione.y)*-1);
                } catch (IOException ex) {
                    Logger.getLogger(Mappa.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    public int Controllacollisioni() {
        if(giocatore.posizione.x<0||giocatore.posizione.x>=maxRighe||giocatore.posizione.y<0||giocatore.posizione.y>=maxColonne){
            InitMappa();
            return -1;
        }
        for(Ostacolo o:ostacoli.values())
            if(o.Hacolliso(giocatore.posizione))
                return 1;
        for(Punto p:cure.keySet())
            if(cure.get(p).Hacolliso(giocatore.posizione)&&giocatore.vita<giocatore.MaxVita)
            {
                giocatore.AumentaVita();
                cure.remove(p);
                break;
            }
        if(spada !=null &&spada.Hacolliso(giocatore.posizione))
        {
            giocatore.AumentaDanno();
            spada=null;
        }
        return 0;
    }
    public boolean Controllacollisioni(Punto punto) {
        if(punto.x<0||punto.x>=maxRighe||punto.y<0||punto.y>=maxColonne)
            return true;
        for(Ostacolo o:ostacoli.values())
            if(o.Hacolliso(punto))
                return true;
        return false;
    }
    public void AggiornaPercorsi(){
        for(Nemico n:nemici.values())
            if(n.getDistanza(giocatore.posizione)<60)
                n.RicalcolaPercorso(giocatore.posizione);
    }
    boolean ControllaOstacoli(Giocatore giocatore, Nemico nemico) {
        Punto centroGiocatore=new Punto(giocatore.posizione.x*dimensioneCelle+dimensioneCelle/2,giocatore.posizione.y*dimensioneCelle+dimensioneCelle/2);
        Punto centroNemico=new Punto(nemico.posizione.x*dimensioneCelle+dimensioneCelle/2,nemico.posizione.y*dimensioneCelle+dimensioneCelle/2);
        double direzioneNemico=centroNemico.Direzione(centroGiocatore);
        double distanzaGiocatore=centroGiocatore.DistanzaDa(centroNemico);
        for(Punto o:ostacoli.keySet())
        {
            Punto centroOstacolo=new Punto(o.x*dimensioneCelle+dimensioneCelle/2,o.y*dimensioneCelle+dimensioneCelle/2);
            if(centroOstacolo.DistanzaDa(centroNemico)<distanzaGiocatore&& Math.abs(direzioneNemico-centroNemico.Direzione(centroOstacolo))<Math.PI/6)
                return true;
        }
        return false;
    }
}
