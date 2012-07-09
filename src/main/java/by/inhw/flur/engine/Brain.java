package by.inhw.flur.engine;

import by.inhw.flur.model.movement.KinematicSteeringOutput;

public interface Brain
{
    KinematicSteeringOutput nextMove();
}
