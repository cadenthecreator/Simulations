package org.caden.simulations.Utilities;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.dimension.DimensionTypes;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

import java.util.UUID;

import static org.caden.simulations.Simulations.fantasy;
import static org.caden.simulations.Simulations.server;

public class SimulationManager {
    public static RuntimeWorldHandle CreateDimension(UUID uuid) {
        while (server == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        RuntimeWorldConfig config = new RuntimeWorldConfig()
                .setDimensionType(DimensionTypes.OVERWORLD)
                .setDifficulty(Difficulty.HARD)
                .setGameRule(GameRules.DO_DAYLIGHT_CYCLE, false)
                .setGenerator(server.getOverworld().getChunkManager().getChunkGenerator())
                .setSeed(server.getOverworld().getSeed());
        return fantasy.getOrOpenPersistentWorld(new Identifier("simulations","simulation-"+uuid.toString()),config);
    }
}
