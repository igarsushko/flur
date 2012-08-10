package by.inhw.flur.model.movement;

import by.inhw.flur.model.World;

/**
 * Velocities are given in meters per second.
 */
public class Kinematic extends Static
{
    Point velocity;
    // angular acceleration, rad per sec
    double rotation;

    public Kinematic()
    {
        super(new Point(0, 0, 0), 0);
        velocity = new Point(0, 0, 0);
        rotation = 0;
    }

    public Kinematic(Point position, double orientation, Point velocity)
    {
        super(position, orientation);
        this.velocity = velocity.createCopy();
    }

    public void update(SteeringOutput steering, double time, double maxSpeed, double maxRotationSpeed)
    {
        // update position
        position.addToSelf(velocity.multiply(time));

        // update velocity
        if (steering.velocity.isNonZero())
        {
            velocity.addToSelf(steering.velocity.multiply(time));
            if (velocity.length() > maxSpeed)
            {
                velocity.normalize();
                velocity.multiplySelf(maxSpeed);
            }
        }
        // some forces decrease existing
        else
        {
            velocity.setX(velocity.getX() / 1.25);
            velocity.setX(velocity.getY() / 1.25);
        }

        // orientation
        orientation += rotation * time;

        if (steering.rotation != 0)
        {
            rotation += steering.rotation * time;
            if (rotation > maxRotationSpeed)
            {
                rotation = maxRotationSpeed;
            }
        }
        else
        {
            rotation /= 1.25;
        }
    }

    public Point getVelocity()
    {
        return velocity;
    }

    public double getRotation()
    {
        return rotation;
    }
}
