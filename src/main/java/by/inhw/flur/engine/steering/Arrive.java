package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Arrive
{
    public static SteeringOutput getSteering(Agent agent, Agent target)
    {
        return Arrive.getSteering(agent, target.getPosition());
    }

    public static SteeringOutput getSteering(Agent agent, Point targetPosition)
    {
        double arriveRadius = 2;
        double slowRadius = 7;
        double timeToTarget = 0.1;

        double maxSpeed = agent.getMaxSpeed();
        double finalSpeed = 0;

        Point direction = targetPosition.substract(agent.getPosition());

        double distance = direction.length();
        if (distance <= arriveRadius)
        {
            return new SteeringOutput(new Point(), 0);
        }
        else if (distance > arriveRadius && distance < slowRadius)
        {
            finalSpeed = maxSpeed * distance / slowRadius;
        }
        else if (distance >= slowRadius)
        {
            finalSpeed = maxSpeed;
        }

        Point finalVelocity = direction;
        finalVelocity.normalize();
        finalVelocity.multiplySelf(finalSpeed);

        // Acceleration tries to get to the target velocity
        finalVelocity.substractFromSelf(agent.getKinematic().getVelocity());
        finalVelocity.devideSelf(timeToTarget);

        // Check if the acceleration is too fast
        double maxAccel = agent.getMaxAcceleration();
        if (finalVelocity.length() > maxAccel)
        {
            finalVelocity.normalize();
            finalVelocity.multiplySelf(maxAccel);
        }

        return new SteeringOutput(finalVelocity, 0);
    }
}
