package by.inhw.flur.platform.swing.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
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

    public ControllerListener(Agent agent)
    {
        this.agent = agent;
    }

    {
        runMovementListening();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        state = IDLE;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_F4)
        {
            System.exit(0);
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
    }

    void runMovementListening()
    {
        new Thread()
        {
            public void run()
            {
                while (true)
                {
                    Point velocity = new Point();
                    if (state == UP)
                    {
                        velocity.setY(-1);
                    }
                    else if (state == DOWN)
                    {
                        velocity.setY(1);
                    }
                    else if (state == LEFT)
                    {
                        velocity.setX(-1);
                    }
                    else if (state == RIGHT)
                    {
                        velocity.setX(1);
                    }

                    SteeringOutput steering = null;
                    if (state != IDLE)
                    {
                        steering = new SteeringOutput(velocity);
                        agent.updateKinematic(steering, Timing.FRAME_TIME_SEC);
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
