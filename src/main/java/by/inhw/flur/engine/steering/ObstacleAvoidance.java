package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.add;
import by.inhw.flur.engine.steering.collision.Collision;
import by.inhw.flur.engine.steering.collision.CollisionDetector;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Line;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.platform.swing.Debugger;
import by.inhw.flur.util.VectorUtil;

public class ObstacleAvoidance implements Steering
{
    public static boolean debug = false;

    private Agent agent;
    private CollisionDetector collisionDetector;

    // Holds the minimum distance to a wall (i.e., how far
    // to avoid collision) should be greater than the
    // radius of the character.
    private double avoidDistance = 1;

    // Holds the distance to look ahead for a collision
    // (i.e., the length of the collision ray)
    private double lookahead = 3;

    public ObstacleAvoidance(Agent agent, CollisionDetector collisionDetector)
    {
        this.agent = agent;
        this.collisionDetector = collisionDetector;
    }

    @Override
    public SteeringOutput getSteering()
    {
        // 1. Calculate the target to delegate to seek

        // Calculate the collision ray vector
        Point rayVector = agent.getVelocity().createCopy();
        rayVector.normalize();

        Point whiskerLeft = getWhisker(rayVector, -30);
        Point whiskerRight = getWhisker(rayVector, 30);

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

    private Point getWhisker(Point rayVector, double angle)
    {
        Point whisker = rayVector.createCopy();
        whisker = VectorUtil.rotateVector2d(whisker, angle);
        whisker.multiplySelf(lookahead / 2);

        return whisker;
    }
}
