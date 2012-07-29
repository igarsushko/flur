package by.inhw.flur.platform.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import by.inhw.flur.engine.steering.path.LinePath;
import by.inhw.flur.model.movement.Line;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.render.AgentRendererImpl;
import by.inhw.flur.platform.swing.render.WorldRendererImpl;
import by.inhw.flur.util.CpuProfiler;

public class Debugger
{
    private static JFrame frame;
    private static WorldRendererImpl worldRenderer;
    private static JPanel loggingPanel;
    private static JPanel debugLayer;
    private static boolean on = true;
    private static boolean doDrawPath = true;
    private static List<LinePath> paths = new ArrayList<LinePath>();
    private static Map<String, Line> vectors = new HashMap<String, Line>();
    private static Map<String, Point> points = new HashMap<String, Point>();

    // in milliseconds
    private static int statInterval = 1000;

    private static DecimalFormat cpuLoadFormat = new DecimalFormat("#.##");
    private static DecimalFormat memUsedFormat = new DecimalFormat("#.##");
    private static DecimalFormat decimalFormat = new DecimalFormat("#.############");

    private static Map<String, JLabel> labels = Collections.synchronizedMap(new HashMap<String, JLabel>());

    public static void on()
    {
        on = true;
    }

    public static void off()
    {
        on = false;
    }

    public static void toogleDrawPath()
    {
        doDrawPath = !doDrawPath;
    }

    public static void setWorldRenderer(WorldRendererImpl renderer)
    {
        Debugger.worldRenderer = renderer;

        debugLayer = getDebugDrawLayer();
        debugLayer.setOpaque(false);
        debugLayer.setBounds(0, 0, renderer.getWidth(), renderer.getHeight());
        renderer.add(debugLayer, JLayeredPane.POPUP_LAYER);
        debugLayer.repaint();
    }

    public static void drawPath(LinePath path)
    {
        paths.add(path);
    }

    public static void setFrame(JFrame jframe)
    {
        Debugger.frame = jframe;
        loggingPanel = new JPanel();
        loggingPanel.setLayout(new BoxLayout(loggingPanel, BoxLayout.Y_AXIS));
        loggingPanel.setBounds(5, 5, 200, 20);
        loggingPanel.setOpaque(false);
        frame.add(loggingPanel);

        logCpuUsage();
        logMemoryUsage();
    }

    static synchronized JLabel addLabelToFrame(String name, JLabel label)
    {
        label = new JLabel();
        labels.put(name, label);

        int count = labels.size();
        loggingPanel.setBounds(5, 5, 1000, 20 * count);
        loggingPanel.add(label);
        loggingPanel.repaint();
        return label;
    }

    static void D(Object o)
    {
        System.out.println(Thread.currentThread().getName() + ": " + o);
    }

    public static void log(String name, Object value)
    {
        if (on)
        {
            JLabel label = labels.get(name);
            if (label == null)
            {
                label = addLabelToFrame(name, label);
            }

            String formattedValue = format(value);
            label.setText(name + formattedValue);
        }
    }

    public static void logVector(String id, Line vector)
    {
        vectors.put(id, vector);
    }
    
    public static void unlogVector(String id)
    {
        vectors.remove(id);
    }


    public static void logPoint(String id, Point point)
    {
        points.put(id, point);
    }

    public static void unlogPoint(String id)
    {
        points.remove(id);
    }

    private static String format(Object value)
    {
        String result = null;

        if (value instanceof Double)
        {
            Double d = (Double) value;
            result = decimalFormat.format(d);
        }
        else if (value instanceof Point)
        {
            Point p = (Point) value;
            result = "( " + decimalFormat.format(p.getX()) + ": " + decimalFormat.format(p.getY()) + " : "
                    + decimalFormat.format(p.getZ()) + " )";
        }
        else
        {
            result = value.toString();
        }

        return result;
    }

    private static void logCpuUsage()
    {
        CpuProfiler profiler = new CpuProfiler(statInterval);
        profiler.setUpdateHandler(new CpuProfiler.UpdateHandler()
        {
            public void onUpdate(double cpuLoad)
            {
                String cpuLoadFormatted = cpuLoadFormat.format(cpuLoad);
                Debugger.log("CPU ", cpuLoadFormatted + "%");
            }
        });
        profiler.start();
    }

