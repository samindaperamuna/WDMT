/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import data.DbConnect;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.DateFormat;
import model.Engine;
import model.Ship;
import ui.tablerenderer.RowRenderer;
import model.MessageObserver;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.DeflectionHeader;
import model.DeflectionRecord;
import ui.celleditor.IntegerCellEditor;
import util.DataValidator;

public class EnterData extends javax.swing.JInternalFrame implements MessageObserver {

    private final ImageIcon cancelIcon = new ImageIcon(getClass().getResource("/resources/cancel.png"));
    private final ImageIcon successIcon = new ImageIcon(getClass().getResource("/resources/success.png"));
    private final ImageIcon areYouSureIcon = new ImageIcon(getClass().getResource("/resources/areyousure.png"));
    private final ImageIcon warningIcon = new ImageIcon(getClass().getResource("/resources/warning.png"));

    private long lastSelectedHeaderId;

    private static final String[] ROW_HEADERS = {
        "Near BDC, Stbd (X)",
        "Starboard (S)",
        "Top (T)",
        "Port (P)",
        "Near BDC, Port (Y)",
        "",
        "Bottom (X+Y)/2",
        "",
        "",
        "Top-Bottom (V)",
        "Starboard-Port (H)",
        "",
        "T+B (C)",
        "S+P (D)"
    };

    public EnterData() {
        initComponents();
    }

    private void updateComboBoxes(List<Ship> shipList) {
        shipNameComboBox.removeAllItems();

        for (Ship ship : shipList) {
            shipNameComboBox.addItem(ship);
        }
    }

    private void updateDataTable() {
        // final InputVerifier verifier = getInputVerifier();
        final DefaultCellEditor editor = new IntegerCellEditor(new JTextField());

        int noOfCylinders;
        Object selectedItem = shipNameComboBox.getSelectedItem();

        if (selectedItem != null) {
            try {
                int shipId = ((Ship) selectedItem).getShipId();

                Engine engine = DbConnect.getEngineList(shipId).get(0);
                noOfCylinders = engine.getNoOfCylinders();

                ListModel<Object> listModel = new AbstractListModel<Object>() {
                    @Override
                    public int getSize() {
                        return ROW_HEADERS.length;
                    }

                    @Override
                    public Object getElementAt(int index) {
                        return ROW_HEADERS[index];
                    }
                };

                Object[][] data = new Object[14][noOfCylinders];
                String[] columnNames = new String[noOfCylinders];

                for (int i = 0; i < noOfCylinders; i++) {
                    columnNames[i] = Integer.toString(i + 1);
                }

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

                dataTable.setModel(tableModel);
                dataTable.setDefaultEditor(Object.class, editor);
                dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                dataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        
                        if (row > 4) {
                            c.setBackground(row % 2 == 0 ? new Color(255, 232, 208) : Color.WHITE);
                            c.setForeground(Color.BLACK);
                        } else {
                            c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                            c.setForeground(Color.WHITE);
                        }
                        
                        return c;
                    }
                });

                dataTable.getModel().addTableModelListener((TableModelEvent e) -> {
                    updateCellsWithFormulae(e);
                });

                JList<Object> rowHeader = new JList<>(listModel);
                rowHeader.setFixedCellHeight(dataTable.getRowHeight());

