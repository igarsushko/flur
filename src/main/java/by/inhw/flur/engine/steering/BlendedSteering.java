package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.util.VectorUtil;

public class BlendedSteering implements Steering
{
    private WeightedSteering[] steerings;
    private Agent agent;

    public BlendedSteering(Agent agent, WeightedSteering... steerings)
    {
        this.agent = agent;
        this.steerings = steerings;
    }

    public static class WeightedSteering
    {
        private Steering steering;
        private double weight;

        public WeightedSteering(Steering steering, double weigth)
        {
            this.steering = steering;
            this.weight = weigth;
        }
    }

    public SteeringOutput getSteering()
    {
        double maxAcceleration = agent.getMaxAcceleration();
        double maxRotation = agent.getMaxAngularAcceleration();

        SteeringOutput steeringOut = new SteeringOutput();

        for (WeightedSteering behavior : steerings)
        {
            SteeringOutput currentSteering = behavior.steering.getSteering();
            currentSteering.multiplySelf(behavior.weight);

            steeringOut.addToSelf(currentSteering);
        }

        // clip to max velocity and rotation
        VectorUtil.clipVectorToMax(steeringOut.getVelocity(), maxAcceleration);

        if (steeringOut.getRotation() > maxRotation)
        {
            steeringOut.setRotation(maxRotation);
        }

        return steeringOut;
    }
}
