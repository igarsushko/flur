package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.sub;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Flee implements Steering
{
    protected Agent agent;
    protected Agent persuader;

    public Flee(Agent agent, Agent persuader)
    {
        this.agent = agent;
        this.persuader = persuader;
    }

    @Override
    public SteeringOutput getSteering()
    {
        Point persuaderPosition = persuader.getPosition();

        return getSteering(persuaderPosition);
    }

    protected SteeringOutput getSteering(Point persuaderPosition)
    {
        double maxAcceleration = agent.getMaxAcceleration();

        Point velocity = sub(agent.getPosition(), persuaderPosition);
        velocity.normalize();
        velocity.multiplySelf(maxAcceleration);

        return new SteeringOutput(velocity, 0);
    }
}
