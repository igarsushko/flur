package by.inhw.flur.engine.behave;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Evade
{
    public static SteeringOutput getSteering(Agent agent, Agent target)
    {
        Point targetPredictedPosition = PredictPosition.predictPosition(agent, target);

        return Flee.getSteering(agent, targetPredictedPosition);
    }
}
