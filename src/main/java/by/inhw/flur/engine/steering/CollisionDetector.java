package by.inhw.flur.engine.steering;

import by.inhw.flur.model.movement.Point;

public interface CollisionDetector
{
    Collision getCollision(Point agentPosition, Point... rayVector);
}
