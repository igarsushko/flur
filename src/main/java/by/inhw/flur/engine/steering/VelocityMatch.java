package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class VelocityMatch
{
    public static SteeringOutput getSteering(Agent agent, Agent target)
    {
        double maxAcceleration = agent.getMaxAcceleration();
        double timeToTarget = 0.1;

        // Acceleration tries to get to the target velocity
        Point velocity = target.getVelocity().substract(agent.getVelocity());
        velocity.devideSelf(timeToTarget);

        // Check if the acceleration is too fast
        if (velocity.length() > maxAcceleration)
        {
            velocity.normalize();
            velocity.multiplySelf(maxAcceleration);
        }

        return new SteeringOutput(velocity, 0);
    }
}
