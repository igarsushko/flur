package by.inhw.flur.engine;

import by.inhw.flur.model.Agent;
import by.inhw.flur.util.VectorUtil;

public class Align
{
    public static double getAlign(Agent agent, Agent target)
    {
        double maxAngularAcceleration = 50;
        double maxRotation = 8;

        double targetRadius = 0;
        double slowRadius = 2;
        double timeToTarget = 0.01;

        double rotation = target.getOrientation() - agent.getOrientation();

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
