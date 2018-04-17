/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import data.DbConnect;
import java.awt.BorderLayout;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import model.DeflectionRecord;
import ui.component.GraphPanel;

/**
 *
 * @author Saminda Permauna
 */
public class GraphTester {
    private static Map<Integer, DeflectionRecord> records;
    
    public static void main(String[] s) {
        try {
            records = DbConnect.getRecords(9);
        } catch (Exception ex) {
            Logger.getLogger(GraphTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GraphPanel testGraph = new GraphPanel(records);
        testGraph.setSize(800, 600);

        JFrame testFrame = new JFrame("Graph Test");
        testFrame.setSize(1000, 600);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setLayout(new BorderLayout());
        testFrame.getContentPane().add(testGraph, BorderLayout.CENTER);
        testFrame.setVisible(true); 
    }
}

