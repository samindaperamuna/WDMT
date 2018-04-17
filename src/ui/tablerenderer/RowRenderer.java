package ui.tablerenderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

/**
 * Row Renderer class which is required to display row headers.
 *
 * @author Saminda Permauna
 */
public class RowRenderer extends JLabel implements ListCellRenderer<Object> {

    public RowRenderer(JTable table) {
        this.initRenderer(table);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        setText((value == null) ? "" : value.toString());
        return this;
    }

    /**
     * Initialise the default properties.
     * 
     * @param table 
     */
    private void initRenderer(JTable table) {
        JTableHeader header = table.getTableHeader();
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
    }
}
