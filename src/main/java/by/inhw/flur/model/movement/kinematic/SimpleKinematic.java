package by.inhw.flur.model.movement.kinematic;

import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.Static;
import by.inhw.flur.util.VectorUtil;

/**
 * Velocities are given in meters per second. Kinematic for simple cases without
 * accelerations.
 */
public class SimpleKinematic extends Static
{
    Point velocity;

    public SimpleKinematic(Point position, double orientation, Point velocity)
    {
        super(position, orientation);
        this.velocity = velocity.createCopy();
    }

    public void update(KinematicSteeringOutput steering, double time, double maxSpeed, double maxRotationSpeed)
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

    public void setVelocity(Point velocity)
    {
        if (this.velocity == null)
        {
            this.velocity = new Point();
        }

        this.velocity.set(velocity);
    }
}
