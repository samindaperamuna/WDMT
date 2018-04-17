/*
 * Upgraded to floating point accuracy on curves.
 */
package ui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.DeflectionRecord;

/**
 *
 * @author Saminda
 */
public class GraphPanel extends JPanel {

    private static final int GRAPH_WIDTH = 1150;
    private static final int GRAPH_HEIGHT = 800;
    private static final Color LINE_COLOUR = Color.BLACK;
    private static final Color CURVE_COLOR = Color.RED;
    private static final Font SANS_FONT_18 = new Font("Sans", Font.BOLD, 18);
    private static final Font SANS_FONT_12 = new Font("Sans", Font.BOLD, 12);
    private static final int GRAPH_UNIT_SIZE = 10;
    private static final int Y_START_POINT = 150;
    private static final int GRAPH_PADDING = 200;

    private final int noOfBearings;
    private final float verticalAlignment[];
    private final Stroke graphStrokeTDC;
    private final Stroke graphStrokeBDC;
    private final Stroke curveStroke;
    private final Point deflectionGraphAxis;
    private final JPopupMenu popupMenu;
    private final JMenuItem saveMenuItem;
    private final Map<Integer, DeflectionRecord> records;

    private BufferedImage iBeamImage;
    private int totalDeviation;

    /**
     * Default constructor with the record object from the Subject.
     *
     * @param records
     */
    public GraphPanel(Map<Integer, DeflectionRecord> records) {
        this.records = records;
        this.noOfBearings = records.values().size() + 1;
        this.verticalAlignment = new float[records.values().size()];
        this.totalDeviation = 0;
        this.popupMenu = new JPopupMenu();
        this.saveMenuItem = new JMenuItem("Save Image");
        this.deflectionGraphAxis = new Point(0, totalDeviation + Y_START_POINT + GRAPH_PADDING);
        this.graphStrokeTDC = new BasicStroke(1f);
        this.graphStrokeBDC = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        this.curveStroke = new BasicStroke(2.5f);

        initComponents();
    }

