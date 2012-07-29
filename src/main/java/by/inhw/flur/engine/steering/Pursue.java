package by.inhw.flur.engine.steering;

import by.inhw.flur.engine.steering.predict.PredictPosition;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Pursue extends Seek
{
    public Pursue(Agent agent, Agent target)
    {
        super(agent, target);
    }

    @Override
    public SteeringOutput getSteering()
    {
        Point targetPredictedPosition = PredictPosition.predictPosition(agent, target);

        // Debugger.logVector("Pursue vector", new
        // LinePath.Line(agent.getPosition(), targetPredictedPosition));

        return Seek.getSteering(agent, targetPredictedPosition);
    }
}
