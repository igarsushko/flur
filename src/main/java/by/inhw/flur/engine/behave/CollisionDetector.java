package by.inhw.flur.engine.behave;

import by.inhw.flur.model.movement.Point;

public interface CollisionDetector
{
    Collision getCollision(Point agentPosition, Point rayVector);
}
