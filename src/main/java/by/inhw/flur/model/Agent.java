package by.inhw.flur.model;

import by.inhw.flur.engine.Brain;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.KinematicSteeringOutput;
import by.inhw.flur.render.AgentRenderer;

public class Agent extends WorldPart
{
    String name;
    String color;
    Brain brain;
    Kinematic kinematic;
    AgentRenderer renderer;
    double maxSpeed;
    double maxRotationSpeed;
    World world;

    public Agent(String name, String color, double maxSpeed, double maxRotationSpeed)
    {
        this.name = name;
        this.color = color;
        this.maxSpeed = maxSpeed;
        this.maxRotationSpeed = maxRotationSpeed;
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

    public KinematicSteeringOutput nextMove()
    {
        return brain.nextMove();
    }

    public void updateKinematic(KinematicSteeringOutput steering, double timeInSeconds)
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

    public Point getPosition()
    {
        return kinematic.getPosition();
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
}
