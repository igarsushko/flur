package by.inhw.flur.platform.swing.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;
import by.inhw.flur.model.movement.Point;
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
        repaint();// calls paintComponent(Graphics g)
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        int x = getCoordinate(agent.x(), scale);
        int y = getCoordinate(agent.y(), scale);

        double rotation = agent.getOrientation();

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(-(rotation) - Math.toRadians(180), x, y);

        double z = agent.z();
        int jumpScale = scale;
        if (z > 0)
        {
            jumpScale = (int) (scale + agent.z() * 5);
        }

        int upperLeftX = x - jumpScale / 2;
        int upperLeftY = y - jumpScale / 2;

        // agent
        setAgentColor(g);
        g.fillRect(upperLeftX, upperLeftY, jumpScale, jumpScale);
        drawPointer(upperLeftX, upperLeftY, g, jumpScale);
        drawCenterOfMass(x, y, g);

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

    void drawPointer(int x, int y, Graphics g, int scale)
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
