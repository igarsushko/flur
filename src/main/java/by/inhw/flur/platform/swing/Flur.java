package by.inhw.flur.platform.swing;

import static by.inhw.flur.util.VectorUtil.p;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFrame;

import by.inhw.flur.engine.BrainFactory;
import by.inhw.flur.engine.steering.ObstacleAvoidance;
import by.inhw.flur.engine.steering.collision.CollisionDetector;
import by.inhw.flur.engine.steering.collision.SimpleCollisionDetector;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;
import by.inhw.flur.platform.swing.controller.ControllerListener;
import by.inhw.flur.platform.swing.render.WorldRendererImpl;
import by.inhw.flur.util.LevelLoader;
import by.inhw.flur.util.PropertiesManager;
import by.inhw.flur.util.Timing;

public class Flur
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Flur");
        Debugger.setFrame(frame);
        // Debugger.off();
        // Timing.setPaused(true);
        frame.setLayout(new BorderLayout());
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Properties worldProps = PropertiesManager.load("src/main/resources/world.properties");
        int worldScale = Integer.valueOf(worldProps.getProperty("world.scale")).intValue();

        WorldRendererImpl renderer = new WorldRendererImpl(worldScale);
        frame.add(renderer);

        int[][] map = LevelLoader.loadLevel("src/main/resources/map/level_1.map");
        World world = new World(map, renderer);
        world.renderWorld();

        CollisionDetector collisionDetector = new SimpleCollisionDetector(world);

        // Agent player = world.registerAgent(new Agent("player", "red", 6, 7,
        // 8, 40), p(12, 12));
        // player.setBrain(BrainFactory.puppetBrain(player));
        ControllerListener keyListener = new ControllerListener();
        frame.addKeyListener(keyListener);

        Agent bot1 = world.registerAgent(new Agent("bot1", "blue", 3, 60, 10, 40), p(5, 5));
        Agent bot2 = world.registerAgent(new Agent("bot2", "blue", 3, 60, 10, 40), p(8, 8));
        Agent bot3 = world.registerAgent(new Agent("bot3", "blue", 3, 60, 10, 40), p(15, 15));

        bot1.setBrain(BrainFactory.priorityObstacleAvoidanceAndWander(bot1, collisionDetector));
        bot2.setBrain(BrainFactory.priorityObstacleAvoidanceAndWander(bot2, collisionDetector));
        bot3.setBrain(BrainFactory.priorityObstacleAvoidanceAndWander(bot3, collisionDetector));

        world.bringWorldToLive();
        frame.pack();
        Debugger.setWorldRenderer(renderer);
    }
}

// LinePath path = new LinePath(p(5, 5), p(7, 15), p(12, 16), p(14, 14),
// p(16, 5), p(18, 3), p(5, 5));
// LinePath path = new LinePath(p(5, 4), p(3, 6), p(4, 11), p(10, 12),
// p(16, 10), p(17, 7), p(14, 5), p(9, 6), p(
// 4, 14), p(6, 17), p(9, 15), p(5, 4));

// LinePath path = new LinePath(p(7, 7), p(13, 7), p(13, 13), p(7, 13),
// p(7, 7));
// Debugger.drawPath(path);

