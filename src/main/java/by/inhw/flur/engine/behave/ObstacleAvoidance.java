package by.inhw.flur.engine.behave;

import static by.inhw.flur.util.VectorUtil.add;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Line;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;

public class ObstacleAvoidance
{
    private static CollisionDetector collisionDetector;
    public static boolean debug = false;

    public static SteeringOutput getSteering(Agent agent)
    {
        // Holds the minimum distance to a wall (i.e., how far
        // to avoid collision) should be greater than the
        // radius of the character.
        double avoidDistance = 2;

        // Holds the distance to look ahead for a collision
        // (i.e., the length of the collision ray)
        double lookahead = 3;

        // 1. Calculate the target to delegate to seek

        // Calculate the collision ray vector
        Point rayVector = agent.getVelocity().createCopy();
        rayVector.normalize();
        rayVector.multiplySelf(lookahead);

        // Find the collision
        Collision collision = collisionDetector.getCollision(agent.getPosition(), rayVector);

        if (debug)
        {
            if (collision != null)
            {
                Debugger.logPoint("Collision point", collision.getPosition());
                Debugger.logVector("Collision point", new Line(agent.getPosition(), collision.getPosition()));
            }
            else
            {
                Debugger.unlogPoint("Collision point");
                Debugger.logVector("Collision point",
                        new Line(agent.getPosition(), add(agent.getPosition(), rayVector)));
            }
        }

        if (collision == null)
        {
            return new SteeringOutput();
        }

        Point normal = collision.getNormal();
        normal.multiplySelf(avoidDistance);

        Point targetPosition = add(collision.getPosition(), normal);

        //if (debug)
        //    Debugger.logVector("Target vector", new Line(collision.getPosition(), targetPosition));

        return Seek.getSteering(agent, targetPosition);
    }

    public static void setCollisionDetector(CollisionDetector collisionDetector)
    {
        ObstacleAvoidance.collisionDetector = collisionDetector;
    }
}
