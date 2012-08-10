package by.inhw.flur.engine.steering;

import static by.inhw.flur.util.VectorUtil.near;
import static by.inhw.flur.util.VectorUtil.sub;
import static java.lang.Math.sqrt;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;
import by.inhw.flur.model.movement.Kinematic;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.model.movement.SteeringOutput;

public class Jump implements Steering
{
    private Agent agent;

    // Holds the jump point to use
    private JumpPoint jumpPoint;

    double GRAVITY_Z = World.G;

    public Jump(Agent agent)
    {
        this.agent = agent;
    }

    @Override
    public SteeringOutput getSteering()
    {
        Kinematic target = calculateTarget();

        // Check if the trajectory is zero
        if (target == null)
        {
            // If not, we have no acceleration
            return new SteeringOutput();
        }

        target.setPosition(jumpPoint.jumpLocation);

        if (near(agent.getPosition(), target.getPosition()) && near(agent.getVelocity(), target.getVelocity()))
        {
            // Perform the jump, and return no steering (we’re airborne, no need
            // to steer)
            World.scheduleJumpAction(agent, target);
            return new SteeringOutput();
        }

        // Delegate the steering
        return VelocityMatch.matchVelocity(agent, target.getVelocity());
    }

    // Works out the trajectory calculation
    Kinematic calculateTarget()
    {
        Kinematic target = null;

        double maxZVelocity = agent.getMaxZVelocity();

        // Calculate the first jump time
        double sqrtTerm = sqrt(2 * GRAVITY_Z * jumpPoint.deltaPosition.getZ() + maxZVelocity * maxZVelocity);
        double time = (maxZVelocity - sqrtTerm) / GRAVITY_Z;

        // Check if we can use it
        target = checkJumpTime(time);
        if (target == null)
        {
            // Otherwise try the other time
            time = (maxZVelocity + sqrtTerm) / GRAVITY_Z;
            target = checkJumpTime(time);
        }

        return target;
    }

    private Kinematic checkJumpTime(double time)
    {
        // Calculate the planar speed
        double vx = jumpPoint.deltaPosition.getX() / time;
        double vy = jumpPoint.deltaPosition.getY() / time;
        double speedSq = vx * vx + vy * vy;

        // Check it
        double maxSpeed = agent.getMaxSpeed();
        if (speedSq < maxSpeed * maxSpeed)
        {
            // We have a valid solution,
            Kinematic target = new Kinematic();
            target.getVelocity().setX(vx);
            target.getVelocity().setY(vy);

            return target;
        }

        // can't achieve
        return null;
    }

    public void setJumpPonint(Point jumpLocation, Point landingLocation)
    {
        jumpPoint = new JumpPoint(jumpLocation, landingLocation);
    }

    public static class JumpPoint
    {
        // The position of the jump point
        private Point jumpLocation;

        // The position of the landing pad
        private Point landingLocation;

        // The change in position from jump to landing
        // This is calculated from the other values
        private Point deltaPosition;

        public JumpPoint(Point jumpLocation, Point landingLocation)
        {
            this.jumpLocation = jumpLocation.createCopy();
            this.landingLocation = landingLocation.createCopy();

            deltaPosition = sub(landingLocation, jumpLocation);
        }

        public Point getJumpLocation()
        {
            return jumpLocation;
        }

        public Point getLandingLocation()
        {
            return landingLocation;
        }
    }
}
