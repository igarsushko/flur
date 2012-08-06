package by.inhw.flur.engine.steering.pipeline;

import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.SteeringOutput;

public interface Actuator
{
    /**
     * The method returns the route that the character will take to the given
     * goal.
     */
    Path getPath(Kinematic kinematic, Goal goal);

    /**
     * The method returns the steering output for achieving the given path.
     */
    SteeringOutput output(Path path, Kinematic kinematic, Goal goal);
}
