package by.inhw.flur.util;

import static java.lang.Math.PI;
import by.inhw.flur.model.movement.Point;

public class VectorUtil
{
    /**
     * Get a unit length vector that points into direction of orientaion.
     * 
     * @param orientation
     *            in radians.
     * @return unit length vector.
     */
    public static Point orientation2dAsVector(double orientation)
    {
        double x = Math.sin(orientation);
        double y = Math.cos(orientation);

        return new Point(x, y);
    }

    /**
     * Gets 2D orientation in velocity direction.
     */
    public static double getOrientationFromVector(Point velocity)
    {
        return Math.atan2(velocity.getX(), velocity.getY());
    }

    public static double normalizeOrientation(double orientation)
    {
        double min = -PI;
        double max = PI;
        double newOrientation = orientation;
        while (newOrientation < min || newOrientation > max)
        {
            if (newOrientation < min)
            {
                newOrientation += 2 * PI;
            }
            else if (newOrientation > max)
            {
                newOrientation -= 2 * PI;
            }
        }

        return newOrientation;
    }

    /**
     * Return the nearest point on line p1->p2, from point p3.
     */
    public static Point nearestPoint(Point p1, Point p2, Point p3)
    {
        Point p1p2Direction = p2.substract(p1);

        double u = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p3.getY() - p1.getY())
                * (p2.getY() - p1.getY());
        u /= p1p2Direction.squareLength();

        double x = p1.getX() + u * (p2.getX() - p1.getX());
        double y = p1.getY() + u * (p2.getY() - p1.getY());

        return new Point(x, y);
    }
}
