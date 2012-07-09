package by.inhw.flur.platform.swing.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.render.AgentRenderer;

public class AgentRendererImpl extends JPanel implements AgentRenderer
{
    Agent agent;
    int scale;
    World world;

    public AgentRendererImpl(Agent agent, int scale, World world)
    {
        this.agent = agent;
        this.scale = scale;
        this.world = world;
    }

    public void renderAgent()
    {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int x = getCoordinate(agent.x(), scale);
        int y = getCoordinate(agent.y(), scale);

        //Debugger.log(agent.getName() + ": X (in game wold): ", agent.getPosition().getX());
        //Debugger.log(agent.getName() + ": X (actual on screen): ", x);
        //Debugger.log(agent.getName() + ": Y (in game wold): ", agent.getPosition().getY());
        //Debugger.log(agent.getName() + ": Y (actual on screen): ", y);

        Point v = agent.getKinematic().getVelocity();
        DecimalFormat df = new DecimalFormat("#.###");
        String xF = df.format(v.getX());
        String yF = df.format(v.getY());
        String zF = df.format(v.getZ());

        Debugger.log(agent.getName() + " Velocity", "(" + xF + ", " + yF + ", " + zF + ")");

        setAgentColor(g);

        double rotation = agent.getOrientation();

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(-(rotation) - Math.toRadians(180), x, y);

        int upperLeftX = x - scale / 2;
        int upperLeftY = y - scale / 2;
        g.fillRect(upperLeftX, upperLeftY, scale, scale);
         drawPointer(upperLeftX, upperLeftY, g);
        drawCenterOfMass(x, y, g);
        //drawPosition(x, y, g);
    }

    void setAgentColor(Graphics g)
    {
        String colorStr = agent.getColor();
        if ("red".equalsIgnoreCase(colorStr))
        {
            g.setColor(Color.RED);
        }
        else if ("green".equalsIgnoreCase(colorStr))
        {
            g.setColor(Color.GREEN);
        }
        else if ("blue".equalsIgnoreCase(colorStr))
        {
            g.setColor(Color.BLUE);
        }
    }

    public static int getCoordinate(double value, int scale)
    {
        int valueInt = (int) value;
        double valueFraction = value - valueInt;

        int result = (int) (valueInt * scale + valueFraction * scale);

        return result;
    }

    void drawPointer(int x, int y, Graphics g)
    {
        Point p1 = new Point(x + scale / 2, y - scale);
        Point p2 = new Point(x + scale, y);
        Point p3 = new Point(x, y);

        g.drawLine(p1.xInt(), p1.yInt(), p2.xInt(), p2.yInt());
        g.drawLine(p2.xInt(), p2.yInt(), p3.xInt(), p3.yInt());
        g.drawLine(p3.xInt(), p3.yInt(), p1.xInt(), p1.yInt());
    }

    void drawCenterOfMass(int x, int y, Graphics g)
    {
        g.setColor(Color.ORANGE);
        g.drawLine(x, y, x, y);
    }

    private void drawPosition(int x, int y, Graphics g)
    {
        Point p = agent.getPosition();

        DecimalFormat df = new DecimalFormat("#.##");

        String xF = df.format(p.getX() - 2);
        String yF = df.format(-(p.getY() - 15));

        g.setColor(Color.ORANGE);
        g.drawString("(" + xF + " : " + yF + ")", (int) (x + scale / 1.5), (int) (y - scale / 1.5));
    }
}
