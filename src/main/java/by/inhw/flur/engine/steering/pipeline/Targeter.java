package by.inhw.flur.engine.steering.pipeline;

import by.inhw.flur.model.movement.Kinematic;

public interface Targeter
{
    Goal getGoal(Kinematic kinematic);
}
