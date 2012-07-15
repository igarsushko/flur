package by.inhw.flur.platform.swing.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLayeredPane;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;
import by.inhw.flur.model.WorldPart;
import by.inhw.flur.render.AgentRenderer;
import by.inhw.flur.render.WorldRenderer;

public class WorldRendererImpl extends JLayeredPane implements WorldRenderer
{
    private World world;
    private int scale;
    private int worldWidth;
    private int worldHeight;
    private int[][] worldMap;
    private int actualWidth;
    private int actualHeight;

    public WorldRendererImpl(int scale)
    {
        this.scale = scale;
    }

    public void setWorld(World world)
    {
        this.world = world;
        this.worldWidth = world.getWidth();
        this.worldHeight = world.getHeight();
        this.worldMap = world.getMap();

        actualWidth = scale * worldWidth;
        actualHeight = scale * worldHeight;

        setPreferredSize(new Dimension(actualWidth, actualHeight));
        setBounds(0, 0, actualWidth, actualHeight);
    }

    public void renderWorld()
    {
        repaint();
    }

    public AgentRenderer registerAgentRenderer(Agent agent)
    {
        AgentRendererImpl renderer = new AgentRendererImpl(agent, scale, world);
        agent.setRenderer(renderer);

        // add agent on different layer
        add(renderer, JLayeredPane.MODAL_LAYER);

        renderer.setOpaque(false);
        renderer.setBounds(0, 0, actualWidth, actualHeight);

        return renderer;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (int y = 0; y < worldHeight; y++)
        {
            for (int x = 0; x < worldWidth; x++)
            {
                int value = worldMap[y][x];
                if (value == WorldPart.FREE_SPACE)
                {
                    g.setColor(Color.GRAY);
                }
                else if (value == WorldPart.OBSTACLE)
                {
                    g.setColor(Color.BLACK);
                }

                g.fillRoundRect(x * scale, y * scale, scale, scale, (int) scale / 15, (int) scale / 15);
            }
        }
    }

    public int getScale()
    {
        return scale;
    }
}
