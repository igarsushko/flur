package by.inhw.flur.engine.behave;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;
import by.inhw.flur.util.Rand;
import by.inhw.flur.util.VectorUtil;

public class Wander
{
    public static SteeringOutput getSteering(Agent agent)
    {
        // Holds the radius and forward offset of the wander circle.
        double wanderOffset = 5;
        double wanderRadius = 3;

        // Holds the maximum rate at which the wander orientation can change
        double wanderRate = 4;

        // Holds the current orientation of the wander target
        double wanderOrientation = agent.getOrientation();

        // Holds the maximum acceleration of the character
        double maxAcceleration = agent.getMaxAcceleration();

        // Calculate the target to delegate to face
        // Update the wander orientation
        wanderOrientation += Rand.randomBinomial() * wanderRate;

        // Calculate the combined target orientation
        double targetOrientation = wanderOrientation + agent.getOrientation();

        // Calculate the center of the wander circle
        Point currentOrientationAsVector = VectorUtil.orientation2dAsVector(agent.getOrientation());
        Point target = agent.getPosition().add(currentOrientationAsVector.multiply(wanderOffset));

        // Calculate the target location
        Point targetOrientationAsVector = VectorUtil.orientation2dAsVector(targetOrientation);
        target.addToSelf(targetOrientationAsVector.multiply(wanderRadius));

        // Delegate to face
        double desiredRotation = Face.getFacing(agent, target);

        // Now set the linear acceleration to be at full acceleration in the
        // direction of the orientation
        Point velocity = currentOrientationAsVector.multiply(maxAcceleration);

        return new SteeringOutput(velocity, desiredRotation);
    }
}
