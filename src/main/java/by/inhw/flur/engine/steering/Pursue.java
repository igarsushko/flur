package by.inhw.flur.engine.steering;

import by.inhw.flur.engine.steering.predict.PredictPosition;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Pursue
{
    public static SteeringOutput getSteering(Agent agent, Agent target)
    {
        Point targetPredictedPosition = PredictPosition.predictPosition(agent, target);

        // Debugger.logVector("Pursue vector", new
        // LinePath.Line(agent.getPosition(), targetPredictedPosition));

        return Seek.getSteering(agent, targetPredictedPosition);
    }
}
