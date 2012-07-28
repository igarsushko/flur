package by.inhw.flur.model.movement;

public class Line
{
    private Point begin;
    private Point end;

    public Line(Point begin, Point end)
    {
        this.begin = begin;
        this.end = end;
    }

    public Point getBegin()
    {
        return begin;
    }

    public Point getEnd()
    {
        return end;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Line))
        {
            return false;
        }

        Line line = (Line) obj;

        return begin.equals(line.begin) && end.equals(line.end);
    }

    @Override
    public int hashCode()
    {
        return begin.hashCode() + end.hashCode() + 17;
    }

    @Override
    public String toString()
    {
        return "Line [begin(" + begin.getX() + ", " + begin.getY() + ", " + begin.getZ() + "), end(" + end.getX()
                + ", " + end.getY() + ", " + end.getZ() + ")]";
    }
}
