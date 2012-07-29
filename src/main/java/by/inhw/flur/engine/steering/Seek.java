package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Seek implements Steering
{
    protected Agent agent;
    protected Agent target;

    public Seek(Agent agent, Agent target)
    {
        this.agent = agent;
        this.target = target;
    }

    @Override
    public SteeringOutput getSteering()
    {
        Point targetPosition = target.getPosition();

        return Seek.getSteering(agent, targetPosition);
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
