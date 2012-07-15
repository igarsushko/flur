package by.inhw.flur.engine;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

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

                return new SteeringOutput(velocity, 0);
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
                double rotation = Align.getAlign(persuader, target);

                double arriveRadius = 2;
                double slowRadius = 7;
                double timeToTarget = 0.1;

                double maxSpeed = persuader.getMaxSpeed();
                double finalSpeed = 0;

                Point direction = target.getPosition().substract(persuader.getPosition());

                double distance = direction.length();

                if (distance <= arriveRadius)
                {
                    return new SteeringOutput(new Point(), rotation);
                }
                else if (distance > arriveRadius && distance < slowRadius)
                {
                    finalSpeed = maxSpeed * distance / slowRadius;
                }
                else if (distance >= slowRadius)
                {
                    finalSpeed = maxSpeed;
                }

                Point finalVelocity = direction.createCopy();
                finalVelocity.normalize();
                finalVelocity.multiplySelf(finalSpeed);

                // Acceleration tries to get to the target velocity
                finalVelocity.substractFromSelf(persuader.getKinematic().getVelocity());
                finalVelocity.devideSelf(timeToTarget);

                double maxAccel = 30;
                // # Check if the acceleration is too fast
                if (finalVelocity.length() > maxAccel)
                {
                    finalVelocity.normalize();
                    finalVelocity.multiplySelf(maxAccel);
                }

                return new SteeringOutput(finalVelocity, rotation);
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
