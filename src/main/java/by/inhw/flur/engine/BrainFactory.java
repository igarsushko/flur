package by.inhw.flur.engine;

import by.inhw.flur.engine.steering.Arrive;
import by.inhw.flur.engine.steering.BlendedSteering;
import by.inhw.flur.engine.steering.BlendedSteering.WeightedSteering;
import by.inhw.flur.engine.steering.CollisionAvoidance;
import by.inhw.flur.engine.steering.Evade;
import by.inhw.flur.engine.steering.Flee;
import by.inhw.flur.engine.steering.FollowPath;
import by.inhw.flur.engine.steering.ObstacleAvoidance;
import by.inhw.flur.engine.steering.PredictiveFollowPath;
import by.inhw.flur.engine.steering.PrioritySteering;
import by.inhw.flur.engine.steering.Pursue;
import by.inhw.flur.engine.steering.Seek;
import by.inhw.flur.engine.steering.Separation;
import by.inhw.flur.engine.steering.Steering;
import by.inhw.flur.engine.steering.VelocityMatch;
import by.inhw.flur.engine.steering.Wander;
import by.inhw.flur.engine.steering.collision.CollisionDetector;
import by.inhw.flur.engine.steering.facing.LookWhereYoureGoing;
import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class BrainFactory
{
    public static Brain seek(final Agent agent, final Agent target)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Seek(agent, target);

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

    public static Brain velocityMatch(final Agent agent, final Agent target)
    {
        Brain brain = new Brain()
        {
            Steering steering = new VelocityMatch(agent, target);

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

    public static Brain arrive(final Agent agent, final Agent target)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Arrive(agent, target);

            public SteeringOutput nextMove()
            {
                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);

                SteeringOutput steeringOut = steering.getSteering();
                steeringOut.setRotation(rotation);

                return steeringOut;
            }
        };

        return brain;
    }

    public static Brain pursue(final Agent agent, final Agent target)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Pursue(agent, target);

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
            Steering steering = new Wander(agent);

            public SteeringOutput nextMove()
            {
                return steering.getSteering();
            }
        };

        return brain;
    }

    public static Brain followPath(final Agent agent, final Path path)
    {
        Brain brain = new Brain()
        {
            Steering steering = new FollowPath(agent, path);

            public SteeringOutput nextMove()
            {
                return steering.getSteering();
            }
        };

        return brain;
    }

    public static Brain predictiveFollowPath(final Agent agent, final Path path)
    {
        Brain brain = new Brain()
        {
            Steering steering = new PredictiveFollowPath(agent, path);

            public SteeringOutput nextMove()
            {
                return steering.getSteering();
            }
        };

        return brain;
    }

    public static Brain puppetBrain(final Agent agent, final Controller controller)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                // here normalized velocity comes
                Point requestedVelocity = controller.getVelocity().createCopy();
                requestedVelocity.multiplySelf(agent.getMaxSpeed());

                SteeringOutput steeringOut = VelocityMatch.matchVelocity(agent, requestedVelocity, 0.1);

                double rotation = LookWhereYoureGoing.getWhereYoureGoingFacing(agent);
                steeringOut.setRotation(rotation);

                return steeringOut;
            }
        };

        return brain;
    }

    public static Brain separation(final Agent agent, final Agent... targets)
    {
        Brain brain = new Brain()
        {
            Steering steering = new Separation(agent, targets);

            public SteeringOutput nextMove()
            {
                return steering.getSteering();
            }
        };

        return brain;
    }

    public static Brain collisionAvoidance(final Agent agent, final Agent... targets)
    {
        Brain brain = new Brain()
        {
            Steering collisionAvoidance = new CollisionAvoidance(agent, targets);
            Steering wander = new Wander(agent);
            Steering blendedSteering;

            {
                WeightedSteering w1 = new WeightedSteering(collisionAvoidance, 1);
                WeightedSteering w2 = new WeightedSteering(wander, 0.5);

                blendedSteering = new BlendedSteering(agent, w1, w2);
            }

            public SteeringOutput nextMove()
            {
                SteeringOutput steeringOut = blendedSteering.getSteering();

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

    public static Brain blendedObstacleAvoidanceAndWander(final Agent agent, final CollisionDetector collisionDetector)
    {
        Brain brain = new Brain()
        {
            Steering obstacleAvoidance = new ObstacleAvoidance(agent, collisionDetector);
            Steering wander = new Wander(agent);
            Steering blendedSteering;

            {
                WeightedSteering w1 = new WeightedSteering(obstacleAvoidance, 3);
                WeightedSteering w2 = new WeightedSteering(wander, 1);

                blendedSteering = new BlendedSteering(agent, w1, w2);

            }

            public SteeringOutput nextMove()
            {
                SteeringOutput steeringOut = blendedSteering.getSteering();

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

    public static Brain priorityObstacleAvoidanceAndWander(final Agent agent, final CollisionDetector collisionDetector)
    {
        Brain brain = new Brain()
        {
            Steering obstacleAvoidance = new ObstacleAvoidance(agent, collisionDetector);
            Steering wander = new Wander(agent);
            Steering prioritySteering;

            {
                WeightedSteering obstacleAvoidanceWeighted = new WeightedSteering(obstacleAvoidance, 1);
                WeightedSteering wanderWeighted = new WeightedSteering(wander, 1);

                BlendedSteering blend_0 = new BlendedSteering(agent, obstacleAvoidanceWeighted);
                BlendedSteering blend_1 = new BlendedSteering(agent, wanderWeighted);

                prioritySteering = new PrioritySteering(blend_0, blend_1);

            }

            public SteeringOutput nextMove()
            {
                SteeringOutput steeringOut = prioritySteering.getSteering();

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
}
