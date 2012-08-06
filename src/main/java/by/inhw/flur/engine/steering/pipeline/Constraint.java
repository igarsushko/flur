package by.inhw.flur.engine.steering.pipeline;

import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.movement.Kinematic;

public interface Constraint
{
    /**
     * The method returns true if the given path will violate the constraint at
     * some point.
     */
    boolean isViolated(Path path);

    /**
     * The method should return a new goal that enables the character to avoid
     * violating the constraint
     */
    Goal suggest(Path path, Kinematic kinematic, Goal goal);
}
