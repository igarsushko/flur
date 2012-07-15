package by.inhw.flur.engine.behave;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Seek
{
    public static SteeringOutput getSteering(Agent agent, Agent target)
    {
        return getSteering(agent, target.getPosition());
    }

    public static SteeringOutput getSteering(Agent agent, Point targetPosition)
    {
        double maxAcceleration = agent.getMaxAcceleration();

        Point velocity = targetPosition.substract(agent.getPosition());
        velocity.normalize();
        velocity.multiplySelf(maxAcceleration);

        return new SteeringOutput(velocity, 0);
    }
}
