package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.add;
import static by.inhw.flur.util.VectorUtil.mult;
import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;

/**
 * TODO test
 */
public class PredictiveFollowPath extends FollowPath
{
    private double predictTime = 0.6;

    public PredictiveFollowPath(Agent agent, Path path)
    {
        super(agent, path);
    }

    @Override
    protected Point getAgentPosition()
    {
        // Find the predicted future location
        Point velocity = mult(agent.getVelocity(), predictTime);
        Point futurePos = add(agent.getPosition(), velocity);

        return futurePos;
    }
}
