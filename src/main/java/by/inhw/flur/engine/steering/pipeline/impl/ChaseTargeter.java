package by.inhw.flur.engine.steering.pipeline.impl;

import static by.inhw.flur.util.VectorUtil.add;
import static by.inhw.flur.util.VectorUtil.mult;
import by.inhw.flur.engine.steering.pipeline.Goal;
import by.inhw.flur.engine.steering.pipeline.Targeter;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.Point;

public class ChaseTargeter implements Targeter
{
    private Agent chasedCharacter;

    // Controls how much to anticipate the movement
    private double lookahead = 2;

    @Override
    public Goal getGoal(Kinematic kinematic)
    {
        Goal goal = new Goal();

        Point predictedVelocity = mult(chasedCharacter.getVelocity(), lookahead);
        Point goalPosition = add(chasedCharacter.getPosition(), predictedVelocity);

        goal.setPosition(goalPosition);
        goal.setHasPosition(true);

        return goal;
    }
}
