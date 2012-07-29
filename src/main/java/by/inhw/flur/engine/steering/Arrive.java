package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.sub;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Arrive implements Steering
{
    private Agent agent;
    private Agent target;

    private double arriveRadius = 2;
    private double slowRadius = 7;
    private double timeToTarget = 0.1;

    public Arrive(Agent agent, Agent target)
    {
        this.agent = agent;
        this.target = target;
    }

    public SteeringOutput getSteering()
    {
        double maxSpeed = agent.getMaxSpeed();
        double finalSpeed = 0;

        Point direction = sub(target.getPosition(), agent.getPosition());

        double distance = direction.length();
        if (distance <= arriveRadius)
        {
            return new SteeringOutput(new Point(), 0);
        }
        else if (distance > arriveRadius && distance < slowRadius)
        {
            finalSpeed = maxSpeed * distance / slowRadius;
        }
        else if (distance >= slowRadius)
        {
            finalSpeed = maxSpeed;
        }

        Point finalVelocity = direction;
        finalVelocity.normalize();
        finalVelocity.multiplySelf(finalSpeed);

        // Acceleration tries to get to the target velocity
        finalVelocity.substractFromSelf(agent.getKinematic().getVelocity());
        finalVelocity.devideSelf(timeToTarget);

        // Check if the acceleration is too fast
        double maxAccel = agent.getMaxAcceleration();
        if (finalVelocity.length() > maxAccel)
        {
            finalVelocity.normalize();
            finalVelocity.multiplySelf(maxAccel);
        }

        return new SteeringOutput(finalVelocity, 0);
    }
}
