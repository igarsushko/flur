package by.inhw.flur.model.movement;

/**
 * Kinematic movements don't take into account aceleration. So as result we
 * usually have 2 kind of speed: zero, and max speed.
 */
public class SteeringOutput
{
    Point velocity;

    public SteeringOutput(Point velocity)
    {
        this.velocity = velocity.createCopy();
    }

    public void setVelocity(Point velocity)
    {
        this.velocity = velocity.createCopy();
    }

    public Point getVelocity()
    {
        return velocity;
    }
}
