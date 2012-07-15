package by.inhw.flur.model.movement.kinematic;

import by.inhw.flur.model.movement.Point;

/**
 * Kinematic movements don't take into account aceleration. So as result we
 * usually have 2 kind of speed: zero, and max speed.
 */
public class KinematicSteeringOutput
{
    Point velocity;

    public KinematicSteeringOutput(Point velocity)
    {
        this.velocity = velocity.createCopy();
    }

    public Point getVelocity()
    {
        return velocity;
    }
}
