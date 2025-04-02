package org.caden.simulations;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.caden.simulations.Types.Utilities.QueuedBlockRegistry;
import xyz.nucleoid.fantasy.Fantasy;

public class Simulations implements ModInitializer {
    public static Fantasy fantasy;
    public static volatile MinecraftServer server;
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register((minecraftServer) -> {
            fantasy = Fantasy.get(minecraftServer);
            server = minecraftServer;
        });
        BlockRegistry.register();
    }
}
