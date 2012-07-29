package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.add;
import static by.inhw.flur.util.VectorUtil.dot;
import static by.inhw.flur.util.VectorUtil.sub;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

/**
 * The algorithm needs to search for the character whose closest approach will
 * occur first and to react to this character only. Once this imminent collision
 * is avoided, the steering behavior can then react to more distant characters.
 */
public class CollisionAvoidance implements Steering
{
    private Agent agent;
    private Agent[] targets;

    // Holds the collision radius of a character (we assume
    // all characters have the same radius here)
    double radius = 5;

    public CollisionAvoidance(Agent agent, Agent... targets)
    {
        this.agent = agent;
        this.targets = targets;
    }

    @Override
    public SteeringOutput getSteering()
    {
        // Find the target that�s closest to collision
        // Store the first collision time
        double shortestTime = Double.MAX_VALUE;

        // Store the target that collides then, and other data
        // that we will need and can avoid recalculating
        Agent firstTarget = null;
        double firstMinSeparation = 0;
        double firstDistance = 0;
        Point firstRelativePos = null;
        Point firstRelativeVel = null;

        // 1. Find the target that�s closest to collision
        for (Agent target : targets)
        {
            // Calculate the time to collision
            Point relativePos = sub(target.getPosition(), agent.getPosition());
            Point relativeVel = sub(target.getVelocity(), agent.getVelocity());
            double relativeSpeed = relativeVel.length();
            double timeToCollision = dot(relativePos, relativeVel) / (relativeSpeed * relativeSpeed);

            // Check if it is going to be a collision at all
            double distance = relativePos.length();
            double minSeparation = distance - relativeSpeed * timeToCollision;

            if (minSeparation > radius)
            {
                continue;
            }

            // Check if it is the shortest
            if (timeToCollision > 0 && timeToCollision < shortestTime)
            {
                // Store the time, target and other data
                shortestTime = timeToCollision;
                firstTarget = target;
                firstMinSeparation = minSeparation;
                firstDistance = distance;
                firstRelativePos = relativePos;
                firstRelativeVel = relativeVel;
            }
        }

        // 2. Calculate the steering

        // If we have no target, then exit
        if (firstTarget == null)
        {
            return Wander.getSteering(agent);
        }

        // If we�re going to hit exactly, or if we�re already
        // colliding, then do the steering based on current position.
        Point relativePos = null;
        if (firstMinSeparation <= 0 || firstDistance < radius)
        {
            relativePos = sub(firstTarget.getPosition(), agent.getPosition());
        }
        // Otherwise calculate the future relative position
        else
        {
            firstRelativeVel.multiplySelf(shortestTime);
            relativePos = add(firstRelativePos, firstRelativeVel);
        }

        // 3. Avoid the target
        relativePos.normalize();
        relativePos.multiplySelf(-agent.getMaxAcceleration());

        return new SteeringOutput(relativePos, 0);
    }
}