    private void initComponents() {
        MouseAdapter panelRightClicListener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.setVisible(true);
            }
        };

        ActionListener saveMenuItemClicked = (ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG image.", "png", "PNG"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                saveImage(fileChooser.getSelectedFile().getAbsolutePath());
            }
        };

        popupMenu.addMouseListener(panelRightClicListener);
        saveMenuItem.addActionListener(saveMenuItemClicked);
        popupMenu.add(saveMenuItem);
        this.setComponentPopupMenu(popupMenu);

        // Calculate vertical alignment for each bearing.
        for (int i = 0; i < records.size(); i++) {
            DeflectionRecord record = records.get(i + 1);
            float va = (record.getTop() - ((record.getNearBdcPort() + record.getNearBdcStbd()) / 2)) * GRAPH_UNIT_SIZE;
            totalDeviation += va;
            verticalAlignment[i] = -va;
        }

        try {
            this.iBeamImage = ImageIO.read(getClass().getResource("/resources/ibeam.gif"));
        } catch (IOException ex) {
            // TODO: Implement error handling.
            Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This is where all the graph plotting happens.
     *
     * @param g
     */
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(SANS_FONT_18);
        FontMetrics fm = g.getFontMetrics();

        double xScale = 8;
        double yScale = 20;

        List<Point.Float> graphPointsTDC = new ArrayList<>();
        List<Point.Float> graphPointsBDC = new ArrayList<>();
        List<Point.Float[]> intersectionWidths = new ArrayList<>();
        List<Point.Float> iBeamStartingPoints = new ArrayList<>();

        float xSegLength = GRAPH_WIDTH / (noOfBearings * 2);
        float ySegLength = xSegLength;

        float xStartPoint = xSegLength / 2;

        // Points for both graphs.
        for (int i = 0; i < noOfBearings - 1; i++) {
            // Set TDC Points.
            graphPointsTDC.add(new Point.Float(xStartPoint, Y_START_POINT));
            graphPointsTDC.add(new Point.Float(xStartPoint + (xSegLength / 2), Y_START_POINT));
            graphPointsTDC.add(new Point.Float(xStartPoint + (xSegLength / 2), Y_START_POINT - ySegLength));
            graphPointsTDC.add(new Point.Float(xStartPoint + (xSegLength * 2), Y_START_POINT - ySegLength));
            graphPointsTDC.add(new Point.Float(xStartPoint + (xSegLength * 2), Y_START_POINT));

            Point.Float[] points = new Point.Float[2];
            points[0] = new Point.Float(xStartPoint + (xSegLength * 3 / 2) - 10, Y_START_POINT - ySegLength);
            points[1] = new Point.Float(xStartPoint + (xSegLength * 3 / 2) - 10, deflectionGraphAxis.y);
            intersectionWidths.add(points);

            iBeamStartingPoints.add(new Point.Float(points[1].x - (xSegLength * 5 / 4) + 10, Y_START_POINT + ySegLength));

            // Set BDC Points.
            graphPointsBDC.add(new Point.Float(xStartPoint, Y_START_POINT));
            graphPointsBDC.add(new Point.Float(xStartPoint + (xSegLength / 2), Y_START_POINT));
            graphPointsBDC.add(new Point.Float(xStartPoint + (xSegLength / 2), Y_START_POINT + ySegLength));
            graphPointsBDC.add(new Point.Float(xStartPoint + (xSegLength * 2), Y_START_POINT + ySegLength));
            graphPointsBDC.add(new Point.Float(xStartPoint + (xSegLength * 2), Y_START_POINT));

            xStartPoint = xStartPoint + (xSegLength * 2);
        }

        graphPointsTDC.add(new Point.Float(xStartPoint + (xSegLength / 2), Y_START_POINT));
        Point.Float tempPoint = new Point.Float(xStartPoint + (xSegLength * 3 / 2) - 10, Y_START_POINT + ySegLength);
        iBeamStartingPoints.add(new Point.Float(tempPoint.x - (xSegLength * 5 / 4) + 10, tempPoint.y));

        // Drawing the graph BDC
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, GRAPH_WIDTH, GRAPH_HEIGHT + totalDeviation * GRAPH_UNIT_SIZE);
        g2.setColor(Color.BLACK);

        g2.setColor(LINE_COLOUR);
        g2.setStroke(graphStrokeTDC);

        // Draw graph TDC.
        for (int i = 0; i < graphPointsTDC.size() - 1; i++) {
            float x1 = graphPointsTDC.get(i).x;
            float y1 = graphPointsTDC.get(i).y;
            float x2 = graphPointsTDC.get(i + 1).x;
            float y2 = graphPointsTDC.get(i + 1).y;

            Line2D.Float line = new Line2D.Float(x1, y1, x2, y2);
            g2.draw(line);
        }

        g2.setStroke(graphStrokeBDC);

        // Draw graph BDC.
        for (int i = 0; i < graphPointsBDC.size() - 1; i++) {
            float x1 = graphPointsBDC.get(i).x;
            float y1 = graphPointsBDC.get(i).y;
            float x2 = graphPointsBDC.get(i + 1).x;
            float y2 = graphPointsBDC.get(i + 1).y;

            Line2D.Float line = new Line2D.Float(x1, y1, x2, y2);
            g2.draw(line);
        }

        // Draw the intersection points.
        xStartPoint = xSegLength * 3 / 2;

        for (int i = 0; i < intersectionWidths.size(); i++) {
            float x1 = intersectionWidths.get(i)[0].x;
            float y1 = intersectionWidths.get(i)[0].y;
            float x2 = intersectionWidths.get(i)[1].x;
            float y2 = intersectionWidths.get(i)[1].y;

            Line2D.Float line = new Line2D.Float(x1, y1, x2, y2);
            g2.draw(line);

            // Draw journal numbers.
            String text = String.valueOf(i + 1);
            g.setFont(SANS_FONT_18);
            g.drawString(text, Math.round(x1), Math.round(y1) - 10);

            // Draw vertical deflection values.
            DeflectionRecord record = this.records.get(i + 1);
            double v = record.getTop() * 1.0 - (record.getNearBdcStbd() + record.getNearBdcPort()) / 2.0;
            text = String.valueOf(v);
            g.setFont(SANS_FONT_12);
            g.drawString(text, Math.round(x1 + 10), Math.round(y1 + 60));

            xStartPoint = xStartPoint + ((xSegLength * 3) / 2);
        }

        // Draw the i-beam symbols.
        for (int i = 0; i < iBeamStartingPoints.size(); i++) {
            float x = iBeamStartingPoints.get(i).x;
            float y = iBeamStartingPoints.get(i).y;
            g2.drawImage(iBeamImage, Math.round(x), Math.round(y), this);
            String text = String.valueOf(i + 1);
            g.setFont(SANS_FONT_18);
            g.drawString(text, Math.round(x), Math.round(y) + 35);
        }

        // Draw the initial line of the curve.
        g2.setColor(CURVE_COLOR);
        g2.setStroke(curveStroke);
        Line2D initialLine = new Line2D.Float(xSegLength / 2, deflectionGraphAxis.y, intersectionWidths.get(0)[0].x, deflectionGraphAxis.y);
        g2.draw(initialLine);

        // Draw the initial label.
        DeflectionRecord record = this.records.get(1);
        double v = record.getTop() * 1.0 - (record.getNearBdcStbd() + record.getNearBdcPort()) / 2.0;
        String text = String.valueOf(v);
        g.setFont(SANS_FONT_12);
        g2.setColor(LINE_COLOUR);
        g2.setStroke(graphStrokeBDC);

        float y = deflectionGraphAxis.y + (verticalAlignment[0] / 2);
        g.drawString(text, Math.round(intersectionWidths.get(1)[0].x), Math.round(y));

        // Draw the inital base.
        g2.setColor(CURVE_COLOR);
        Line2D line = new Line2D.Float(deflectionGraphAxis.x, deflectionGraphAxis.y, intersectionWidths.get(1)[0].x, deflectionGraphAxis.y);
        g2.draw(line);

        // Draw the vertical deflection curve.
        float yCurveStart = deflectionGraphAxis.y;
        float yCurveEnd = yCurveStart + verticalAlignment[0];

        for (int i = 0; i < intersectionWidths.size() - 1; i++) {
            float x1 = intersectionWidths.get(i)[0].x;
            float y1 = yCurveStart;
            float x2 = intersectionWidths.get(i + 1)[0].x;
            float y2 = yCurveEnd;

            // Draw the solid part.
            g2.setColor(CURVE_COLOR);
            g2.setStroke(curveStroke);
            Line2D solidLine = new Line2D.Float(x1, y1, x2, y2);
            g2.draw(solidLine);

            // Draw the journal crosssection.
            g2.setColor(LINE_COLOUR);
            g2.setStroke(graphStrokeBDC);
            Line2D crossSectionTop = new Line2D.Float(x2, deflectionGraphAxis.y, x2, y2);
            g2.draw(crossSectionTop);

            // Shift the x-axis.
            if (i != intersectionWidths.size() - 2) {
                g2.setColor(CURVE_COLOR);
                Line2D extension;

                if (i != 0) {
                    extension = new Line2D.Float(x2, y2, intersectionWidths.get(i + 2)[0].x, yCurveEnd + verticalAlignment[i - 1] + verticalAlignment[i]);
                } else {
                    extension = new Line2D.Float(x2, y2, intersectionWidths.get(i + 2)[0].x, yCurveEnd + verticalAlignment[i]);
                }

                g2.draw(extension);

                g2.setColor(LINE_COLOUR);
                Line2D crossSectionBot;

                if (i != 0) {
                    crossSectionBot = new Line2D.Float(intersectionWidths.get(i + 2)[0].x, deflectionGraphAxis.y, intersectionWidths.get(i + 2)[0].x, yCurveEnd + verticalAlignment[i - 1] + verticalAlignment[i]);
                } else {
                    crossSectionBot = new Line2D.Float(intersectionWidths.get(i + 2)[0].x, deflectionGraphAxis.y, intersectionWidths.get(i + 2)[0].x, yCurveEnd + verticalAlignment[i]);
                }

                g2.draw(crossSectionBot);

                // Draw the labels.
                record = this.records.get(i + 2);
                v = record.getTop() * 1.0 - (record.getNearBdcStbd() + record.getNearBdcPort()) / 2.0;
                text = String.valueOf(v);
                g.setFont(SANS_FONT_12);

                if (i != 0) {
                    y = yCurveEnd + verticalAlignment[i - 1] + verticalAlignment[i] + (verticalAlignment[i + 1] / 2);
                } else {
                    y = yCurveEnd + verticalAlignment[i] + (verticalAlignment[i + 1] / 2);
                }

                g.drawString(text, Math.round(intersectionWidths.get(i + 2)[0].x), Math.round(y));
            }

            yCurveStart = yCurveEnd;

            if (i != 0) {
                yCurveEnd += verticalAlignment[i] + verticalAlignment[i + 1];
            } else {
                yCurveEnd += verticalAlignment[i] + verticalAlignment[i + 1];
            }
        }
    }

    /**
     * Image generation and writing.
     *
     * @param filePath
     */
    public void saveImage(String filePath) {
        Rectangle rectangle = new Rectangle(this.getLocation().x, this.getLocation().y, this.getWidth(), this.getHeight());

        int width = rectangle.width - 2;
        int height = rectangle.height - 2;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        g2.translate(-rectangle.x - 1, -rectangle.y - 1);
        this.paint(g2);
        g2.dispose();

        try {
            ImageIO.write(bufferedImage, "png", new File(filePath));
            JOptionPane.showMessageDialog(this, "Image saved sucessfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.out.println("Panel write help: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Couldn't save image.", "Error!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
