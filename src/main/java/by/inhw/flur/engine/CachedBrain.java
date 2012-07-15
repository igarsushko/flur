package by.inhw.flur.engine;

import by.inhw.flur.model.movement.SteeringOutput;

/**
 * Used to repeat next move defined number of times. Implemented to get smooth
 * behavior in random moving and wandering.
 * 
 */
public abstract class CachedBrain implements Brain
{
    int count = 0;
    int maxCount;
    SteeringOutput steeringCached;

    public abstract SteeringOutput doNextMove();

    public CachedBrain(int repeatFrames)
    {
        this.maxCount = repeatFrames;
    }

    public SteeringOutput nextMove()
    {
        SteeringOutput steering = null;
        if (count == 0 || count == maxCount)
        {
            steeringCached = doNextMove();
            steering = steeringCached;

            if (count == 0)
            {
                count++;
            }
            else if (count == maxCount)
            {
                count = 0;
            }
        }
        else if (count < maxCount)
        {
            steering = steeringCached;
            count++;
        }

        return steering;
    }

}
