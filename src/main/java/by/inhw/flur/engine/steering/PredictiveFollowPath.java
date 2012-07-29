package by.inhw.flur.engine.steering;

import by.inhw.flur.engine.steering.facing.Face;
import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Line;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.VectorUtil;

/**
 * TODO test
 */
public class PredictiveFollowPath
{
    public static boolean debug = true;
    public static String AGENT_PREV_POS_ON_PATH = "AGENT_PREV_POS_ON_PATH";

    // Holds the distance along the path to generate the
    // target. Can be negative if the character is to move
    // along the reverse direction (in meters)
    private static double pathOffset = 0.2;
    private static double predictTime = 0.6;
    private static boolean isForward = pathOffset > 0;

    public static SteeringOutput getSteering(Agent agent, Path path)
    {
        // 1. Get agent's current previous position on path
        Double prevPos = (Double) agent.getData(AGENT_PREV_POS_ON_PATH);
        if (prevPos == null)
        {
            prevPos = new Double(0);
        }

        // 2. Find the predicted future location
        Point velocity = agent.getVelocity().multiply(predictTime);
        Point futurePos = VectorUtil.add(agent.getPosition(), velocity);

        // 3. Find the current position on the path
        double currentParam = path.getParam(futurePos, prevPos, isForward);
        agent.putData(AGENT_PREV_POS_ON_PATH, new Double(currentParam));
        Debugger.log("CurrPos: ", currentParam);

        // 4. Offset it
        double targetParam = currentParam + pathOffset;
        Debugger.log("TargetPos: ", targetParam);

        // 5. Get the target position
        Point targetPosition = path.getPosition(targetParam);

        if (debug)
            Debugger.logVector("Target pos", new Line(agent.getPosition(), targetPosition));

        // 6. Delegate to seek and face
        SteeringOutput steering = Seek.getSteering(agent, targetPosition);

        double desiredRotation = Face.getFacing(agent, targetPosition);
        steering.setRotation(desiredRotation);

        return steering;
    }
}
