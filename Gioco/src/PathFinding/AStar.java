/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PathFinding;

import Record.Punto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author user
 */
public class AStar {

    static PriorityQueue<Nodo> closedList ;//da elaborare
    static PriorityQueue<Nodo> openList;//già elaborati
    static Nodo inizio, fine;

    public static Nodo findPath(Punto start, Punto target) {
        inizio=new Nodo(1,start.x,start.y);
        fine=new Nodo(1,target.x,target.y);
        inizio.f = inizio.g + inizio.calculateHeuristic(fine);
        closedList = new PriorityQueue<>();
        openList = new PriorityQueue<>();
        openList.add(inizio);

        while (!openList.isEmpty()) {
            Nodo n = openList.peek();
            if (n.equals(fine))
                return n;

            for (Nodo m : n.Vicini()) {
                double totalWeight = n.g + 1;

                if (!openList.contains(m) && !closedList.contains(m)) {
                    m.padre = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(fine);
                    openList.add(m);
                } else {
                    if (totalWeight < m.g) {
                        m.padre = n;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateHeuristic(fine);

                        if (closedList.contains(m)) {
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

    public static Stack<Punto> printPath(Nodo target) {
        Nodo n = target;
        if (n == null) {
            return null;
        }
        Stack<Punto> percorso = new Stack<>();
        while (n.padre != null) {
            percorso.push(n.p);
            n = n.padre;
        }
        percorso.push(n.p);
          if(!n.equals(inizio))
            return null;
        return percorso;
    }
}
