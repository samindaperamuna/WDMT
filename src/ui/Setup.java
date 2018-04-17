/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import model.HODRank;
import model.Rank;
import data.DbConnect;
import ui.component.SubJPanel;
import model.Engine;
import model.Ship;
import ui.tablerenderer.DrawnHeaderRenderer;
import ui.tablerenderer.WonHeaderRenderer;
import model.MessageObserver;
import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import static ui.component.SubJPanel.sliderPanel;
import static ui.component.SubJPanel.shipDataContainer;
import static ui.component.SubJPanel.deflectionDataContainer;

public class Setup extends javax.swing.JInternalFrame implements MessageObserver {

    SubJPanel subpane = new SubJPanel();

    public Setup() {
        initComponents();

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(true);
        newButton.setEnabled(true);
        colorSetupTable();
        setSetupTableHeader();
    }

    private void updateTable() {
        try {
            setupTabel.setModel(DbConnect.getSetupTabelModel());

            colorSetupTable();
            setSetupTableHeader();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Exception Error", JOptionPane.INFORMATION_MESSAGE, Warningicon);
        }
    }

    private void fillShipNameCombo(List<Ship> shipList) {
        shipNameSerachComboBox.removeAllItems();

        for (Ship ship : shipList) {
            shipNameSerachComboBox.addItem(ship);
        }
    }

    private void colorSetupTable() {
        setupTabel.setShowGrid(true);
        setupTabel.setGridColor(Color.DARK_GRAY);
        setupTabel.getColumnModel().getColumn(0).setHeaderRenderer(new WonHeaderRenderer());
        setupTabel.getColumnModel().getColumn(1).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(2).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(3).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(4).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(5).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(6).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(7).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(8).setHeaderRenderer(new DrawnHeaderRenderer());
        setupTabel.getColumnModel().getColumn(9).setHeaderRenderer(new DrawnHeaderRenderer());
    }

    private void setSetupTableHeader() {
        setupTabel.getColumnModel().getColumn(0).setHeaderValue("<html><b>Ship<br>Name</b></html>");
        setupTabel.getColumnModel().getColumn(1).setHeaderValue("<html><b>Engine<br>Name</b></html>");
        setupTabel.getColumnModel().getColumn(2).setHeaderValue("<html><b>Engine<br>Type</b></html>");
        setupTabel.getColumnModel().getColumn(3).setHeaderValue("<html><b>Number Of<br>Cylinders</b></html>");
        setupTabel.getColumnModel().getColumn(4).setHeaderValue("<html><b>Number Of<br>Bearings</b></html>");
        setupTabel.getColumnModel().getColumn(5).setHeaderValue("<html><b>Firing<br>Order</b></html>");
        setupTabel.getColumnModel().getColumn(6).setHeaderValue("<html><b>Incharge<br>Name</b></html>");
        setupTabel.getColumnModel().getColumn(7).setHeaderValue("<html><b>Incharge<br>Rank</b></html>");
        setupTabel.getColumnModel().getColumn(8).setHeaderValue("<html><b>Worker<br>Name</b></html>");
        setupTabel.getColumnModel().getColumn(9).setHeaderValue("<html><b>Worker<br>Rank</b></html>");
    }

    public boolean isValidLicense() {
        try {
            if (shipNameTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Ship Name. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                shipNameTextBox.requestFocus();
                return false;

            }
            if (engineNameTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Engine Name. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                engineNameTextBox.requestFocus();
                return false;

            }
            if (engineTypeTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Engine Type. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                engineTypeTextBox.requestFocus();
                return false;

            }
            if (noOfCylTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Number of Cylinders. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                noOfCylTextBox.requestFocus();
                return false;

            }
            if (noOFBearingTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Number of Bearings. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                noOFBearingTextBox.requestFocus();
                return false;

            }
            if (firingOrderTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Firing Order. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                firingOrderTextBox.requestFocus();
                return false;

            }
            if (inchargeNameTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Incharge Name. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                noOFBearingTextBox.requestFocus();
                return false;

            }
            
            String getinchragerank = hodRankComboBox.getSelectedItem().toString();

            if (getinchragerank.trim().equals(model.HODRank.Select_Rank.toString())) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Select an Incharge Rank</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                hodRankComboBox.requestFocus();
                return false;

            }
            if (workerNameTextBox.getText().trim().equals("")) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Check the Worker Name. Textfiled Is Empty!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                workerNameTextBox.requestFocus();
                return false;

            }
            String getworkrank = rankComboBox.getSelectedItem().toString();

