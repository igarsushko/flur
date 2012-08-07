package by.inhw.flur.engine;

import by.inhw.flur.model.movement.Point;

public interface Controller
{
    /**
     * Returns current velocity that controller requests.
     */
    Point getVelocity();
}
