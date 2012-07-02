package by.inhw.flur.engine;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;

public class BrainFactory
{
    public static Brain seek(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                Point velocity = target.getPosition().substract(persuader.getPosition());
                velocity.normalize();

                return new SteeringOutput(velocity);
            }
        };

        return brain;
    }

    public static Brain seekAndArrive(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double arriveRadius = 2;
                double slowDownFactor = 0.25;

                Point velocity = target.getPosition().substract(persuader.getPosition());

                double length = velocity.length();
                if (length < arriveRadius)
                {
                    return new SteeringOutput(new Point());
                }
                else
                {
                    velocity.normalize();
                }

                return new SteeringOutput(velocity);
            }
        };

        return brain;
    }

    public static Brain flee(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                Point velocity = agent.getPosition().substract(persuader.getPosition());
                velocity.normalize();

                return new SteeringOutput(velocity);
            }
        };

        return brain;
    }

    public static Brain moveRandomly()
    {
        Brain brain = new Brain()
        {
            int count = 0;
            int maxCount = 100;
            SteeringOutput steeringCached;

            public SteeringOutput nextMove()
            {
                SteeringOutput steering = null;
                if (count == 0 || count == maxCount)
                {
                    Point velocity = new Point();
                    double d = Math.random();
                    if (d > 0 && d <= 0.25)
                    {
                        velocity.setY(-1);
                    }
                    else if (d > 0.25 && d <= 0.5)
                    {
                        velocity.setY(1);
                    }
                    else if (d > 0.5 && d <= 0.75)
                    {
                        velocity.setX(1);
                    }
                    else if (d > 0.75 && d <= 1)
                    {
                        velocity.setX(-1);
                    }

                    steeringCached = new SteeringOutput(velocity);
                    steering = steeringCached;

                    if (count == 0)
                    {
                        count++;
                    }
                    else if (count == maxCount)
                    {
                        count = 0;
                    }
                }
                else if (count < maxCount)
                {
                    steering = steeringCached;
                    count++;
                }

                return steering;
            }
        };

        return brain;
    }
}
