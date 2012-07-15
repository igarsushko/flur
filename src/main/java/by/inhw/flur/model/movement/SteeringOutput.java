package by.inhw.flur.model.movement;

public class SteeringOutput
{
    Point velocity;
    double rotation;

    public SteeringOutput(Point velocity, double rotation)
    {
        this.velocity = velocity.createCopy();
        this.rotation = rotation;
    }

    public void setVelocity(Point velocity)
    {
        this.velocity = velocity.createCopy();
    }

    public Point getVelocity()
    {
        return velocity;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }
}
