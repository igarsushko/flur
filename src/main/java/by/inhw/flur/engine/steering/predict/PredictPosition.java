package by.inhw.flur.engine.steering.predict;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;

public class PredictPosition
{
    public static Point predictPosition(Agent agent, Agent target)
    {
        double maxPrediction = 0.5;

        // Calculate the target to delegate to seek
        Point direction = target.getPosition().substract(agent.getPosition());
        double distance = direction.length();

        // Work out our current speed
        double speed = agent.getVelocity().length();

        double prediction = 0;
        // Check if speed is too small to give a reasonable prediction time
        if (speed <= distance / maxPrediction)
        {
            prediction = maxPrediction;
        }
        else
        // Otherwise calculate the prediction time
        {
            prediction = distance / speed;
        }

        // tsymus
        Point targetPredictedPosition = target.getPosition().createCopy();
        Point targetVelocity = target.getVelocity();

        targetPredictedPosition.addToSelf(targetVelocity.multiply(prediction));

        return targetPredictedPosition;
    }
}
