package by.inhw.flur.engine;

import by.inhw.flur.engine.steering.Arrive;
import by.inhw.flur.engine.steering.CollisionAvoidance;
import by.inhw.flur.engine.steering.Evade;
import by.inhw.flur.engine.steering.Flee;
import by.inhw.flur.engine.steering.FollowPath;
import by.inhw.flur.engine.steering.ObstacleAvoidance;
import by.inhw.flur.engine.steering.PredictiveFollowPath;
import by.inhw.flur.engine.steering.Pursue;
import by.inhw.flur.engine.steering.Seek;
import by.inhw.flur.engine.steering.Separation;
import by.inhw.flur.engine.steering.Steering;
import by.inhw.flur.engine.steering.VelocityMatch;
import by.inhw.flur.engine.steering.Wander;
import by.inhw.flur.engine.steering.facing.Align;
import by.inhw.flur.engine.steering.facing.Face;
import by.inhw.flur.engine.steering.facing.LookWhereYoureGoing;
import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class BrainFactory
{
    public static Brain seekAndLookWhereYoureGoing(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                SteeringOutput steering = Seek.getSteering(persuader, target);

                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(persuader);
                steering.setRotation(rotation);

                return steering;
            }
        };

        return brain;
    }

    public static Brain flee(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Flee(agent, persuader);

            public SteeringOutput nextMove()
            {
                SteeringOutput steeringOut = steering.getSteering();

                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);
                steeringOut.setRotation(rotation);

                return steeringOut;
            }
        };

        return brain;
    }

    public static Brain matchVelocityAndAlign(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                SteeringOutput steering = VelocityMatch.getSteering(persuader, target);

                double rotation = Align.getAlign(persuader, target);
                steering.setRotation(rotation);

                return steering;
            }
        };

        return brain;
    }

    public static Brain arrive(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Arrive(persuader, target);

            public SteeringOutput nextMove()
            {
                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(persuader);

                SteeringOutput steeringOut = steering.getSteering();
                steeringOut.setRotation(rotation);

                return steeringOut;
            }
        };

        return brain;
    }

    public static Brain pursueAndAlign(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double rotation = Align.getAlign(persuader, target);

                SteeringOutput steering = Pursue.getSteering(persuader, target);
                steering.setRotation(rotation);

                return steering;
            }
        };

        return brain;
    }

    public static Brain pursueAndFace(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double rotation = Face.getFacing(persuader, target);

                SteeringOutput steering = Pursue.getSteering(persuader, target);
                steering.setRotation(rotation);

                return steering;
            }
        };

        return brain;
    }

    public static Brain pursueAndLookWhereYoureGoing(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(persuader);

                SteeringOutput steering = Pursue.getSteering(persuader, target);
                steering.setRotation(rotation);

                return steering;
            }
        };

        return brain;
    }

    public static Brain evade(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Evade(agent, persuader);

            public SteeringOutput nextMove()
            {
                SteeringOutput steeringOut = steering.getSteering();

                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);
                steeringOut.setRotation(rotation);

                return steeringOut;
            }
        };

        return brain;
    }

    public static Brain wander(final Agent agent)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                return Wander.getSteering(agent);
            }
        };

        return brain;
    }

    public static Brain followPath(final Agent agent, final Path path)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                return FollowPath.getSteering(agent, path);
            }
        };

        return brain;
    }

    public static Brain predictiveFollowPath(final Agent agent, final Path path)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                return PredictiveFollowPath.getSteering(agent, path);
            }
        };

        return brain;
    }

    public static Brain puppetBrain(final Agent agent)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                Point currVelocity = agent.getKinematic().getVelocity();
                return new SteeringOutput(currVelocity, 0);
            }
        };

        return brain;
    }

    public static Brain separation(final Agent agent, final Agent... targets)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                return Separation.getSteering(agent, targets);
            }
        };

        return brain;
    }

    public static Brain collisionAvoidance(final Agent agent, final Agent... targets)
    {
        Brain brain = new Brain()
        {
            Steering steering = new CollisionAvoidance(agent, targets);

            public SteeringOutput nextMove()
            {
                SteeringOutput steeringOut = steering.getSteering();

                if (steeringOut.getRotation() == 0)
                {
                    double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);
                    steeringOut.setRotation(rotation);
                }

                return steeringOut;
            }
        };

        return brain;
    }

    public static Brain obstacleAvoidance(final Agent agent)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                SteeringOutput steering = ObstacleAvoidance.getSteering(agent);

                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);
                steering.setRotation(rotation);

                if (!steering.getVelocity().isNonZero())
                {
                    steering = Wander.getSteering(agent);
                }

                return steering;
            }
        };

        return brain;
    }
}
