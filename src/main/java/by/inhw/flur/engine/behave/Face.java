package by.inhw.flur.engine.behave;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.util.VectorUtil;

/**
 * Returns acceleration required to face in target direction.
 */
public class Face
{
    public static double getFacing(Agent agent, Agent target)
    {
        return Face.getFacing(agent, target.getPosition());
    }

    public static double getFacing(Agent agent, Point targetPosition)
    {
        Point direction = targetPosition.substract(agent.getPosition());

        if (direction.length() == 0)
        {
            return 0;
        }

        double desiredOrientation = VectorUtil.getOrientationFromVector(direction);

        return Align.getAlign(agent, desiredOrientation);
    }
}
