package by.inhw.flur.engine.steering.facing;

import by.inhw.flur.model.Agent;
import by.inhw.flur.util.VectorUtil;

/**
 * Returns angular acceleration to align facing with target.
 */
public class Align
{
    public static double getAlign(Agent agent, Agent target)
    {
        return Align.getAlign(agent, target.getOrientation());
    }

    public static double getAlign(Agent agent, double targetOrientation)
    {
        double maxRotation = agent.getMaxRotationSpeed();
        double maxAngularAcceleration = agent.getMaxAngularAcceleration();

        double targetRadius = 0;
        double slowRadius = 2;
        double timeToTarget = 0.01;

        double rotation = targetOrientation - agent.getOrientation();

        rotation = VectorUtil.normalizeOrientation(rotation);
        double rotationSize = Math.abs(rotation);

        double finalRotation = 0;
        if (rotationSize <= targetRadius)
        {
            return 0;
        }
        else if (rotationSize > slowRadius)
        {
            finalRotation = maxRotation;
        }
        else
        // calculate a scaled rotation
        {
            finalRotation = maxRotation * rotationSize / slowRadius;
        }

        finalRotation *= rotation / rotationSize;

        // Acceleration tries to get to the target rotation
        finalRotation -= agent.getRotation();
        finalRotation /= timeToTarget;

        // Check if the acceleration is too great
        double angularAcceleration = Math.abs(finalRotation);
        if (angularAcceleration > maxAngularAcceleration)
        {
            finalRotation /= angularAcceleration;
            finalRotation *= maxAngularAcceleration;
        }

        return finalRotation;
    }
}
