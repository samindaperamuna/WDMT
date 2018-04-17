/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tablerenderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Saminda Permauna
 */
public class DisabledCellRenderer extends JLabel implements TableCellRenderer {

    public DisabledCellRenderer() {
        initRenderer();
    }
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
    
    private void initRenderer() {
        this.setBackground(Color.LIGHT_GRAY);
    }
}
