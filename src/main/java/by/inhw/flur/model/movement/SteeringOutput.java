package by.inhw.flur.model.movement;

public class SteeringOutput
{
    Point velocity;
    double rotation;

    public SteeringOutput()
    {
        this.velocity = new Point(0, 0, 0);
        this.rotation = 0;
    }

    public SteeringOutput(Point velocity, double rotation)
    {
        this.velocity = velocity.createCopy();
        this.rotation = rotation;
    }

    public void multiplySelf(double scalar)
    {
        velocity.multiplySelf(scalar);
        rotation *= scalar;
    }

    public void addToSelf(SteeringOutput steering)
    {
        if (steering.getVelocity() != null)
        {
            velocity.addToSelf(steering.getVelocity());
        }

        rotation += steering.getRotation();
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

    public double getRotation()
    {
        return rotation;
    }
}
