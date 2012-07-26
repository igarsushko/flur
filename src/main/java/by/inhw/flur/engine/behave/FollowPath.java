package by.inhw.flur.engine.behave;

import by.inhw.flur.engine.behave.LinePath.Line;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;

public class FollowPath
{
    public static boolean debug = true;
    public static String AGENT_PREV_POS_ON_PATH = "AGENT_PREV_POS_ON_PATH";

    // Holds the distance along the path to generate the
    // target. Can be negative if the character is to move
    // along the reverse direction (in meters)
    private static double pathOffset = 3;
    private static boolean isForward = pathOffset > 0;

    public static SteeringOutput getSteering(Agent agent, Path path)
    {
        // 1. Get agent's current previous position on path
        Double prevPos = (Double) agent.getData(AGENT_PREV_POS_ON_PATH);
        if (prevPos == null)
        {
            prevPos = new Double(0);
        }

        // 2. Find the current position on the path
        double currentParam = path.getParam(agent.getPosition(), prevPos, isForward);
        agent.putData(AGENT_PREV_POS_ON_PATH, new Double(currentParam));
        if (debug)
            Debugger.log("CurrPos: ", currentParam);

        // 3. Offset it
        double targetParam = currentParam + pathOffset;
        if (debug)
            Debugger.log("TargetPos: ", targetParam);

        // 4. Get the target position
        Point targetPosition = path.getPosition(targetParam);

        if (debug)
            Debugger.logVector("Target pos", new Line(agent.getPosition(), targetPosition));

        // 5. Delegate to seek and face
        SteeringOutput steering = Seek.getSteering(agent, targetPosition);

        double desiredRotation = Face.getFacing(agent, targetPosition);
        steering.setRotation(desiredRotation);

        return steering;
    }
}
