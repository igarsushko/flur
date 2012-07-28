package by.inhw.flur.engine;

import by.inhw.flur.engine.behave.Align;
import by.inhw.flur.engine.behave.Arrive;
import by.inhw.flur.engine.behave.CollisionAvoidance;
import by.inhw.flur.engine.behave.CollisionDetector;
import by.inhw.flur.engine.behave.Evade;
import by.inhw.flur.engine.behave.Face;
import by.inhw.flur.engine.behave.Flee;
import by.inhw.flur.engine.behave.FollowPath;
import by.inhw.flur.engine.behave.LookWhereYoureGoing;
import by.inhw.flur.engine.behave.ObstacleAvoidance;
import by.inhw.flur.engine.behave.Path;
import by.inhw.flur.engine.behave.PredictiveFollowPath;
import by.inhw.flur.engine.behave.Pursue;
import by.inhw.flur.engine.behave.Seek;
import by.inhw.flur.engine.behave.Separation;
import by.inhw.flur.engine.behave.VelocityMatch;
import by.inhw.flur.engine.behave.Wander;
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

    public static Brain fleeAndAlign(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                SteeringOutput steering = Flee.getSteering(agent, persuader);

                double rotation = Align.getAlign(agent, persuader);
                steering.setRotation(rotation);

                return steering;
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

    public static Brain arriveAndAlign(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double rotation = Align.getAlign(persuader, target);

                SteeringOutput steering = Arrive.getSteering(persuader, target);
                steering.setRotation(rotation);

                return steering;
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

    public static Brain evadeAndLookWhereYoureGoing(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);

                SteeringOutput steering = Evade.getSteering(agent, persuader);
                steering.setRotation(rotation);

                return steering;
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
            public SteeringOutput nextMove()
            {
                SteeringOutput steering = CollisionAvoidance.getSteering(agent, targets);

                if (steering.getRotation() == 0)
                {
                    double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);
                    steering.setRotation(rotation);
                }

                return steering;
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
