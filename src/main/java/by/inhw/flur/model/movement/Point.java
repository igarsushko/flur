package by.inhw.flur.model.movement;

/**
 * Represents a point in space, or a vector, just as you wish.
 */
public final class Point
{
    double x;
    double y;
    double z;

    /**
     * All to zero.
     */
    public Point()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * 2 and half D.
     */
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Point(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Sets x, y, z from parameter to self.
     */
    public void set(Point point)
    {
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }

    /**
     * Creates copy of self, adds parameter point to copy, and returns copy.
     */
    public Point add(Point point)
    {
        Point copy = this.createCopy();
        copy.addToSelf(point);

        return copy;
    }

    public void addToSelf(Point point)
    {
        this.x += point.x;
        this.y += point.y;
        this.z += point.z;
    }

    /**
     * Creates copy of self, substracts parameter point from copy, and returns
     * copy.
     */
    public Point substract(Point point)
    {
        Point copy = this.createCopy();
        copy.substractFromSelf(point);

        return copy;
    }

    public void substractFromSelf(Point point)
    {
        this.x -= point.x;
        this.y -= point.y;
        this.z -= point.z;
    }

    private double decreezeToZero(double value, double decreazeOn)
    {
        if (value < 0)
        {
            double newValue = value + decreazeOn;
            if (newValue > 0)
            {
                return 0;
            }
            else
            {
                return newValue;
            }
        }
        else
        {
            double newValue = value - decreazeOn;
            if (newValue < 0)
            {
                return 0;
            }
            else
            {
                return newValue;
            }
        }
    }

    private double decreeze(double value, double decreazeOn)
    {
        if (value < 0)
        {
            return value + decreazeOn;
        }
        else
        {
            return value - decreazeOn;
        }
    }

    public void decreazeToZero(double decreazeOn)
    {
        x = decreezeToZero(x, decreazeOn);
        y = decreezeToZero(y, decreazeOn);
        z = decreezeToZero(z, decreazeOn);
    }

    public void decreaze(double decreazeOn)
    {
        x = decreeze(x, decreazeOn);
        y = decreeze(y, decreazeOn);
        z = decreeze(z, decreazeOn);
    }

    /**
     * Creates copy of self, multiplies copy on scalar, and returns copy.
     */
    public Point multiply(double scalar)
    {
        Point copy = this.createCopy();
        copy.multiplySelf(scalar);

        return copy;
    }

    public void multiplySelf(double scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
    }

    /**
     * Creates copy of self, devides copy on scalar, and returns copy.
     */
    public Point devide(double scalar)
    {
        Point copy = this.createCopy();
        copy.devideSelf(scalar);

        return copy;
    }

    public void devideSelf(double scalar)
    {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
    }

    public void normalize()
    {
        double length = length();
        if (length != 0)
        {
            x /= length;
            y /= length;
            z /= length;
        }
    }

    public double length()
    {
        return Math.sqrt(squareLength());
    }

    public double squareLength()
    {
        return (x * x) + (y * y) + (z * z);
    }

    /**
     * @return true if either of x, y, z is non zero.
     */
    public boolean isNonZero()
    {
        return x != 0 || y != 0 || z != 0;
    }

    public Point createCopy()
    {
        return new Point(x, y, z);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Point))
        {
            return false;
        }

        Point p = (Point) obj;
        return this.x == p.x && this.y == p.y && this.z == p.z;
    }

    @Override
    public int hashCode()
    {
        return (int) (x * 13 + y * 51 + z * 77);
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public int xInt()
    {
        return (int) x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public int yInt()
    {
        return (int) y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public int zInt()
    {
        return (int) z;
    }
}