                rowHeader.setCellRenderer(new RowRenderer(dataTable));
                dataTableScrollPane.setRowHeaderView(rowHeader);
            } catch (Exception ex) {
                Logger.getLogger(EnterData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method handles the formula enabled cells.
     *
     * @param e
     */
    private void updateCellsWithFormulae(TableModelEvent e) {
        TableModel dataModel = this.dataTable.getModel();
        int column = e.getColumn();
        int row = e.getLastRow();
        double value;

        try {
            switch (row) {
                case 0:
                case 4:
                    value = (Double.parseDouble((String) dataModel.getValueAt(0, column)) + Double.parseDouble((String) dataModel.getValueAt(4, column))) / 2;
                    dataModel.setValueAt(value, 6, column);
                    break;
                case 2:
                case 6:
                    value = (Double) dataModel.getValueAt(6, column) - Double.parseDouble((String) dataModel.getValueAt(2, column));
                    dataModel.setValueAt(value, 9, column);
                    value = (Double) dataModel.getValueAt(6, column) + Double.parseDouble((String) dataModel.getValueAt(2, column));
                    dataModel.setValueAt(value, 12, column);
                    break;
                case 1:
                case 3:
                    value = Double.parseDouble((String) dataModel.getValueAt(1, column)) - Double.parseDouble((String) dataModel.getValueAt(3, column));
                    dataModel.setValueAt(value, 10, column);
                    value = Double.parseDouble((String) dataModel.getValueAt(1, column)) + Double.parseDouble((String) dataModel.getValueAt(3, column));
                    dataModel.setValueAt(value, 13, column);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        shipNameLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        timeLabel = new javax.swing.JLabel();
        timeTextField = new javax.swing.JTextField();
        timeComboBox = new javax.swing.JComboBox();
        crankCaseTempLabel = new javax.swing.JLabel();
        databasePanel = new javax.swing.JPanel();
        openBtn = new javax.swing.JButton();
        buttonPanel = new javax.swing.JPanel();
        newBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        plotBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();
        checkErrorLabel = new javax.swing.JLabel();
        bottomErrorLabel = new javax.swing.JLabel();
        dataTableScrollPane = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable(){

            public boolean isCellEditable(int row, int column){
                if(row > 4) return false;
                return true;
            }

            ListModel lm=new AbstractListModel() {
                String[] Headers={"A","B","C","D"};

                @Override
                public Object getElementAt(int index) {
                    return Headers[index];
                }

                @Override
                public int getSize() {
                    return Headers.length;
                }

            };

        };

        setTitle("ENTER DATA");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        topPanel.setBackground(new java.awt.Color(122, 206, 224));
        topPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shipNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shipNameLabel.setText("Ship Name:");
        topPanel.add(shipNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        shipNameComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Ship Name" }));
        shipNameComboBox.setToolTipText("<html><b><font color=red>"+ "Select A Tank Name To Edit Or Delete Data " + "</font></b></html>");
        shipNameComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        shipNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shipNameComboBoxActionPerformed(evt);
            }
        });
        topPanel.add(shipNameComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 290, 30));

        dateLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dateLabel.setText("Date :");
        topPanel.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 80, -1));

        dateChooser.setDateFormatString("yyyy- MM-dd ");
        topPanel.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 130, -1));

        timeLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        timeLabel.setText("Time:");
        topPanel.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 50, -1));

        timeTextField.setToolTipText("<html><b><font color=red>"+ "Please Enter Ship's Tanks Sounded Time" + "</font></b></html>");
        topPanel.add(timeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 50, 30));

        timeComboBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        timeComboBox.setForeground(new java.awt.Color(0, 0, 153));
        timeComboBox.setModel(new javax.swing.DefaultComboBoxModel(model.Period.values()));
        topPanel.add(timeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 60, 30));

        crankCaseTempLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        crankCaseTempLabel.setText("Crankcase Temperature :");
        topPanel.add(crankCaseTempLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, -1, -1));

        crankCaseTempTextField.setToolTipText("<html><b><font color=red>"+ "Enter Your Ship's Tank Name" + "</font></b></html>");
        topPanel.add(crankCaseTempTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 70, 50, 30));

        databasePanel.setBackground(new java.awt.Color(255, 232, 208));
        databasePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        databasePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        openBtn.setBackground(new java.awt.Color(153, 0, 0));
        openBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        openBtn.setForeground(new java.awt.Color(255, 255, 255));
        openBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/documentopen.png"))); // NOI18N
        openBtn.setText("OPEN");
        openBtn.setToolTipText("<html><b><font color=red>"+ "Click Here To Add New Data Or Clear The Fields " + "</font></b></html>");
        openBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        openBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                openBtnMouseExited(evt);
            }
        });
        openBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBtnActionPerformed(evt);
            }
        });
        databasePanel.add(openBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        topPanel.add(databasePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 650, 60));

        buttonPanel.setBackground(new java.awt.Color(255, 232, 208));
        buttonPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        newBtn.setBackground(new java.awt.Color(153, 0, 0));
        newBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newBtn.setForeground(new java.awt.Color(255, 255, 255));
        newBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/documentnew.png"))); // NOI18N
        newBtn.setText("NEW");
        newBtn.setToolTipText("<html><b><font color=red>"+ "Click Here To Add New Data Or Clear The Fields " + "</font></b></html>");
        newBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newBtnMouseExited(evt);
            }
        });
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });
        buttonPanel.add(newBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        saveBtn.setBackground(new java.awt.Color(153, 0, 0));
        saveBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(255, 255, 255));
        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/documentsave.png"))); // NOI18N
        saveBtn.setText("SAVE");
        saveBtn.setToolTipText("<html><b><font color=red>"+ "Click Here To Save New Data " + "</font></b></html>");
        saveBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveBtnMouseExited(evt);
            }
        });
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        buttonPanel.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        plotBtn.setBackground(new java.awt.Color(153, 0, 0));
        plotBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        plotBtn.setForeground(new java.awt.Color(255, 255, 255));
        plotBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/plotcurves.png"))); // NOI18N
        plotBtn.setText("PLOT CURVES");
        plotBtn.setToolTipText("<html><b><font color=red>"+ "Click Here To Add New Data Or Clear The Fields " + "</font></b></html>");
        plotBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        plotBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                plotBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                plotBtnMouseExited(evt);
            }
        });
        plotBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotBtnActionPerformed(evt);
            }
        });
        buttonPanel.add(plotBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        printBtn.setBackground(new java.awt.Color(153, 0, 0));
        printBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        printBtn.setForeground(new java.awt.Color(255, 255, 255));
        printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/print.png"))); // NOI18N
        printBtn.setText("PRINT");
        printBtn.setToolTipText("<html><b><font color=red>"+ "Click Here To Add New Data Or Clear The Fields " + "</font></b></html>");
        printBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printBtn.setEnabled(false);
        printBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                printBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                printBtnMouseExited(evt);
            }
        });
        buttonPanel.add(printBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        topPanel.add(buttonPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 60));

        checkErrorLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkErrorLabel.setText("Check Error(C-D):");
        topPanel.add(checkErrorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 80, -1, -1));

        checkErrorTextField.setToolTipText("<html><b><font color=red>"+ "Enter Your Ship's Tank Name" + "</font></b></html>");
        topPanel.add(checkErrorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 70, 50, 30));

        bottomErrorLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bottomErrorLabel.setText("Bottom Error (B):");
        topPanel.add(bottomErrorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, -1));

        bottomErrorTextField.setToolTipText("<html><b><font color=red>"+ "Enter Your Ship's Tank Name" + "</font></b></html>");
        topPanel.add(bottomErrorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 70, 50, 30));

        dataTableScrollPane.setPreferredSize(new java.awt.Dimension(450, 400));

        dataTable.getTableHeader().setReorderingAllowed(false);
        dataTableScrollPane.setViewportView(dataTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dataTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void shipNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shipNameComboBoxActionPerformed
        updateDataTable();
    }//GEN-LAST:event_shipNameComboBoxActionPerformed

    private void saveBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveBtnMouseEntered
        saveBtn.setFont(new java.awt.Font("Tahoma", 1, 14));
        saveBtn.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_saveBtnMouseEntered

    private void saveBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveBtnMouseExited
        saveBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_saveBtnMouseExited

    private void plotBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotBtnMouseEntered
        plotBtn.setFont(new java.awt.Font("Tahoma", 1, 13));
        plotBtn.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_plotBtnMouseEntered

    private void plotBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotBtnMouseExited
        plotBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        plotBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_plotBtnMouseExited

    private void printBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printBtnMouseEntered
        printBtn.setFont(new java.awt.Font("Tahoma", 1, 13));
        printBtn.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_printBtnMouseEntered

    private void printBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printBtnMouseExited
        printBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        printBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_printBtnMouseExited

    private void newBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseExited
        newBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_newBtnMouseExited

    private void newBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseEntered
        newBtn.setFont(new java.awt.Font("Tahoma", 1, 13));
        newBtn.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_newBtnMouseEntered

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        triggerDataUpdate();
    }//GEN-LAST:event_formComponentShown

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Clear Data !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, areYouSureIcon);

        if (result == 0) {
            bottomErrorTextField.setText("");
            crankCaseTempTextField.setText("");
            checkErrorTextField.setText("");
            dateChooser.setDate(null);
            timeTextField.setText("");
            timeComboBox.setSelectedIndex(0);

            updateDataTable();
        }
    }//GEN-LAST:event_newBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        Object selectedItem = shipNameComboBox.getSelectedItem();

        if (selectedItem != null && validateFields()) {
            try {
                int shipId = ((Ship) selectedItem).getShipId();

                Engine engine = DbConnect.getEngineList(shipId).get(0);
                int engineId = engine.getEngineId();
                Date date = new Date(dateChooser.getDate().getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
                Time time = new Time(sdf.parse(timeTextField.getText() + timeComboBox.getSelectedItem().toString()).getTime());
                double crankCaseTemp = Double.parseDouble(crankCaseTempTextField.getText());
                double botErr = Double.parseDouble(bottomErrorTextField.getText());
                double checkErr = Double.parseDouble(checkErrorTextField.getText());

                DeflectionHeader header = new DeflectionHeader();
                header.setEngineId(engineId);
                header.setDate(date);
                header.setTime(time);
                header.setCracnkCaseTemp(crankCaseTemp);
                header.setBottomError(botErr);
                header.setCheckError(checkErr);

                DbConnect.insertDeflectionRecordsWithHeader(mapRecords(), header);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Saving Records !", JOptionPane.ERROR_MESSAGE, warningIcon);
            }

            JOptionPane.showMessageDialog(this, "All records saved successfully", "DB Tasks Completed !", JOptionPane.INFORMATION_MESSAGE, successIcon);
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    /**
     * Map form elements to a sorted DefelctionRecord collection.
     *
     * @return
     */
    private Map<Integer, DeflectionRecord> mapRecords() {
        Map<Integer, DeflectionRecord> recordList = new TreeMap<>();
        int colCount = dataTable.getModel().getColumnCount();

        for (int col = 0; col < colCount; col++) {
            DeflectionRecord record = new DeflectionRecord();
            record.setHeaderId(lastSelectedHeaderId);
            record.setCylinderId(col + 1);
            record.setNearBdcStbd(Integer.parseInt((String) dataTable.getModel().getValueAt(0, col)));
            record.setStarboard(Integer.parseInt((String) dataTable.getModel().getValueAt(1, col)));
            record.setTop(Integer.parseInt((String) dataTable.getModel().getValueAt(2, col)));
            record.setPort(Integer.parseInt((String) dataTable.getModel().getValueAt(3, col)));
            record.setNearBdcPort(Integer.parseInt((String) dataTable.getModel().getValueAt(4, col)));

            recordList.put(record.getCylinderId(), record);
        }

        return recordList;
    }

    /**
     * Function to validate the form fields.
     *
     * @return
     */
    private boolean validateFields() {
        final String validationHeader = "Please correct the following error!";

        // Validate the header.
        if (!DataValidator.isDouble(bottomErrorTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Bottom error must be a number.", validationHeader, JOptionPane.INFORMATION_MESSAGE);
            bottomErrorTextField.requestFocus();
            return false;
        } else if (!DataValidator.isDouble(crankCaseTempTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Crankcase temperature must be a number.", validationHeader, JOptionPane.INFORMATION_MESSAGE);
            crankCaseTempTextField.requestFocus();
            return false;
        } else if (!DataValidator.isDouble(checkErrorTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Check error must be a number.", validationHeader, JOptionPane.INFORMATION_MESSAGE);
            checkErrorTextField.requestFocus();
            return false;
        } else if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date.", validationHeader, JOptionPane.INFORMATION_MESSAGE);
            dateChooser.requestFocus();
            return false;
        } else if (!DataValidator.isValidTime(timeTextField.getText() + timeComboBox.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid time.", validationHeader, JOptionPane.INFORMATION_MESSAGE);
            timeTextField.requestFocus();
            return false;
        }

        // Validate the records.
        return true;
    }

    public void setLastSelectedHeaderId(long lastSelectedHeaderId) {
        this.lastSelectedHeaderId = lastSelectedHeaderId;
    }

    private void openBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openBtnMouseExited
        openBtn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        openBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_openBtnMouseExited

    private void openBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openBtnMouseEntered
        openBtn.setFont(new java.awt.Font("Tahoma", 1, 13));
        openBtn.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_openBtnMouseEntered

    private void openBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBtnActionPerformed
        Object selectedItem = shipNameComboBox.getSelectedItem();

        if (selectedItem != null) {
            try {
                int shipId = ((Ship) selectedItem).getShipId();
                Engine engine = DbConnect.getEngineList(shipId).get(0);

                JDialog dialog = new OpenRecords(engine, this);
                dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                dialog.setVisible(true);

                // Edit mode load stuff
                DeflectionHeader header = DbConnect.getDeflectionHeader(this.lastSelectedHeaderId);

                bottomErrorTextField.setText(String.valueOf(header.getBottomError()));
                crankCaseTempTextField.setText(String.valueOf(header.getCracnkCaseTemp()));
                checkErrorTextField.setText(String.valueOf(header.getCheckError()));
                dateChooser.setDate(header.getDate());

                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                timeTextField.setText(dateFormat.format(header.getTime()));

                Map<Integer, DeflectionRecord> records = DbConnect.getRecords(lastSelectedHeaderId);
                TableModel model = dataTable.getModel();

                for (DeflectionRecord record : records.values()) {
                    int col = record.getCylinderId() - 1;
                    model.setValueAt(String.valueOf(record.getNearBdcStbd()), 0, col);
                    model.setValueAt(String.valueOf(record.getStarboard()), 1, col);
                    model.setValueAt(String.valueOf(record.getTop()), 2, col);
                    model.setValueAt(String.valueOf(record.getPort()), 3, col);
                    model.setValueAt(String.valueOf(record.getNearBdcPort()), 4, col);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error loading records !", JOptionPane.ERROR_MESSAGE, cancelIcon);
            }
        }
    }//GEN-LAST:event_openBtnActionPerformed

    private void plotBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotBtnActionPerformed
        if (validateFields()) {
            triggerDataUpdate();
        }
    }//GEN-LAST:event_plotBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bottomErrorLabel;
    public static final javax.swing.JTextField bottomErrorTextField = new javax.swing.JTextField();
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel checkErrorLabel;
    public static final javax.swing.JTextField checkErrorTextField = new javax.swing.JTextField();
    private javax.swing.JLabel crankCaseTempLabel;
    public static final javax.swing.JTextField crankCaseTempTextField = new javax.swing.JTextField();
    private javax.swing.JTable dataTable;
    private javax.swing.JScrollPane dataTableScrollPane;
    private javax.swing.JPanel databasePanel;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton newBtn;
    private javax.swing.JButton openBtn;
    private javax.swing.JButton plotBtn;
    private javax.swing.JButton printBtn;
    private javax.swing.JButton saveBtn;
    public static final javax.swing.JComboBox shipNameComboBox = new javax.swing.JComboBox();
    private javax.swing.JLabel shipNameLabel;
    private javax.swing.JComboBox timeComboBox;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JTextField timeTextField;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    private void triggerDataUpdate() {
        JDesktopPane desktopPane = (JDesktopPane) this.getParent();
        Home home = (Home) desktopPane.getTopLevelAncestor();
        home.setRecords(mapRecords());
        home.notifyObservers();
    }

    @Override
    public void update() {
        try {
            List<Ship> shipList = DbConnect.getShipList();
            updateComboBoxes(shipList);
        } catch (Exception ex) {
            Logger.getLogger(EnterData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
