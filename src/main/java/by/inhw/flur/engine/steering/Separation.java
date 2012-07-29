package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.util.VectorUtil;

public class Separation implements Steering
{
    private Agent agent;
    private Agent[] targets;

    // minimal distance between agent and targets
    private double threshold = 3;

    // Holds the constant coefficient of decay for the
    // inverse square law force
    // use positive for attraction
    private double decayCoefficient = -8;

    public Separation(Agent agent, Agent... targets)
    {
        this.agent = agent;
        this.targets = targets;
    }

    public SteeringOutput getSteering()
    {
        SteeringOutput steering = new SteeringOutput();
        for (Agent target : targets)
        {
            // Check if the target is close
            Point direction = VectorUtil.sub(target.getPosition(), agent.getPosition());
            double distance = direction.length();
            if (distance < threshold)
            {
                // Calculate the strength of repulsion
                double strength = Math.min(decayCoefficient / (distance * distance), agent.getMaxAcceleration());
                // alternative strength calculation
                // double strength = agent.getMaxAcceleration() * (threshold -
                // distance) / threshold;

                // Add the acceleration
                direction.normalize();
                direction.multiplySelf(strength);
                steering.getVelocity().addToSelf(direction);
            }
        }
        return steering;
    }
}
