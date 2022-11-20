package Record;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import gioco.*;

/**
 *
 * @author user
 */
public class Punto {
     public int x,y;
    public Punto(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Punto(){
        x=0;
        y=0;
    }
     @Override
       public boolean equals(Object o){
           Punto n=(Punto)o;
           return x==n.x&y==n.y;
       }
    public  double DistanzaDa(Punto secondo){
        return Math.sqrt(Math.pow(Math.abs(x-secondo.x),2)+ Math.pow(Math.abs(y-secondo.y),2));
    }
    public double Direzione(Punto secondo)
    {
        double distanza=DistanzaDa(secondo);
        double sin=(y-secondo.y)/distanza,cos=(x-secondo.x)/distanza*-1;
        double arcsin=Math.asin(sin);
        double arccos=Math.acos(cos);
        if(cos>=0)
            if(sin>=0)
                return arccos;//primo quadrante
            else
                return arcsin;
        else 
            if(sin>=0)
                return arccos;//secondo quadrante
            else
                return Math.PI*2-arccos;
    }
    public static Punto add(Punto primo,Punto secondo){
        Punto add=new Punto();
        add.x=primo.x+secondo.x;
        add.y=primo.y+secondo.y;
        return add;
    }
    public static Punto add(Punto primo,int distanza,double angolo){
        Punto add=new Punto();
        add.x=primo.x+(int)(Math.cos(angolo)*distanza);
        add.y=primo.y-(int)(Math.sin(angolo)*distanza);
        return add;
    }
    public static Punto reduce(Punto primo,Punto secondo){
        Punto add=new Punto();
        add.x=primo.x-secondo.x;
        add.y=primo.y-secondo.y;
        return add;
    }
    public static Punto reduce(Punto primo,int distanza,double angolo){
        Punto add=new Punto();
        add.x=primo.x-(int)(Math.cos(angolo)*distanza);
        add.y=primo.y-(int)(Math.sin(angolo)*distanza);
        return add;
    }
    
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}
