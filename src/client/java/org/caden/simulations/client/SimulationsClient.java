package org.caden.simulations.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.caden.simulations.BlockRegistry;
import org.caden.simulations.client.Renderers.SimulatorRenderer;

public class SimulationsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(BlockRegistry.SIMULATOR_BLOCK.getBlockEntityType(), SimulatorRenderer::new);
    }
}
