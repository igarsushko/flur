package by.inhw.flur.engine.steering.pipeline;

import by.inhw.flur.model.movement.Kinematic;

public interface Decomposer
{
    /**
     * The decompose method takes a goal, decomposes it if possible, and returns
     * a sub-goal. If the decomposer cannot decompose the goal, it simply
     * returns the goal it was given.
     */
    Goal decompose(Kinematic kinematic, Goal goal);
}
