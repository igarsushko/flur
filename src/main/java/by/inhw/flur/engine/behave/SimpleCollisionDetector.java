package by.inhw.flur.engine.behave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.inhw.flur.model.World;
import by.inhw.flur.model.movement.Line;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.VectorUtil;
import static by.inhw.flur.util.VectorUtil.p;
import static by.inhw.flur.util.VectorUtil.add;
import static by.inhw.flur.util.VectorUtil.sub;
import static by.inhw.flur.util.VectorUtil.cross;

public class SimpleCollisionDetector implements CollisionDetector
{
    private World world;
    private List<Line> obstacleLines = new ArrayList<Line>();
    private Map<Point, Line> collisionPointsAndLines = new HashMap<Point, Line>();
    private Point upVector = new Point(0, 0, 1);

    public SimpleCollisionDetector(World world)
    {
        this.world = world;
        collectCollisionInfo(this.world.getMap());
    }

    public Collision getCollision(Point agentPosition, Point rayVector)
    {
        Collision collision = null;
        collisionPointsAndLines.clear();

        Point p3 = agentPosition;
        Point p4 = add(agentPosition, rayVector);
        for (Line line : obstacleLines)
        {
            // Debugger.logVector(line.toString(), line);
            Point p1 = line.getBegin();
            Point p2 = line.getEnd();

            Point collisionPoint = VectorUtil.getLineIntersection2d(p1, p2, p3, p4);
            if (collisionPoint != null)
            {
                collisionPointsAndLines.put(collisionPoint, line);
            }
        }

        // find the closest collision
        double shortestDistance = Double.MAX_VALUE;
        Point nearestCollisionPoint = null;
        for (Point point : collisionPointsAndLines.keySet())
        {
            Point direction = sub(point, agentPosition);
            double length = direction.length();
            if (length < shortestDistance)
            {
                shortestDistance = length;
                nearestCollisionPoint = point;
            }
        }

        if (nearestCollisionPoint != null)
        {
            Line line = collisionPointsAndLines.get(nearestCollisionPoint);
            Point lineVector = sub(line.getEnd(), line.getBegin());

            Point normal = cross(lineVector, upVector);
            normal.normalize();

            collision = new Collision(nearestCollisionPoint, normal);
        }

        return collision;
    }

    void collectCollisionInfo(int[][] map)
    {
        for (int y = 0; y < world.getHeight() - 1; y++)
        {
            for (int x = 0; x < world.getWidth() - 1; x++)
            {
                if (map[y][x] == World.OBSTACLE)
                {
                    obstacleLines.add(new Line(p(x, y), p(x + 1, y)));
                    obstacleLines.add(new Line(p(x + 1, y), p(x + 1, y + 1)));
                    obstacleLines.add(new Line(p(x + 1, y + 1), p(x, y + 1)));
                    obstacleLines.add(new Line(p(x, y + 1), p(x, y)));
                }
            }
        }
    }
}
