package by.inhw.flur.model;

import java.util.HashMap;
import java.util.Map;

import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.render.WorldRenderer;
import by.inhw.flur.util.Timing;

public class World extends WorldPart
{
    public static final double G = 9.81;
    public static final Point GRAVITY = new Point(0, 0, -G);

    Map<String, Agent> agents = new HashMap<String, Agent>();

    private final int[][] map;
    private WorldRenderer worldRenderer;
    private Timing timing;

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

    public void bringWorldToLive()
    {
        timing = new Timing();
        new Thread()
        {
            public void run()
            {
                setName("world");
                while (true)
                {
                    try
                    {
                        timing.update();
                        Debugger.log("FPS ", (int) timing.getFPS());
                        if (!Timing.isPaused())
                        {
                            for (Agent agent : agents.values())
                            {
                                SteeringOutput steering = agent.nextMove();
                                // applyForces(steering);
                                agent.updateKinematic(steering, Timing.FRAME_TIME_SEC);
                            }
                        }

                        Thread.sleep(Timing.FRAME_TIME_MILLIS);
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void applyForces(SteeringOutput steering)
    {
        Point velocity = steering.getVelocity();

        // friction force
        velocity.decreaze(0.75);
        steering.setVelocity(velocity);
    }

    public Agent registerAgent(Agent agent, Point position)
    {
        agents.put(agent.getName(), agent);
        agent.setWorld(this);
        agent.setPosition(position);
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
