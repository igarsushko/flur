package by.inhw.flur.model;

import by.inhw.flur.engine.Brain;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.render.AgentRenderer;

public class Agent extends WorldPart
{
    String name;
    String color;
    Brain brain;
    Kinematic kinematic;
    AgentRenderer renderer;
    double maxSpeed;
    double maxAcceleration;
    double maxRotationSpeed;
    double maxAngularAcceleration;
    World world;

    public Agent(String name, String color, double maxSpeed, double maxAcceleration, double maxRotationSpeed,
            double maxAngularAcceleration)
    {
        this.name = name;
        this.color = color;
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.maxRotationSpeed = maxRotationSpeed;
        this.maxAngularAcceleration = maxAngularAcceleration;
        this.kinematic = new Kinematic(new Point(), 0, new Point());
    }

    public void setBrain(Brain brain)
    {
        this.brain = brain;
    }

    public void setPosition(Point position)
    {
        kinematic.setPosition(position);
    }

    public SteeringOutput nextMove()
    {
        return brain.nextMove();
    }

    public void updateKinematic(SteeringOutput steering, double timeInSeconds)
    {
        kinematic.update(steering, timeInSeconds, maxSpeed, maxRotationSpeed);
        world.normalizeCoordinates2D(this);
        renderer.renderAgent();
    }

    public Kinematic getKinematic()
    {
        return kinematic;
    }

    public double x()
    {
        return kinematic.getPosition().getX();
    }

    public double y()
    {
        return kinematic.getPosition().getY();
    }

    public double getOrientation()
    {
        return kinematic.getOrientation();
    }

    public double getRotation()
    {
        return kinematic.getRotation();
    }

    public Point getPosition()
    {
        return kinematic.getPosition();
    }

    public Point getVelocity()
    {
        return kinematic.getVelocity();
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }

    public void setRenderer(AgentRenderer renderer)
    {
        this.renderer = renderer;
    }

    public double getMaxSpeed()
    {
        return maxSpeed;
    }

    public double getMaxAcceleration()
    {
        return maxAcceleration;
    }

    public double getMaxRotationSpeed()
    {
        return maxRotationSpeed;
    }

    public double getMaxAngularAcceleration()
    {
        return maxAngularAcceleration;
    }
}
