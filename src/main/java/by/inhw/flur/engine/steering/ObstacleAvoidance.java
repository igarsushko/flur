package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.add;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Line;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.VectorUtil;

public class ObstacleAvoidance
{
    private static CollisionDetector collisionDetector;
    public static boolean debug = false;

    public static SteeringOutput getSteering(Agent agent)
    {
        // Holds the minimum distance to a wall (i.e., how far
        // to avoid collision) should be greater than the
        // radius of the character.
        double avoidDistance = 1;

        // Holds the distance to look ahead for a collision
        // (i.e., the length of the collision ray)
        double lookahead = 3;

        // 1. Calculate the target to delegate to seek

        // Calculate the collision ray vector
        Point rayVector = agent.getVelocity().createCopy();
        rayVector.normalize();

        Point whiskerLeft = rayVector.createCopy();
        whiskerLeft = VectorUtil.rotateVector2d(whiskerLeft, -30);
        whiskerLeft.multiplySelf(lookahead / 2);

        Point whiskerRight = rayVector.createCopy();
        whiskerRight = VectorUtil.rotateVector2d(whiskerRight, 30);
        whiskerRight.multiplySelf(lookahead / 2);

        rayVector.multiplySelf(lookahead);

        // Find the collision
        Collision collision = collisionDetector.getCollision(agent.getPosition(), rayVector, whiskerLeft, whiskerRight);

        if (debug)
        {
            String debugId = "Collision point " + agent.getName();
            if (collision != null)
            {
                Debugger.logPoint(debugId, collision.getPosition());
                Debugger.logVector(debugId, new Line(agent.getPosition(), collision.getPosition()));
            }
            else
            {
                Debugger.unlogPoint(debugId);
                Debugger.logVector(debugId, new Line(agent.getPosition(), add(agent.getPosition(), rayVector)));
                Debugger.logVector(debugId + "wl", new Line(agent.getPosition(), add(agent.getPosition(), whiskerLeft)));
                Debugger.logVector(debugId + "wr",
                        new Line(agent.getPosition(), add(agent.getPosition(), whiskerRight)));
            }
        }

        if (collision == null)
        {
            return new SteeringOutput();
        }

        Point normal = collision.getNormal();
        normal.multiplySelf(avoidDistance);

        Point targetPosition = add(collision.getPosition(), normal);

        // if (debug)
        // Debugger.logVector("Target vector", new Line(collision.getPosition(),
        // targetPosition));

        return Seek.getSteering(agent, targetPosition);
    }

    public static void setCollisionDetector(CollisionDetector collisionDetector)
    {
        ObstacleAvoidance.collisionDetector = collisionDetector;
    }
}