            if (getworkrank.trim().equals(model.Rank.Select_Rank.toString())) {
                String AlertString = "<html><h3 style='color: #8b0000'>Please Select Apropriate Tank Type Name!</h3></html>";
                JOptionPane.showMessageDialog(null, AlertString, "Alert Message", JOptionPane.INFORMATION_MESSAGE, Warningicon);
                rankComboBox.requestFocus();
                return false;

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Exception Occurred", JOptionPane.INFORMATION_MESSAGE, Warningicon);
            return false;
        }
        
        return true;
    }

    public void Clear_EntryFiled() {
        //ShipNameSerachCombo.setSelectedIndex(0);
        shipNameSerachComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Select Ship Name"}));
        shipNameTextBox.setText("");
        engineNameTextBox.setText("");
        engineTypeTextBox.setText("");
        noOfCylTextBox.setText("");
        noOFBearingTextBox.setText("");
        firingOrderTextBox.setText("");
        inchargeNameTextBox.setText("");
        //hodRankComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Ship Name" }));
        hodRankComboBox.setSelectedIndex(0);
        workerNameTextBox.setText("");
        rankComboBox.setSelectedIndex(0);
        //searchcombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Tank Name" }));

    }
    //--SAVE BUTTON-INSERT INTO DATABASE------------------------

    private void Insert_table() {
        String confirmString = "<html><h2 style='color: #8b008b '>Are You Want to Save The Data?</h2></html>";
        int reply = JOptionPane.showConfirmDialog(this, confirmString, "Warning Message", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, Areyousureicon);//.showConfirmDialog(null, confirmString, "Confirmation Dialog!", JOptionPane.YES_NO_OPTION, Areyousureicon);

        if (reply == JOptionPane.YES_OPTION) {
            try {
                String shipName = shipNameTextBox.getText();
                Ship ship = new Ship();
                ship.setShipName(shipName);
                DbConnect.insertShip(ship);

                Engine engineData = new Engine();
                engineData.setShipId(DbConnect.getShipId(shipName));
                engineData.setEngineName(engineNameTextBox.getText());
                engineData.setEngineType(engineTypeTextBox.getText());
                engineData.setNoOfCylinders(Integer.parseInt(noOfCylTextBox.getText()));
                engineData.setNoOfBearings(Integer.parseInt(noOFBearingTextBox.getText()));
                engineData.setFiringOrder(Integer.parseInt(firingOrderTextBox.getText()));
                engineData.setInchargeName(inchargeNameTextBox.getText());
                engineData.setInchargeRank(HODRank.valueOf(hodRankComboBox.getSelectedItem().toString()));
                engineData.setWorkerName(workerNameTextBox.getText());
                engineData.setWorkerRank(Rank.valueOf(rankComboBox.getSelectedItem().toString()));

                DbConnect.insertEngine(engineData);

                String updateString = "<html><h3 style='color: #008b00'>Tank Name Saved Successfully</h3></html>";
                JOptionPane.showMessageDialog(null, updateString, "Saved Message", JOptionPane.INFORMATION_MESSAGE, Successicon);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Exception Error", JOptionPane.INFORMATION_MESSAGE, Warningicon);
            }
        } else {
            String cancelString = "<html><h2 style='color: #006400'>Canceled Successfully!</h2></html>";
            JOptionPane.showMessageDialog(null, cancelString, "Warning Message", JOptionPane.INFORMATION_MESSAGE, Cancelicon);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formPanel = new javax.swing.JPanel();
        generalDataButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        editDeleteCheckBox = new javax.swing.JCheckBox();
        shipDetailsPanel = new javax.swing.JPanel();
        shipNameLabel = new javax.swing.JLabel();
        engineNameLabel = new javax.swing.JLabel();
        engineTypeLabel = new javax.swing.JLabel();
        noOfCylindersLabel = new javax.swing.JLabel();
        noOfBearingsLabel = new javax.swing.JLabel();
        firingOrderLabel = new javax.swing.JLabel();
        workerDetailsPanel = new javax.swing.JPanel();
        inchargeNameLabel = new javax.swing.JLabel();
        inchargeRankLabel = new javax.swing.JLabel();
        hodRankComboBox = new javax.swing.JComboBox();
        workerNameLabel = new javax.swing.JLabel();
        workerRankLabel = new javax.swing.JLabel();
        rankComboBox = new javax.swing.JComboBox();
        backgroundLabel = new javax.swing.JLabel();
        dataTablePanel = new javax.swing.JPanel();
        mainScrollPane = new javax.swing.JScrollPane();
        setupTabel = new javax.swing.JTable();
        dbManagementPanel = new javax.swing.JPanel();
        dbManagementLabel = new javax.swing.JLabel();
        generalDataLabel = new javax.swing.JButton();
        availableRecordsLabel = new javax.swing.JButton();
        sideDeskPanel = new javax.swing.JPanel();
        slideDeskPanel = new javax.swing.JDesktopPane();

        setTitle("SETUP PAGE");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        formPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        formPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        generalDataButton.setBackground(new java.awt.Color(122, 206, 224));
        generalDataButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        generalDataButton.setForeground(new java.awt.Color(153, 0, 51));
        generalDataButton.setText("GENERAL DATA");
        formPanel.add(generalDataButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 410, 30));

        newButton.setBackground(new java.awt.Color(153, 0, 0));
        newButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        newButton.setForeground(new java.awt.Color(255, 255, 255));
        newButton.setText("NEW");
        newButton.setToolTipText("<html><b><font color=red>"+ "Click Here To Add New Data Or Clear The Fields " + "</font></b></html>");
        newButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newButtonMouseExited(evt);
            }
        });
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        formPanel.add(newButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        saveButton.setBackground(new java.awt.Color(153, 0, 0));
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("SAVE");
        saveButton.setToolTipText("<html><b><font color=red>"+ "Click Here To Save New Data " + "</font></b></html>");
        saveButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButtonMouseEntered(evt);
            }
        });
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        formPanel.add(saveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        editButton.setBackground(new java.awt.Color(153, 0, 0));
        editButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setText("EDIT");
        editButton.setToolTipText("<html><b><font color=red>"+ "Click Here To Edit Exisiting Data " + "</font></b></html>");
        editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                editButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                editButtonMouseExited(evt);
            }
        });
        formPanel.add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        deleteButton.setBackground(new java.awt.Color(153, 0, 0));
        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("DELETE");
        deleteButton.setToolTipText("<html><b><font color=red>"+ "Click Here To Delete Data " + "</font></b></html>");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteButtonMouseExited(evt);
            }
        });
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        formPanel.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        editDeleteCheckBox.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        editDeleteCheckBox.setText("Edit/Delete");
        editDeleteCheckBox.setToolTipText("<html><b><font color=red>"+ "Check The Box For Enable Edit And Delete Button" + "</font></b></html>");
        editDeleteCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editDeleteCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDeleteCheckBoxActionPerformed(evt);
            }
        });
        formPanel.add(editDeleteCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 65, 100, -1));

        shipDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ship Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        shipDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shipNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shipNameLabel.setText("Ship Name:");
        shipDetailsPanel.add(shipNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        shipNameTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Your Ship's Name" + "</font></b></html>");
        shipDetailsPanel.add(shipNameTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 240, 30));

        engineNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        engineNameLabel.setText("Engine Name:");
        shipDetailsPanel.add(engineNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        engineNameTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Your Ship's Engine Name" + "</font></b></html>");
        shipDetailsPanel.add(engineNameTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 240, 30));

        engineTypeLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        engineTypeLabel.setText("Engine Type:");
        shipDetailsPanel.add(engineTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        engineTypeTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Your Ship's Engine Type" + "</font></b></html>");
        shipDetailsPanel.add(engineTypeTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 240, 30));

        noOfCylindersLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        noOfCylindersLabel.setText("No of Cylinders:");
        shipDetailsPanel.add(noOfCylindersLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 110, -1));

        noOfCylTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Engine Number Of Cylinder" + "</font></b></html>");
        shipDetailsPanel.add(noOfCylTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 240, 30));

        noOfBearingsLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        noOfBearingsLabel.setText("No of Bearings:");
        shipDetailsPanel.add(noOfBearingsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, 20));

        noOFBearingTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Number OF Bearings Have(Double Bearings Will Be Consider Single)" + "</font></b></html>");
        shipDetailsPanel.add(noOFBearingTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 240, 30));

        firingOrderLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        firingOrderLabel.setText("Firing Order:");
        shipDetailsPanel.add(firingOrderLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        firingOrderTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Engine Firing Order" + "</font></b></html>");
        shipDetailsPanel.add(firingOrderTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 240, 30));

        formPanel.add(shipDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 390, 290));

        workerDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Worker Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        workerDetailsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inchargeNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        inchargeNameLabel.setText("Incharge Name:");
        workerDetailsPanel.add(inchargeNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        inchargeNameTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Incharge Name" + "</font></b></html>");
        workerDetailsPanel.add(inchargeNameTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 240, 30));

        inchargeRankLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        inchargeRankLabel.setText("Incharge Rank:");
        workerDetailsPanel.add(inchargeRankLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        hodRankComboBox.setModel(new javax.swing.DefaultComboBoxModel(model.HODRank.values()));
        hodRankComboBox.setToolTipText("<html><b><font color=red>"+ "Please Select Rank Of The Person Who is Incharge For This Operation" + "</font></b></html>");
        hodRankComboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                hodRankComboBoxPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        workerDetailsPanel.add(hodRankComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 240, 30));

        workerNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        workerNameLabel.setText("Worker Name:");
        workerDetailsPanel.add(workerNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        workerNameTextBox.setToolTipText("<html><b><font color=red>"+ "Enter Worker Name" + "</font></b></html>");
        workerDetailsPanel.add(workerNameTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 240, 30));

        workerRankLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        workerRankLabel.setText("Worker Rank:");
        workerDetailsPanel.add(workerRankLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        rankComboBox.setModel(new javax.swing.DefaultComboBoxModel(model.Rank.values()));
        rankComboBox.setToolTipText("<html><b><font color=red>"+ "Please Select Rank Of The Person Who is Taken Readings" + "</font></b></html>");
        rankComboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                rankComboBoxPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        workerDetailsPanel.add(rankComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 240, 30));

        formPanel.add(workerDetailsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 390, 210));

        shipNameSerachComboBox.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shipNameSerachComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Ships Name" }));
        shipNameSerachComboBox.setToolTipText("<html><b><font color=red>"+ "Select A Ship Name To Edit Or Delete Data " + "</font></b></html>");
        shipNameSerachComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        formPanel.add(shipNameSerachComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 390, 30));

        backgroundLabel.setBackground(new java.awt.Color(204, 255, 255));
        formPanel.add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 410, 640));

        dataTablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        setupTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ));
        mainScrollPane.setViewportView(setupTabel);

        dataTablePanel.add(mainScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 200));

        dbManagementPanel.setBackground(new java.awt.Color(122, 206, 224));
        dbManagementPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dbManagementPanel.setToolTipText("");

        dbManagementLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dbManagementLabel.setForeground(new java.awt.Color(102, 0, 0));
        dbManagementLabel.setText(" Database Management");

        generalDataLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        generalDataLabel.setText("General Data");
        generalDataLabel.setToolTipText("");
        generalDataLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalDataLabelActionPerformed(evt);
            }
        });

        availableRecordsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        availableRecordsLabel.setText("Available Records");
        availableRecordsLabel.setToolTipText("");
        availableRecordsLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                availableRecordsLabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dbManagementPanelLayout = new javax.swing.GroupLayout(dbManagementPanel);
        dbManagementPanel.setLayout(dbManagementPanelLayout);
        dbManagementPanelLayout.setHorizontalGroup(
            dbManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dbManagementPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dbManagementLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addComponent(generalDataLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(availableRecordsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        dbManagementPanelLayout.setVerticalGroup(
            dbManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dbManagementPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(dbManagementPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generalDataLabel)
                    .addComponent(availableRecordsLabel)
                    .addComponent(dbManagementLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        dataTablePanel.add(dbManagementPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 780, 50));

        javax.swing.GroupLayout slideDeskPanelLayout = new javax.swing.GroupLayout(slideDeskPanel);
        slideDeskPanel.setLayout(slideDeskPanelLayout);
        slideDeskPanelLayout.setHorizontalGroup(
            slideDeskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        slideDeskPanelLayout.setVerticalGroup(
            slideDeskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sideDeskPanelLayout = new javax.swing.GroupLayout(sideDeskPanel);
        sideDeskPanel.setLayout(sideDeskPanelLayout);
        sideDeskPanelLayout.setHorizontalGroup(
            sideDeskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slideDeskPanel)
        );
        sideDeskPanelLayout.setVerticalGroup(
            sideDeskPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slideDeskPanel)
        );

        dataTablePanel.add(sideDeskPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 780, 410));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dataTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(dataTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newButtonMouseEntered
        newButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        newButton.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_newButtonMouseEntered

    private void newButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newButtonMouseExited
        newButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newButton.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_newButtonMouseExited

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        Clear_EntryFiled();
    }//GEN-LAST:event_newButtonActionPerformed

    private void saveButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseEntered
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        saveButton.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_saveButtonMouseEntered

    private void saveButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseExited
        saveButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_saveButtonMouseExited

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (evt.getSource() == saveButton) {
            if (isValidLicense()) {
                Insert_table();
                Clear_EntryFiled();
                
                triggerDataUpdate();
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void editButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editButtonMouseEntered
        editButton.setFont(new java.awt.Font("Tahoma", 1, 14));
        editButton.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_editButtonMouseEntered

    private void editButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editButtonMouseExited
        editButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_editButtonMouseExited

    private void deleteButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseEntered
        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 13));
        deleteButton.setForeground(new java.awt.Color(255, 235, 52));
    }//GEN-LAST:event_deleteButtonMouseEntered

    private void deleteButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseExited
        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_deleteButtonMouseExited

    private void editDeleteCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDeleteCheckBoxActionPerformed
        if (editDeleteCheckBox.isSelected()) {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            saveButton.setEnabled(false);
            newButton.setEnabled(false);

        } else {
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            saveButton.setEnabled(true);
            newButton.setEnabled(true);
        }
    }//GEN-LAST:event_editDeleteCheckBoxActionPerformed

    private void hodRankComboBoxPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_hodRankComboBoxPopupMenuWillBecomeVisible
        hodRankComboBox.removeAllItems();
        hodRankComboBox.setModel(new javax.swing.DefaultComboBoxModel(model.HODRank.values()));
    }//GEN-LAST:event_hodRankComboBoxPopupMenuWillBecomeVisible

    private void rankComboBoxPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_rankComboBoxPopupMenuWillBecomeVisible
        rankComboBox.removeAllItems();
        rankComboBox.setModel(new javax.swing.DefaultComboBoxModel(model.Rank.values()));
    }//GEN-LAST:event_rankComboBoxPopupMenuWillBecomeVisible

    private void generalDataLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalDataLabelActionPerformed
        sliderPanel.nextPanel(5, shipDataContainer, sliderPanel.right);
        sliderPanel.refresh();
    }//GEN-LAST:event_generalDataLabelActionPerformed

    private void availableRecordsLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_availableRecordsLabelActionPerformed
        sliderPanel.nextPanel(5, deflectionDataContainer, sliderPanel.left);
        sliderPanel.refresh();
    }//GEN-LAST:event_availableRecordsLabelActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        slideDeskPanel.removeAll();
        slideDeskPanel.repaint();
        subpane.setSize(slideDeskPanel.getWidth(), slideDeskPanel.getHeight());
        subpane.setLocation(0, 0);
        subpane.setVisible(true);
        slideDeskPanel.add(subpane);
        subpane.show();

        this.triggerDataUpdate();
    }//GEN-LAST:event_formComponentShown

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        String confirmString = "<html><h2 style='color: #8b008b '>Are You Want to Delete the record? All engine records will be deleted.</h2></html>";
        int reply = JOptionPane.showConfirmDialog(this, confirmString, "Warning Message", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, Areyousureicon);

        if (reply == JOptionPane.YES_OPTION) {
            int shipId = ((Ship) shipNameSerachComboBox.getSelectedItem()).getShipId();

            try {
                if (DbConnect.deleteEngines(shipId)) {
                    DbConnect.deleteShip(shipId);

                    triggerDataUpdate();

                    JOptionPane.showMessageDialog(this, "All records deleted !", "Success", JOptionPane.INFORMATION_MESSAGE, Successicon);
                } else {
                    JOptionPane.showMessageDialog(this, "Records cannot be deleted !", "Error", JOptionPane.ERROR_MESSAGE, Warningicon);
                }
            } catch (Exception ex) {
                Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton availableRecordsLabel;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JPanel dataTablePanel;
    private javax.swing.JLabel dbManagementLabel;
    private javax.swing.JPanel dbManagementPanel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JCheckBox editDeleteCheckBox;
    private javax.swing.JLabel engineNameLabel;
    public static final javax.swing.JTextField engineNameTextBox = new javax.swing.JTextField();
    private javax.swing.JLabel engineTypeLabel;
    public static final javax.swing.JTextField engineTypeTextBox = new javax.swing.JTextField();
    private javax.swing.JLabel firingOrderLabel;
    public static final javax.swing.JTextField firingOrderTextBox = new javax.swing.JTextField();
    private javax.swing.JPanel formPanel;
    private javax.swing.JButton generalDataButton;
    private javax.swing.JButton generalDataLabel;
    private javax.swing.JComboBox hodRankComboBox;
    private javax.swing.JLabel inchargeNameLabel;
    public static final javax.swing.JTextField inchargeNameTextBox = new javax.swing.JTextField();
    private javax.swing.JLabel inchargeRankLabel;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JButton newButton;
    public static final javax.swing.JTextField noOFBearingTextBox = new javax.swing.JTextField();
    private javax.swing.JLabel noOfBearingsLabel;
    public static final javax.swing.JTextField noOfCylTextBox = new javax.swing.JTextField();
    private javax.swing.JLabel noOfCylindersLabel;
    private javax.swing.JComboBox rankComboBox;
    private javax.swing.JButton saveButton;
    private javax.swing.JTable setupTabel;
    private javax.swing.JPanel shipDetailsPanel;
    private javax.swing.JLabel shipNameLabel;
    public static final javax.swing.JComboBox shipNameSerachComboBox = new javax.swing.JComboBox();
    public static final javax.swing.JTextField shipNameTextBox = new javax.swing.JTextField();
    private javax.swing.JPanel sideDeskPanel;
    private javax.swing.JDesktopPane slideDeskPanel;
    private javax.swing.JPanel workerDetailsPanel;
    private javax.swing.JLabel workerNameLabel;
    public static final javax.swing.JTextField workerNameTextBox = new javax.swing.JTextField();
    private javax.swing.JLabel workerRankLabel;
    // End of variables declaration//GEN-END:variables

    ImageIcon Cancelicon = new ImageIcon(getClass().getResource("/resources/cancel.png"));
    ImageIcon Successicon = new ImageIcon(getClass().getResource("/resources/success.png"));
    ImageIcon Areyousureicon = new ImageIcon(getClass().getResource("/resources/areyousure.png"));
    ImageIcon Warningicon = new ImageIcon(getClass().getResource("/resources/warning.png"));

    private void triggerDataUpdate() {
        JDesktopPane desktopPane = (JDesktopPane) this.getParent();
        Home home = (Home) desktopPane.getTopLevelAncestor();
        home.notifyObservers();
    }
    
    @Override
    public void update() {
        try {
            List<Ship> shipList = DbConnect.getShipList();
            fillShipNameCombo(shipList);
        } catch (Exception ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updateTable();
    }
}
