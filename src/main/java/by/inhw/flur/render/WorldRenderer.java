package by.inhw.flur.render;

import by.inhw.flur.model.Agent;
import by.inhw.flur.model.World;

public interface WorldRenderer
{
    void setWorld(World world);

    void renderWorld();

    AgentRenderer registerAgentRenderer(Agent agent);
}
