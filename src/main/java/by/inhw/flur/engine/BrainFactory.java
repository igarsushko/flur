package by.inhw.flur.engine;

import by.inhw.flur.engine.behave.Align;
import by.inhw.flur.engine.behave.Arrive;
import by.inhw.flur.engine.behave.Evade;
import by.inhw.flur.engine.behave.Flee;
import by.inhw.flur.engine.behave.Pursue;
import by.inhw.flur.engine.behave.Seek;
import by.inhw.flur.engine.behave.VelocityMatch;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class BrainFactory
{
    public static Brain seekAndAlign(final Agent persuader, final Agent target)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                SteeringOutput steering = Seek.getSteering(persuader, target);

                double rotation = Align.getAlign(persuader, target);
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

    public static Brain evadeAndAlign(final Agent agent, final Agent persuader)
    {
        Brain brain = new Brain()
        {
            public SteeringOutput nextMove()
            {
                double rotation = Align.getAlign(agent, persuader);

                SteeringOutput steering = Evade.getSteering(agent, persuader);
                steering.setRotation(rotation);

                return steering;
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
}
