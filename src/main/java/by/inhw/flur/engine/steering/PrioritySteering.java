package by.inhw.flur.engine.steering;

import static java.lang.Math.abs;
import by.inhw.flur.model.movement.SteeringOutput;

public class PrioritySteering implements Steering
{
    // Holds a list of BlendedSteering instances
    private BlendedSteering[] groups;

    // Holds the epsilon parameter
    double epsilon = 0.000001;

    public PrioritySteering(BlendedSteering... groups)
    {
        this.groups = groups;
    }

    @Override
    public SteeringOutput getSteering()
    {
        SteeringOutput out = null;
        for (BlendedSteering group : groups)
        {
            out = group.getSteering();

            // Check if we’re above the threshold
            double velocityLength = out.getVelocity().length();
            if (velocityLength > epsilon || abs(out.getRotation()) > epsilon)
            {
                return out;
            }
        }

        // If we get here, it means that no group had a large
        // enough acceleration, so return the small
        // acceleration from the final group.
        return out;
    }
}
