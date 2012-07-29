package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Flee
{
    public static SteeringOutput getSteering(Agent agent, Agent persuader)
    {
        Point persuaderPosition = persuader.getPosition();

        return getSteering(agent, persuaderPosition);
    }

    public static SteeringOutput getSteering(Agent agent, Point persuaderPosition)
    {
        double maxAcceleration = agent.getMaxAcceleration();

        Point velocity = agent.getPosition().substract(persuaderPosition);
        velocity.normalize();
        velocity.multiplySelf(maxAcceleration);

        return new SteeringOutput(velocity, 0);
    }
}
