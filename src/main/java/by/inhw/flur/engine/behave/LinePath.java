package by.inhw.flur.engine.behave;

import java.util.ArrayList;
import java.util.List;

import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.VectorUtil;

public class LinePath implements Path
{

    public static boolean debug = true;
    private ArrayList<Point> segments;
    private ArrayList<Line> lines;
    private double pathLength = 0;

    public LinePath(Point... dots)
    {
        createPath(dots);
    }

    public static class Line
    {
        Point begin;
        Point end;
        double length;
        Line prevLine;
        Line nextLine;

        Line(Point begin, Point end)
        {
            this.begin = begin;
            this.end = end;
            this.length = VectorUtil.length(this.begin, this.end);
        }

        public Point getBegin()
        {
            return begin;
        }

        public Point getEnd()
        {
            return end;
        }
    }

    /**
     * @param pathDistance
     *            the distance in meters on path.
     * @return position in the world that corresponds to the distance on the
     *         path.
     */
    public Point getPosition(double pathDistance)
    {
        if (debug)
            Debugger.log("Path length: ", pathLength);

        // cycling path support
        // 1. backwards
        if (pathDistance < 0)
        {
            pathDistance += pathLength;
        }
        // 2. cycling forward
        else if (pathDistance > pathLength)
        {
            pathDistance -= pathLength;
        }

        // 1. walk through lines to see on which line we are
        double linesLength = 0;
        Line desiredLine = null;
        for (Line line : lines)
        {
            linesLength += line.length;
            if (linesLength >= pathDistance)
            {
                desiredLine = line;
                break;
            }
        }

        // begin-------targetPos---end
        // targetPos to end == distance
        double distance = linesLength - pathDistance;
        Point endBeginVector = VectorUtil.sub(desiredLine.end, desiredLine.begin);

        Point endBeginNorm = endBeginVector.createCopy();
        endBeginNorm.normalize();

        Point shift = endBeginNorm.multiply(distance);

        Point targetPosition = VectorUtil.sub(desiredLine.end, shift);

        if (debug)
            Debugger.logPoint("target pos point", targetPosition);

        return targetPosition;
    }

    // sending the last parameter value to the path in
    // order to calculate the current parameter value. This is essential to
    // avoid nasty problems when
    // lines are close together.
    // We limit the getParam algorithm to only considering areas of the path
    // close to the previous
    // parameter value. The character is unlikely to have moved far, after all.
    // This technique, assuming
    // the new value is close to the old one, is called coherence, and it is a
    // feature of many geometric
    // algorithms.
    public double getParam(Point agentCurrPos, double lastParameter)
    {
        // get nearest segment
        double smallestDistance = -1;
        Point nearestOnPath = null;
        Line nearestLine = null;
        for (Line line : lines)
        {
            Point nearestToCurrLine = VectorUtil.nearestPoint(line.begin, line.end, agentCurrPos);
            double length = VectorUtil.length(agentCurrPos, nearestToCurrLine);

            // first point
            if (smallestDistance == -1)
            {
                nearestOnPath = nearestToCurrLine;
                smallestDistance = length;
                nearestLine = line;
                continue;
            }

            if (length < smallestDistance)
            {
                nearestOnPath = nearestToCurrLine;
                smallestDistance = length;
                nearestLine = line;
            }
        }

        if (debug)
            Debugger.logVector("Nearest on segment", new Line(agentCurrPos, nearestOnPath));

        double pathLength = VectorUtil.length(nearestLine.begin, nearestOnPath);
        Line currLine = nearestLine;
        while (currLine.prevLine != null)
        {
            currLine = currLine.prevLine;
            pathLength += currLine.length;
        }

        return pathLength;
    }

    void createPath(Point... dots)
    {
        segments = new ArrayList<Point>();
        for (Point point : dots)
        {
            segments.add(point);
        }

        lines = new ArrayList<LinePath.Line>();
        Point curr = null;
        Point prev = null;
        for (int i = 0; i < dots.length; i++)
        {
            Point dot = dots[i];
            if (i == 0)
            {
                curr = dot;
                continue;
            }

            prev = curr;
            curr = dot;

            lines.add(new Line(prev, curr));
        }

        // link lines together, and get total length
        for (int i = 0; i < lines.size(); i++)
        {
            Line line = lines.get(i);
            pathLength += line.length;

            if (i == 0)
            {
                line.prevLine = null;
                line.nextLine = lines.get(i + 1);
            }
            else if (i == lines.size() - 1)
            {
                line.prevLine = lines.get(i - 1);
                line.nextLine = null;
            }
            else
            {
                line.prevLine = lines.get(i - 1);
                line.nextLine = lines.get(i + 1);
            }
        }
    }

    public List<LinePath.Line> getLines()
    {
        return lines;
    }

    public double length()
    {
        return pathLength;
    }
}
