package by.inhw.flur.model.movement;

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
