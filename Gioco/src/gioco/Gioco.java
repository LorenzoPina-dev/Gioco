/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gioco;

import Logica.Mappa;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class Gioco  extends JFrame{
    public Gioco() {
        Mappa.Init();
        Board.Init();
        initUI();
        ThreadRender tr=new ThreadRender();
        tr.start();
        ThreadNemico tn=new ThreadNemico();
        tn.start();
    }
    
    private void initUI() {
        
        add(Board.Init());
               
        setResizable(false);
        pack();
        
        setTitle("Gioco");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Gioco();
            ex.setVisible(true);
        });
    }
    
}
