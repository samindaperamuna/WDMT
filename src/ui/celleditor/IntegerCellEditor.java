/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.celleditor;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Saminda Permauna
 */
public class IntegerCellEditor extends DefaultCellEditor {

    private static final Border BORDER_RED = new LineBorder(Color.red);
    private static final Border BORDER_BLACK = new LineBorder(Color.black);
    private final JTextField textField;

    public IntegerCellEditor(JTextField textField) {
        super(textField);
        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.CENTER);
    }

    @Override
    public boolean stopCellEditing() {
        try {
            Integer.valueOf(textField.getText());
        } catch (NumberFormatException e) {
            textField.setBorder(BORDER_RED);
            return false;
        }

        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setBorder(BORDER_BLACK);
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
