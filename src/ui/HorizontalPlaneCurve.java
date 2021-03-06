/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;
import model.DeflectionRecord;
import model.MessageObserver;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import util.Calc;

public class HorizontalPlaneCurve extends javax.swing.JInternalFrame implements MessageObserver {

    private final JFreeChart chart;
    private final Map<Integer, DeflectionRecord> records;

    public HorizontalPlaneCurve(Map<Integer, DeflectionRecord> records) {
        this.records = records;
        this.chart = drawChart();
        initComponents();
    }

    private JFreeChart drawChart() {
        XYSeries horizontalDeflection = new XYSeries("Horizontal Deflection");

        // TODO: Implement empty data set handling on ReadingCheck and Curve interfaces.
        if (records.isEmpty()) {
            this.dispose();
        }

        for (int i = 1; i <= records.size(); i++) {
            DeflectionRecord record = records.get(i);
            horizontalDeflection.add(i, Calc.getV(record.getTop(), record.getNearBdcPort(), record.getNearBdcStbd()));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(horizontalDeflection);
        JFreeChart xyLineChart = ChartFactory.createXYLineChart("Horizontal Deflection", "Journal Number", "Top - Bottom", dataset);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        xyLineChart.getXYPlot().setRenderer(renderer);
        
        xyLineChart.getXYPlot().getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return xyLineChart;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        deflectionCurvePanel = new ChartPanel(chart);

        setTitle("HORIZONTAL PLANE GRAPH");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(1200, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setBackground(new java.awt.Color(122, 206, 224));
        mainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(102, 0, 0));
        titleLabel.setText("Deflection in Horizontal Plane");
        mainPanel.add(titleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 280, 30));

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 40));
        getContentPane().add(deflectionCurvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 1150, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel deflectionCurvePanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update() {
        initComponents();
    }
}
