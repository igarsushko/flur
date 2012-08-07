package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.sub;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class VelocityMatch implements Steering
{
    private Agent agent;
    private Agent target;

    double timeToTarget = 0.1;

    public VelocityMatch(Agent agent, Agent target)
    {
        this.agent = agent;
        this.target = target;
    }

    @Override
    public SteeringOutput getSteering()
    {
        return VelocityMatch.matchVelocity(agent, target.getVelocity(), timeToTarget);
    }

    public static SteeringOutput matchVelocity(Agent agent, Point targetVelocity, double timeToTarget)
    {
        double maxAcceleration = agent.getMaxAcceleration();

        // Acceleration tries to get to the target velocity
        Point velocity = sub(targetVelocity, agent.getVelocity());
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
