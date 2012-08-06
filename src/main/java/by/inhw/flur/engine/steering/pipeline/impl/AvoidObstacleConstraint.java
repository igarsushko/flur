package by.inhw.flur.engine.steering.pipeline.impl;

import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.engine.steering.pipeline.Constraint;
import by.inhw.flur.engine.steering.pipeline.Goal;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.Point;

public class AvoidObstacleConstraint implements Constraint
{
    // Holds the obstacle bounding sphere
    private double center, radius;

    // Holds a margin of error by which we’d ideally like
    // to clear the obstacle. Given as a proportion of the
    // radius (i.e. should be > 1.0)
    private double margin;

    // If a violation occurs, stores the part of the path
    // that caused the problem
    private int problemIndex;

    @Override
    public boolean isViolated(Path path)
    {
        // /Check each segment of the path in turn
        // for i in 0..len(path)
        // {
        // segment = path[i]

        // /If we have a clash, store the current segment
        // if (distancePointToSegment(center, segment) < radius)
        // {
        // problemIndex = i;
        // return true
        // }
        // }
        // return false;

        return false;
    }

    @Override
    public Goal suggest(Path path, Kinematic kinematic, Goal goal)
    {
        // // Find the closest point on the segment to the sphere center
        // Point closest = closestPointOnSegment(segment, center);
        //
        // // Check if we pass through the center point
        // if (closest.length() == 0)
        // {
        // // Get any vector at right angles to the segment
        // Point dirn = segment.end - segment.start;
        // Point newDirn = dirn.anyVectorAtRightAngles();
        //
        // // Use the new dirn to generate a target
        // Point newPt = center + newDirn * radius * margin;
        // }
        // else
        // // Otherwise project the point out beyond the radius
        // {
        // newPt = center + (closest - center) * radius * margin /
        // closest.length();
        // }
        //
        // goal.setPosition(newPt);
        // return goal;
        return null;
    }
}
