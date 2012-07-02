package by.inhw.flur.platform.swing.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

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

        int x = getCoordinate(agent.x());
        int y = getCoordinate(agent.y());

        // Debugger.log(agent.getName() + ": X real: ",
        // agent.getPosition().getX());
        // Debugger.log(agent.getName() + ": X actual: ", x);
        // Debugger.log(agent.getName() + ": Y real: ",
        // agent.getPosition().getY());
        // Debugger.log(agent.getName() + ": Y actual: ", y);

        double rotation = agent.getOrientation();

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(-(rotation) - Math.toRadians(180), x + scale / 2, y + scale / 2);
        g.fillRect(x, y, scale, scale);
        drawPointer(x, y, g);
    }

    int getCoordinate(double value)
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
}
