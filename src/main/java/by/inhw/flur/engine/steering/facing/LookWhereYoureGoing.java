package by.inhw.flur.engine.steering.facing;

import by.inhw.flur.model.Agent;
import by.inhw.flur.util.VectorUtil;

public class LookWhereYoureGoing
{
    public static double getWhereYoureGoingFacing(Agent agent)
    {
        if (agent.getVelocity().length() == 0)
        {
            return 0;
        }

        double desiredOrientation = VectorUtil.getOrientationFromVector(agent.getVelocity());

        return Align.getAlign(agent, desiredOrientation);
    }
}
