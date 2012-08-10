package by.inhw.flur.engine.steering.pipeline;

import by.inhw.flur.model.movement.Point;

/**
 * Goals need to store each channel, along with an indication as to whether the
 * channel should be used.
 */
public class Goal
{
    // Flags to indicate if each channel is to be used
    private boolean hasPosition;
    private boolean hasOrientation;
    private boolean hasVelocity;
    private boolean hasRotation;

    // Data for each channel
    private Point position;
    private double orientation;
    private Point velocity;
    private double rotation;

    // Updates this goal
    public void updateChannels(Goal goal)
    {
        if (goal.hasPosition)
        {
            position.set(goal.position);
        }

        if (goal.hasOrientation)
        {
            orientation = goal.orientation;
        }

        if (goal.hasVelocity)
        {
            velocity.set(goal.velocity);
        }

        if (goal.hasRotation)
        {
            rotation = goal.rotation;
        }
    }

    public boolean hasPosition()
    {
        return hasPosition;
    }

    public void setHasPosition(boolean hasPosition)
    {
        this.hasPosition = hasPosition;
    }

    public boolean hasOrientation()
    {
        return hasOrientation;
    }

    public void setHasOrientation(boolean hasOrientation)
    {
        this.hasOrientation = hasOrientation;
    }

    public boolean hasVelocity()
    {
        return hasVelocity;
    }

    public void setHasVelocity(boolean hasVelocity)
    {
        this.hasVelocity = hasVelocity;
    }

    public boolean hasRotation()
    {
        return hasRotation;
    }

    public void setHasRotation(boolean hasRotation)
    {
        this.hasRotation = hasRotation;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }

    public double getOrientation()
    {
        return orientation;
    }

    public void setOrientation(double orientation)
    {
        this.orientation = orientation;
    }

    public Point getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Point velocity)
    {
        this.velocity = velocity;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

}
