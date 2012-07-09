package by.inhw.flur.engine;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.KinematicSteeringOutput;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.Rand;
import by.inhw.flur.util.VectorUtil;

public class BrainFactory
{
    public static Brain kinematikSeek(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public KinematicSteeringOutput nextMove()
            {
                Point velocity = target.getPosition().substract(persuader.getPosition());
                velocity.normalize();

                return new KinematicSteeringOutput(velocity);
            }
        };

        return brain;
    }

    public static Brain kinematicSeekAndArrive(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public KinematicSteeringOutput nextMove()
            {
                double arriveRadius = 2;

                Point velocity = target.getPosition().substract(persuader.getPosition());

                double length = velocity.length();
                if (length < arriveRadius)
                {
                    return new KinematicSteeringOutput(new Point());
                }
                else
                {
                    velocity.normalize();
                    return new KinematicSteeringOutput(velocity);
                }
            }
        };

        return brain;
    }

    public static Brain kinematicFlee(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            public KinematicSteeringOutput nextMove()
            {
                Point velocity = agent.getPosition().substract(persuader.getPosition());
                velocity.normalize();

                return new KinematicSteeringOutput(velocity);
            }
        };

        return brain;
    }

    public static Brain kinematicWander(final Agent agent)
    {
        Brain brain = new Brain()
        {
            int count = 0;
            int maxCount = 20;
            KinematicSteeringOutput steeringCached;

            public KinematicSteeringOutput nextMove()
            {
                KinematicSteeringOutput steering = null;
                if (count == 0 || count == maxCount)
                {
                    double orientation = agent.getKinematic().getOrientation();
                    double newOrientation = (orientation + Rand.randomBinomial());

                    Point velocity = VectorUtil.orientation2dAsVector(newOrientation);
                    velocity.normalize();

                    steeringCached = new KinematicSteeringOutput(velocity);
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

    public static Brain kinematicMoveRandomly()
    {
        Brain brain = new Brain()
        {
            int count = 0;
            int maxCount = 100;
            KinematicSteeringOutput steeringCached;

            public KinematicSteeringOutput nextMove()
            {
                KinematicSteeringOutput steering = null;
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

                    steeringCached = new KinematicSteeringOutput(velocity);
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
