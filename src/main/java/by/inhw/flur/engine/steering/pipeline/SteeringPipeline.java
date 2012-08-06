package by.inhw.flur.engine.steering.pipeline;

import by.inhw.flur.engine.steering.Steering;
import by.inhw.flur.engine.steering.path.Path;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.SteeringOutput;

public class SteeringPipeline implements Steering
{
    // Lists of components at each stage of the pipe
    private Targeter[] targeters;
    private Decomposer[] decomposers;
    private Constraint[] constraints;
    private Actuator actuator;

    // Holds the number of attempts the algorithm will make
    // # to find an unconstrained route.
    private double constraintSteps = 3;

    // Holds the deadlock steering behavior
    private Steering deadlock;

    // Holds the current kinematic data for the character
    private Kinematic kinematic;

    @Override
    public SteeringOutput getSteering()
    {
        // TODO get goal
        // Firstly we get the top level goal
        Goal goal = null;
        for (Targeter targeter : targeters)
        {
            goal.updateChannels(targeter.getGoal(kinematic));
        }

        // Now we decompose it
        for (Decomposer decomposer : decomposers)
        {
            goal = decomposer.decompose(kinematic, goal);
        }

        // Now we loop through the actuation and constraint process
        for (int i = 0; i < constraintSteps; i++)
        {
            // Get the path from the actuator
            Path path = actuator.getPath(kinematic, goal);

            // Check for constraint violation
            for (Constraint constraint : constraints)
            {
                // If we find a violation, get a suggestion
                if (constraint.isViolated(path))
                {
                    goal = constraint.suggest(path, kinematic, goal);

                    // Go back to the top level loop to get the
                    // path for the new goal
                    break;
                }
            }

            // If we’re here it is because we found a valid path
            return actuator.output(path, kinematic, goal);
        }

        // We arrive here if we ran out of constraint steps.
        // We delegate to the deadlock behavior
        return deadlock.getSteering();
    }
}
