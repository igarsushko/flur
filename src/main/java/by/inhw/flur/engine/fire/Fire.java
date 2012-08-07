package by.inhw.flur.engine.fire;

import static by.inhw.flur.util.VectorUtil.dot;
import static by.inhw.flur.util.VectorUtil.mult;
import static by.inhw.flur.util.VectorUtil.sub;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import by.inhw.flur.model.movement.Point;

public class Fire
{
    /**
     * @param start
     *            point of shooting.
     * @param end
     *            landing point of a "bullet"
     * @param muzzle_v
     *            not a vector velocity, beginning speed of the shot bullet.
     * @param gravity
     *            gravity vector.
     * @return vector to which point a gun, so bullet reaches end.
     */
    public static Point calculateFiringSolution(Point start, Point end, double muzzle_v, Point gravity)
    {
        // Calculate the vector from the target back to the start
        Point delta = sub(start, end);

        // Calculate the real-valued a,b,c coefficients of a conventional
        // quadratic equation
        double a = dot(gravity, gravity);
        double b = -4 * (dot(gravity, delta) + muzzle_v * muzzle_v);
        double c = 4 * dot(delta, delta);

        // Check for no real solutions
        if (4 * a * c > b * b)
        {
            return null;
        }

        // Find the candidate times
        double time0 = sqrt((-b + sqrt(b * b - 4 * a * c)) / (2 * a));
        double time1 = sqrt((-b - sqrt(b * b - 4 * a * c)) / (2 * a));

        double ttt;
        // Find the time to target
        if (time0 < 0)
        {
            if (time1 < 0)
            {
                // We have no valid times
                return null;
            }
            else
            {
                ttt = time1;
            }
        }
        else
        {
            if (time1 < 0)
            {
                ttt = time0;
            }
            else
            {
                ttt = min(time0, time1);
            }
        }

        // Return the firing vector = (2 * delta - gravity * ttt*ttt) / (2 *
        // muzzle_v * ttt)

        Point numerator = sub(mult(delta, 2), mult(gravity, ttt * ttt));
        numerator.devideSelf(2 * muzzle_v * ttt);

        return numerator;
    }

    public static Point convertToDirection(Point deltaPosition, double angle)
    {
        // Find the planar direction
        Point direction = deltaPosition.createCopy();
        direction.setZ(0);
        direction.normalize();

        // Add in the vertical component
        double rad = toRadians(angle);
        direction.multiplySelf(cos(rad));
        direction.setZ(sin(rad));

        return direction;
    }

    /**
     * The distanceToTarget function runs the physics simulator and returns how
     * close the projectile got to the target. The sign of this value is
     * critical. It should be positive if the projectile overshot its target and
     * negative if it undershot. Simply performing a 3D distance test will
     * always give a positive distance value, so the simulation algorithm needs
     * to determine whether the miss was too far or too near and set the sign
     * accordingly.
     */
    public static double distanceToTarget(Point direction, Point source, Point target, double muzzleVelocity)
    {
        return 0;
    }

    /**
     * Iterative Targeting
     * 
     * The code uses the equation of motion for a projectile experiencing only
     * viscous drag.
     */
    public static Point refineTargeting(Point source, Point target, double muzzleVelocity, Point gravity, double margin)
    {
        // Get the target offset from the source
        Point deltaPosition = sub(target, source);

        // Take an initial guess from the dragless firing solution
        Point direction = calculateFiringSolution(source, target, muzzleVelocity, gravity);

        // Convert it into a firing angle.
        double minBound = asin(direction.getZ() / direction.length());

        // Find how close it gets us
        double distance = distanceToTarget(direction, source, target, muzzleVelocity);

        double maxBound = 0;
        // Check if we made it
        if (distance * distance < margin * margin)
        {
            return direction;
        }
        // Otherwise check if we overshot
        else if (minBound > 0)
        {
            // We’ve found a maximum, rather than a minimum bound, put it in the
            // right place
            // maxBound = minBound

            // Use the shortest possible shot as the minimum bound
            minBound = -90;
        }
        else
        // Otherwise we need to find a maximum bound, we use 45 degrees
        {
            maxBound = 45;

            // Calculate the distance for the maximum bound
            direction = convertToDirection(deltaPosition, maxBound);// angle
            distance = distanceToTarget(direction, source, target, muzzleVelocity);

            // See if we’ve made it
            if (distance * distance < margin * margin)
            {
                return direction;
            }
            // Otherwise make sure it overshoots
            else if (distance < 0)
            {

                // Our best shot can’t make it
                return null;
            }

        }

        // Now we have a minimum and maximum bound, use a binary search from
        // here on.
        distance = margin;
        while (distance * distance < margin * margin)
        {
            // Divide the two bounds
            double angle = (maxBound - minBound) * 0.5;

            // Calculate the distance
            direction = convertToDirection(deltaPosition, angle);
            distance = distanceToTarget(direction, source, target, muzzleVelocity);

            // Change the appropriate bound
            if (distance < 0)
            {
                minBound = angle;
            }
            else
            {
                maxBound = angle;
            }
        }

        return direction;
    }
}
