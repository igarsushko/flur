package by.inhw.flur.platform.swing;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JFrame;

import by.inhw.flur.engine.BrainFactory;
import by.inhw.flur.engine.KinematicBrainFactory;
import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;
import by.inhw.flur.model.movement.Point;
import by.inhw.flur.platform.swing.controller.ControllerListener;
import by.inhw.flur.platform.swing.render.WorldRendererImpl;
import by.inhw.flur.util.LevelLoader;
import by.inhw.flur.util.PropertiesManager;

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

        int[][] map = LevelLoader.loadLevel("src/main/resources/map/empty.map");
        World world = new World(map, renderer);
        world.renderWorld();

        Agent player = world.registerAgent(new Agent("player", "red", 6, 7));
        player.setPosition(new Point(12, 12));
        player.setBrain(BrainFactory.puppetBrain(player));
        ControllerListener keyListener = new ControllerListener(player);
        frame.addKeyListener(keyListener);

        Agent bot1 = world.registerAgent(new Agent("bot1", "blue", 5, 6));
        bot1.setPosition(new Point(4, 6));
        bot1.setBrain(BrainFactory.seekAndArrive(bot1, player));

        // Agent bot2 = world.registerAgent(new Agent("bot2", "blue", 2, 3));
        // bot2.setPosition(new Point(6, 9));
        // bot2.setBrain(BrainFactory.kinematicWander(bot2));
        // Mover.startActing(bot2);
        //
        // Agent bot3 = world.registerAgent(new Agent("bot3", "blue", 1, 5));
        // bot3.setPosition(new Point(10, 10));
        // bot3.setBrain(BrainFactory.kinematicWander(bot3));
        // Mover.startActing(bot3);
        //
        // Agent bot4 = world.registerAgent(new Agent("bot4", "blue", 4, 5));
        // bot4.setPosition(new Point(15, 10));
        // bot4.setBrain(BrainFactory.kinematicSeekAndArrive(bot4, player));
        // Mover.startActing(bot4);

        world.bringWorldToLive();
        frame.pack();
        // Debugger.setWorldRenderer(renderer);
    }
}
