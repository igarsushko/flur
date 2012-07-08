package by.inhw.flur.model;

import java.util.HashMap;
import java.util.Map;

import by.inhw.flur.render.WorldRenderer;

public class World extends WorldPart
{
    Map<String, Agent> agents = new HashMap<String, Agent>();

    private final int[][] map;
    private WorldRenderer worldRenderer;

    public int[][] getMap()
    {
        return map;
    }

    public World(int[][] map, WorldRenderer worldRenderer)
    {
        this.map = map;
        this.worldRenderer = worldRenderer;
        this.worldRenderer.setWorld(this);
    }

    public int getWidth()
    {
        return map[0].length;
    }

    public int getHeight()
    {
        return map.length;
    }

    public void renderWorld()
    {
        worldRenderer.renderWorld();
    }

    public Agent registerAgent(Agent agent)
    {
        agents.put(agent.getName(), agent);
        agent.setWorld(this);
        worldRenderer.registerAgentRenderer(agent);

        return agent;
    }

    /**
     * Make agent reappear on the other side of the map if he tries to go out of
     * the boundaries.
     */
    public void normalizeCoordinates2D(Agent agent)
    {
        if (agent.x() < 0.5)
        {
            agent.getKinematic().setX(this.getWidth() - 0.5);
        }
        else if (agent.x() > this.getWidth() - 0.5)
        {
            agent.getKinematic().setX(0.5);
        }

        if (agent.y() < 0.5)
        {
            agent.getKinematic().setY(this.getHeight() - 0.5);
        }
        else if (agent.y() > this.getHeight() - 0.5)
        {
            agent.getKinematic().setY(0.5);
        }
    }
}
