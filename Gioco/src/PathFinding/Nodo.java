/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PathFinding;

import Logica.Mappa;
import Record.Punto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Nodo  implements Comparable<Nodo>{
    public Nodo padre=null;
    public Punto p;
    public double f,g,h;
    public Nodo(double h,int x,int y){
        this.h = h;
        this.p=new Punto(x,y);
    }
    public Nodo(double h,Punto p){
        this.h = h;
        this.p=p;
    }
     public List<Nodo> Vicini(){
         List<Nodo> vicini=new ArrayList<>();
         for(int i=-1;i<=1;i++)
             for(int j=-1;j<=1;j++)
                if(Math.abs(i)!=Math.abs(j)&&!Mappa.Init().Controllacollisioni(new Punto(p.x+i,p.y+j)))
                    vicini.add(new Nodo(1,p.x+i,p.y+j));
         return vicini;
     }
     @Override
       public boolean equals(Object o){
           Nodo n=(Nodo)o;
           return p.equals(n.p);
       }
      @Override
      public int compareTo(Nodo n) {
            return Double.compare(this.f, n.f);
      }
      @Override
      public String toString(){
          return "("+p.x+","+p.y+")";
      }

      public double calculateHeuristic(Nodo target){
            return this.h;
      }
}
