package by.inhw.flur.engine.behave;

import by.inhw.flur.model.movement.Point;

public interface Path
{
    Point getPosition(double targetParam);

    double getParam(Point position, double currentPos);
    
    double length();
}
