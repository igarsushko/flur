package by.inhw.flur.engine;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.util.Timing;

public class Mover
{
    public static void startActing(final Agent agent)
    {
        new Thread()
        {
            public void run()
            {
                while (true)
                {
                    try
                    {
                        if (!Timing.isPaused())
                        {
                            SteeringOutput steering = agent.nextMove();

                            agent.updateKinematic(steering, Timing.FRAME_TIME_SEC);
                            Thread.sleep(Timing.FRAME_TIME_MILLIS);
                        }
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
