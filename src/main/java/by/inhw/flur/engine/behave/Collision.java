package by.inhw.flur.engine.behave;

import by.inhw.flur.model.movement.Point;

public class Collision
{
    /** collision point */
    private Point position;
    /**
     * Normal vector (perpendicular), normalized (length == 1) vector that
     * points from the wall at the point of collision
     */
    private Point normal;

    public Collision(Point position, Point normal)
    {
        this.position = position;
        this.normal = normal;
    }

    public Point getNormal()
    {
        return normal;
    }

    public Point getPosition()
    {
        return position;
    }
}
