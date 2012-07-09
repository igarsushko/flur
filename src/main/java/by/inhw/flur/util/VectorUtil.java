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
    public static double getOrientationFromVelocity(Point velocity)
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
}
