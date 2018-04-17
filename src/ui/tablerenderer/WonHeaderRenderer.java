/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tablerenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * A simple renderer class for JTable component.
 *
 * @author www.codejava.net
 *
 */
public class WonHeaderRenderer extends JLabel implements TableCellRenderer {

    public WonHeaderRenderer() {
        initializeComponent();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.setText(value.toString());
        this.setFont(table.getFont());

        return this;
    }

    /**
     * Initialise Component.
     */
    private void initializeComponent() {
        this.setFont(new Font("Consolas", Font.BOLD, 14));
        this.setOpaque(true);
        this.setForeground(Color.WHITE);

        Color lightBlue = new Color(0, 65, 105, 225);

        this.setBackground(lightBlue);
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setHorizontalAlignment(JLabel.CENTER);
    }
}
