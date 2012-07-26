package by.inhw.flur.platform.swing.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.Timing;

public class ControllerListener extends KeyAdapter
{
    int state = IDLE;
    static final int IDLE = 0;
    static final int UP = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;
    Agent agent;
    Set<Integer> pressedKeys = Collections.synchronizedSet(new HashSet<Integer>());

    public ControllerListener()
    {
    }

    public ControllerListener(Agent agent)
    {
        this.agent = agent;
        runMovementListening();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        pressedKeys.remove(new Integer(e.getKeyCode()));
        if (pressedKeys.size() == 0)
        {
            state = IDLE;
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_F4)
        {
            System.exit(0);
        }
        else if (c == KeyEvent.VK_F1)
        {
            Timing.setPaused(false);
        }
        else if (c == KeyEvent.VK_F2)
        {
            Timing.setPaused(true);
        }
        else if (c == KeyEvent.VK_F5)
        {
            Debugger.toogleDrawPath();
        }
        else if (c == KeyEvent.VK_UP)
        {
            state = UP;
        }
        else if (c == KeyEvent.VK_DOWN)
        {
            state = DOWN;
        }
        else if (c == KeyEvent.VK_LEFT)
        {
            state = LEFT;
        }
        else if (c == KeyEvent.VK_RIGHT)
        {
            state = RIGHT;
        }
        pressedKeys.add(new Integer(c));
    }

    void runMovementListening()
    {
        new Thread()
        {
            public void run()
            {
                setName(agent.getName());
                while (true)
                {
                    Point velocity = new Point();
                    double orientation = 0;
                    if (state == UP)
                    {
                        velocity.setY(-1);
                        orientation = Math.toRadians(-180);
                    }
                    else if (state == DOWN)
                    {
                        velocity.setY(1);
                        orientation = Math.toRadians(0);
                    }
                    else if (state == LEFT)
                    {
                        velocity.setX(-1);
                        orientation = Math.toRadians(-90);
                    }
                    else if (state == RIGHT)
                    {
                        velocity.setX(1);
                        orientation = Math.toRadians(90);
                    }

                    if (state != IDLE)
                    {
                        Point currVelocity = agent.getKinematic().getVelocity();
                        currVelocity.set(velocity.multiply(agent.getMaxSpeed()));

                        agent.getKinematic().setOrientation(orientation);
                    }
                    else
                    {
                        agent.getKinematic().getVelocity().set(new Point());
                    }

                    try
                    {
                        Thread.sleep(Timing.FRAME_TIME_MILLIS);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }
}
