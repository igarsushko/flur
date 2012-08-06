package by.inhw.flur.engine.steering.pipeline.impl;

import by.inhw.flur.engine.steering.pipeline.Decomposer;
import by.inhw.flur.engine.steering.pipeline.Goal;
import by.inhw.flur.model.movement.Kinematic;

public class PlanningDecomposer implements Decomposer
{
    // Data for the graph
    // graph
    // heuristic

    @Override
    public Goal decompose(Kinematic kinematic, Goal goal)
    {
        // / First we quantize our current location and our goal
        // / into nodes of the graph
        // start = graph.getNode(kinematic.getPosition());
        // end = graph.getNode(goal.getPosition());

        // / If they are equal, we don’t need to plan
        // if (startNode.equals(endNode))
        // {
        // return goal;
        // }

        // / Otherwise plan the route
        // path = pathfindAStar(graph, start, end, heuristic);

        // / Get the first node in the path and localize it
        // firstNode = path[0].to_node
        // position = graph.getPosition(firstNode);

        // / Update the goal and return
        // goal.setPosition(position);
        return goal;
    }
}
