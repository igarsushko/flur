package by.inhw.flur.engine.steering.path;

import by.inhw.flur.model.movement.Point;

public interface Path
{
    Point getPosition(double targetParam);

    double getParam(Point position, double currentPos, boolean isForward);
    
    double length();
}
