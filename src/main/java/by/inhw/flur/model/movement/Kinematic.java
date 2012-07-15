package by.inhw.flur.model.movement;

import by.inhw.flur.util.VectorUtil;

/**
 * Velocities are given in meters per second.
 */
public class Kinematic extends Static
{
    Point velocity;
    // angular acceleration, rad per sec
    double rotation;

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
        }
        // if steering has zero velocity, the some forces decreases existing
        // velocity
        else
        {
            velocity.devideSelf(1.25);
        }

        // orientation
        orientation += rotation * time;

        if (steering.rotation != 0)
        {
            rotation += steering.rotation * time;
        }
        else
        {
            rotation /= 1.25;
        }
    }

    public void kinematicUpdate(SteeringOutput steering, double time, double maxSpeed, double maxRotationSpeed)
    {
        position.addToSelf(velocity.multiply(time * maxSpeed));

        // get orientation from velocity
        if (steering.velocity.isNonZero())
        {
            double desiredOrientation = VectorUtil.getOrientationFromVelocity(steering.velocity);
            double diff = desiredOrientation - orientation;
            diff = VectorUtil.normalizeOrientation(diff);
            orientation += diff * maxRotationSpeed * time;
            orientation = VectorUtil.normalizeOrientation(orientation);
        }

        velocity.set(steering.velocity);
    }

    public Point getVelocity()
    {
        return velocity;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setVelocity(Point velocity)
    {
        if (this.velocity == null)
        {
            this.velocity = new Point();
        }

        this.velocity.set(velocity);
    }
}
