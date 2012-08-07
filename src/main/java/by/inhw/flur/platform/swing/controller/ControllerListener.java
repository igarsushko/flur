package by.inhw.flur.platform.swing.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import by.inhw.flur.engine.Controller;
import by.inhw.flur.engine.steering.ObstacleAvoidance;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.Timing;

public class ControllerListener extends KeyAdapter implements Controller
{
    boolean isIdle = false;
    boolean isUp = false;
    boolean isDown = false;
    boolean isLeft = false;
    boolean isRight = false;
    Set<Integer> pressedKeys = Collections.synchronizedSet(new HashSet<Integer>());
    private Point currentVelocity = new Point();

    public ControllerListener()
    {
        runMovementListening();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int c = e.getKeyCode();
        pressedKeys.remove(c);

        double curr = 0;
        if (c == KeyEvent.VK_UP)
        {
            curr = currentVelocity.getY();
            if (curr != 0)
            {
                isUp = false;
                currentVelocity.setY(curr + 1);
            }
        }
        else if (c == KeyEvent.VK_DOWN)
        {

            curr = currentVelocity.getY();
            if (curr != 0)
            {
                isDown = false;
                currentVelocity.setY(curr - 1);
            }
        }
        else if (c == KeyEvent.VK_LEFT)
        {
            curr = currentVelocity.getX();
            if (curr != 0)
            {
                isLeft = false;
                currentVelocity.setX(curr + 1);
            }
        }
        else if (c == KeyEvent.VK_RIGHT)
        {
            curr = currentVelocity.getX();
            if (curr != 0)
            {
                isRight = false;
                currentVelocity.setX(curr - 1);
            }
        }

        // so double pressed/released key don't glitch
        if (pressedKeys.size() == 0)
        {
            isIdle = true;
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
            ObstacleAvoidance.debug = !ObstacleAvoidance.debug;
        }
        else if (c == KeyEvent.VK_F6)
        {
            Debugger.toogleDrawPath();
        }
        else if (c == KeyEvent.VK_UP)
        {
            isUp = true;
            isIdle = false;
        }
        else if (c == KeyEvent.VK_DOWN)
        {
            isDown = true;
            isIdle = false;
        }
        else if (c == KeyEvent.VK_LEFT)
        {
            isLeft = true;
            isIdle = false;
        }
        else if (c == KeyEvent.VK_RIGHT)
        {
            isRight = true;
            isIdle = false;
        }
        pressedKeys.add(new Integer(c));
    }

    void runMovementListening()
    {
        new Thread()
        {
            public void run()
            {
                setName("Controller listener");
                while (true)
                {
                    if (isIdle)
                    {
                        currentVelocity.setX(0);
                        currentVelocity.setY(0);
                        currentVelocity.setZ(0);
                    }
                    else
                    {
                        if (isUp)
                        {
                            currentVelocity.setY(-1);
                        }

                        if (isDown)
                        {
                            currentVelocity.setY(1);
                        }

                        if (isLeft)
                        {
                            currentVelocity.setX(-1);
                        }

                        if (isRight)
                        {
                            currentVelocity.setX(1);
                        }
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

    @Override
    public Point getVelocity()
    {
        Debugger.log("controller velocity: ", currentVelocity);

        return currentVelocity;
    }
}
