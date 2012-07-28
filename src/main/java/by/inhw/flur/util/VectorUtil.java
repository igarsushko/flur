package by.inhw.flur.util;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
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

    public static Point sub(Point p1, Point p2)
    {
        return new Point(p1.getX() - p2.getX(), p1.getY() - p2.getY(), p1.getZ() - p2.getZ());
    }

    public static Point add(Point p1, Point p2)
    {
        return new Point(p1.getX() + p2.getX(), p1.getY() + p2.getY(), p1.getZ() + p2.getZ());
    }

    /**
     * Return the nearest point on line p1->p2, from point p3. Assuming infinite
     * extent in both directions.
     */
    public static Point nearestPointOnInfiniteLine(Point p1, Point p2, Point p3)
    {
        Point p1p2Direction = p2.substract(p1);

        double u = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p3.getY() - p1.getY())
                * (p2.getY() - p1.getY());
        u /= p1p2Direction.squareLength();

        // If the distance of the point to a line segment is required then it is
        // only necessary to test that u lies between 0 and 1.

        double x = p1.getX() + u * (p2.getX() - p1.getX());
        double y = p1.getY() + u * (p2.getY() - p1.getY());

        return new Point(x, y);
    }

    /*
     * Return the nearest point on line segment a->b, from point c.
     * 
     * distanceSegment = distance from the point to the line segment
     * distanceLine = distance from the point to the line (assuming infinite
     * extent in both directions
     * 
     * Subject: How do I find the distance from a point to a line?
     * 
     * Let the point be C (Cx,Cy) and the line be AB (Ax,Ay) to (Bx,By). Let P
     * be the point of perpendicular projection of C on AB. The parameter r,
     * which indicates P's position along AB, is computed by the dot product of
     * AC and AB divided by the square of the length of AB:
     * 
     * (1) AC dot AB r = --------- ||AB||^2
     * 
     * r has the following meaning:
     * 
     * r=0 P = A r=1 P = B r<0 P is on the backward extension of AB r>1 P is on
     * the forward extension of AB 0<r<1 P is interior to AB
     * 
     * The length of a line segment in d dimensions, AB is computed by:
     * 
     * L = sqrt( (Bx-Ax)^2 + (By-Ay)^2 + ... + (Bd-Ad)^2)
     * 
     * so in 2D:
     * 
     * L = sqrt( (Bx-Ax)^2 + (By-Ay)^2 )
     * 
     * and the dot product of two vectors in d dimensions, U dot V is computed:
     * 
     * D = (Ux * Vx) + (Uy * Vy) + ... + (Ud * Vd)
     * 
     * so in 2D:
     * 
     * D = (Ux * Vx) + (Uy * Vy)
     * 
     * So (1) expands to:
     * 
     * (Cx-Ax)(Bx-Ax) + (Cy-Ay)(By-Ay) r = ------------------------------- L^2
     * 
     * The point P can then be found:
     * 
     * Px = Ax + r(Bx-Ax) Py = Ay + r(By-Ay)
     * 
     * And the distance from A to P = r*L.
     * 
     * Use another parameter s to indicate the location along PC, with the
     * following meaning: s<0 C is left of AB s>0 C is right of AB s=0 C is on
     * AB
     * 
     * Compute s as follows:
     * 
     * (Ay-Cy)(Bx-Ax)-(Ax-Cx)(By-Ay) s = ----------------------------- L^2
     * 
     * 
     * Then the distance from C to P = |s|*L.
     */
    public static Point nearestPoint(Point a, Point b, Point c)
    {
        double distanceLine;
        double distanceSegment;

        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double cx = c.getX();
        double cy = c.getY();

        double r_numerator = (cx - ax) * (bx - ax) + (cy - ay) * (by - ay);
        double r_denomenator = (bx - ax) * (bx - ax) + (by - ay) * (by - ay);
        double r = r_numerator / r_denomenator;
        //
        double px = ax + r * (bx - ax);
        double py = ay + r * (by - ay);
        //
        double s = ((ay - cy) * (bx - ax) - (ax - cx) * (by - ay)) / r_denomenator;

        distanceLine = abs(s) * sqrt(r_denomenator);

        //
        // (xx,yy) is the point on the lineSegment closest to (cx,cy)
        //
        double xx = px;
        double yy = py;

        if ((r >= 0) && (r <= 1))
        {
            distanceSegment = distanceLine;
        }
        else
        {

            double dist1 = (cx - ax) * (cx - ax) + (cy - ay) * (cy - ay);
            double dist2 = (cx - bx) * (cx - bx) + (cy - by) * (cy - by);
            if (dist1 < dist2)
            {
                xx = ax;
                yy = ay;
                distanceSegment = sqrt(dist1);
            }
            else
            {
                xx = bx;
                yy = by;
                distanceSegment = sqrt(dist2);
            }

        }

        return new Point(xx, yy);
    }

    /**
     * Return the length between points.
     */
    public static double length(Point p1, Point p2)
    {
        Point vector = p2.substract(p1);
        return vector.length();
    }

    /**
     * Shortcut method for creating points.
     */
    public static Point p(double x, double y)
    {
        return new Point(x, y);
    }

    /**
     * Calculates dot product of 2 vectors.
     */
    public static double dot(Point v1, Point v2)
    {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    /**
     * Returns angle in degrees between 2 vectors
     * 
     * A.B = |A| * |B| * cos(theta); A.B = 1 * 1 * cos(theta); A.B = cos(theta);
     * theta = acos(A.B)
     */
    public static double getAngleBetweenVectors(Point v1, Point v2)
    {
        Point vec1 = v1.normalizedCopy();
        Point vec2 = v2.normalizedCopy();

        double dot = dot(vec1, vec2);

        return Math.acos(dot);
    }
}
