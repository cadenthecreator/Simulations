package org.cadencoaster.simulations;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.cadencoaster.simulations.peripherals.PeripheralRegistration;
import org.cadencoaster.simulations.peripherals.simulator.SimulatorBlock;
import org.cadencoaster.simulations.peripherals.simulator.SimulatorBlockEntity;

import java.util.OptionalLong;

public class Simulations implements ModInitializer {

    public static final String MOD_ID = "simulations";

    public static final Identifier DIMENSION_TYPE_ID = new Identifier(MOD_ID,"simulation");
    public static final DimensionType DIMENSION_TYPE = new DimensionType(OptionalLong.empty(),true,true,false,true,1.0,true,false);

    private static int SimulationCount = 0;

    public static int getSimulations() {
        return SimulationCount;
    }

    public static void incSimulations() {
       SimulationCount += 1;
    }

    public static void decSimulations() {
        SimulationCount -= 1;
    }

    public static Identifier makeID(String name) {
        return new Identifier(MOD_ID, name);
    }

    public static final Block SIMULATOR_BLOCK = new SimulatorBlock(FabricBlockSettings.create().strength(4.0f));

    public static final BlockEntityType<SimulatorBlockEntity> SIMULATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            makeID("simulator"),
            BlockEntityType.Builder.create(SimulatorBlockEntity::new, SIMULATOR_BLOCK).build(null)
    );

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, makeID("simulator"), SIMULATOR_BLOCK);
        Registry.register(Registries.ITEM, makeID("simulator"), new BlockItem(SIMULATOR_BLOCK, new FabricItemSettings()));
        PeripheralRegistration.register();
    }

    public ServerWorld createCustomDimension(MinecraftServer server, Biome biome, ChunkGeneratorSettings settings) {
        Identifier dimensionId = new Identifier("mymod", "custom_dimension_" + System.currentTimeMillis());

        DimensionType dimensionType = new DimensionType(
                OptionalLong.empty(), // No fixed time
                true,                 // Has skylight
                false,                // No ceiling
                true,                 // Ultra warm
                true,                 // Natural
                1.0,                  // Coordinate scale
                false,                // Piglin safe
                true,                // Bed works
                false,                // Respawn anchor works
                false,                // Has raiders
                256,                  // Logical height
                new Identifier("minecraft", "overworld"), // Infinite burn
                DimensionTypes.OVERWORLD.effects(), // Effects
                0.0f, // Ambient light
                new DimensionType.MonsterSettings(false, false,), // Monster settings
                new Identifier("minecraft", "overworld") // Identifier
        );

        DimensionOptions options = new DimensionOptions(
                () -> dimensionType,
                new NoiseChunkGenerator(
                        new FixedBiomeSource(biome),
                        settings
                )
        );

        RegistryKey<ServerWorld> dimensionKey = RegistryKey.of(Registry.DIMENSION, dimensionId);

        return FabricDimensions.create(server, dimensionKey, options);
    }
}

}