    private static void logMemoryUsage()
    {
        new Thread()
        {
            public void run()
            {
                this.setName("Memory monitor");

                while (true)
                {
                    long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    double usedKb = (double) used / 1024;
                    String useKbFormatted = memUsedFormat.format(usedKb);

                    Debugger.log("Memory ", useKbFormatted + "Kb");

                    try
                    {
                        Thread.sleep(statInterval);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    private static JPanel getDebugDrawLayer()
    {
        return new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                Graphics2D g2d = (Graphics2D) g;

                // paintGlobalDebugInfo(g2d, worldRenderer.getScale());

                // draw path
                if (doDrawPath && paths.size() > 0)
                {
                    for (LinePath path : paths)
                    {
                        for (LinePath.Line line : path.getLines())
                        {
                            drawVector(line.getBegin(), line.getEnd(), g2d, false);
                        }
                    }
                }

                // draw vectors
                if (vectors.size() > 0)
                {
                    for (Line line : vectors.values())
                    {
                        drawVector(line.getBegin(), line.getEnd(), g2d, false);
                    }
                }

                // draw points
                if (points.size() > 0)
                {
                    for (Point point : points.values())
                    {
                        drawPoint(point, g2d);
                    }
                }
            }
        };
    }

    private static void paintGlobalDebugInfo(Graphics2D g, final int scale)
    {
        g.setColor(Color.ORANGE);

        // x
        int startX = (int) (scale / 2);
        int startY = (int) (scale / 2);
        int endX = (int) (scale * 5);
        int endY = startY;
        g.drawLine(startX, startY, endX, endY);
        g.drawString("X", endX + scale / 3, endY);
        // arrow
        g.drawLine(endX, endY, endX - scale / 5, endY - scale / 10);
        g.drawLine(endX, endY, endX - scale / 5, endY + scale / 10);

        // y
        startX = (int) (scale / 2);
        startY = (int) (scale / 2);
        endX = startX;
        endY = (int) (scale * 5);
        g.drawLine(startX, startY, endX, endY);
        g.drawString("Y", endX, endY + scale / 2);
        // arrow
        g.drawLine(endX, endY, endX - scale / 10, endY - scale / 5);
        g.drawLine(endX, endY, endX + scale / 10, endY - scale / 5);
    }

    private static void drawPoint(Point p, Graphics2D g)
    {
        g.setColor(Color.ORANGE);

        int scale = worldRenderer.getScale();

        int x = AgentRendererImpl.getCoordinate(p.getX(), scale);
        int y = AgentRendererImpl.getCoordinate(p.getY(), scale);

        int hScale = scale / 2;
        g.drawOval(x, y, 1, 1);
        g.drawOval(x - hScale, y - hScale, scale, scale);
    }

    private static void drawVector(Point from, Point to, Graphics2D g, boolean doCaption)
    {
        g.setColor(Color.ORANGE);

        int scale = worldRenderer.getScale();

        int startX = AgentRendererImpl.getCoordinate(from.getX(), scale);
        int startY = AgentRendererImpl.getCoordinate(from.getY(), scale);
        int endX = AgentRendererImpl.getCoordinate(to.getX(), scale);
        int endY = AgentRendererImpl.getCoordinate(to.getY(), scale);

        // vector
        g.drawLine(startX, startY, endX, endY);

        int middleX = startX + ((endX - startX) / 2);
        middleX += middleX / 25;
        int middleY = startY + ((endY - startY) / 2);
        // middleY += middleY / 10;

        if (doCaption)
        {
            // text
            g.drawString("(" + (int) (to.getX() - from.getX()) + " : " + (int) -(to.getY() - from.getY()) + ")",
                    middleX, middleY);
        }

        // arrow
        AffineTransform tx = new AffineTransform();
        Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);

        Polygon arrowHead = new Polygon();
        int size = worldRenderer.getScale() / 8;
        arrowHead.addPoint(0, size);
        arrowHead.addPoint(-size, -size);
        arrowHead.addPoint(size, -size);

        tx.setToIdentity();
        double angle = Math.atan2(line.y2 - line.y1, line.x2 - line.x1);
        tx.translate(line.x2, line.y2);
        tx.rotate((angle - Math.PI / 2d));

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setTransform(tx);
        g2d.fill(arrowHead);
        g2d.dispose();
    }

}