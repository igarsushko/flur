package by.inhw.flur.model.movement;

public class SteeringOutput
{
    Point velocity;

    public SteeringOutput(Point velocity)
    {
        this.velocity = velocity.createCopy();
    }

    public Point getVelocity()
    {
        return velocity;
    }
}
