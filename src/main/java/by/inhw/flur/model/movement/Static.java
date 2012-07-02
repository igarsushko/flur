package by.inhw.flur.model.movement;

/*
 * 0------->x 
 * |
 * |
 * Y
 */
/**
 * Represents position in space and orientation.
 */
public class Static
{
    /** in radians, from the positive y axis */
    protected double orientation;
    protected Point position;

    public Static(Point position, double orientation)
    {
        this.position = position.createCopy();
        this.orientation = orientation;
    }

    public void setX(double x)
    {
        position.setX(x);
    }

    public void setY(double y)
    {
        position.setY(y);
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point position)
    {
        if (this.position == null)
        {
            this.position = new Point();
        }

        this.position.set(position);
    }

    public double getOrientation()
    {
        return orientation;
    }

    /**
     * Sets orientation in radians.
     */
    public void setOrientation(double orientation)
    {
        this.orientation = orientation;
    }

    /**
     * Sets orientation in degrees.
     */
    public void setOrientationDeg(double deg)
    {
        this.orientation = Math.toRadians(deg);
    }
}
