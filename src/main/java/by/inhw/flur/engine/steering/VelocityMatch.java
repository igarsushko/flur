package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.sub;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class VelocityMatch implements Steering
{
    protected Agent agent;
    private Agent targetAgent;

    static double timeToTarget = 0.1;

    public VelocityMatch(Agent agent, Agent target)
    {
        this.agent = agent;
        this.targetAgent = target;
    }

    @Override
    public SteeringOutput getSteering()
    {
        return VelocityMatch.matchVelocity(agent, targetAgent.getVelocity());
    }

    public static SteeringOutput matchVelocity(Agent agent, Point targetVelocity)
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
