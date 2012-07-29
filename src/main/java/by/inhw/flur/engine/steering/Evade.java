package by.inhw.flur.engine.steering;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Evade extends Flee
{
    public Evade(Agent agent, Agent persuader)
    {
        super(agent, persuader);
    }

    public SteeringOutput getSteering()
    {
        Point persuaderPredictedPosition = PredictPosition.predictPosition(agent, persuader);

        return super.getSteering(persuaderPredictedPosition);
    }

}
