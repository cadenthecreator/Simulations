package org.caden.simulations;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.caden.simulations.Blocks.SimulatorBlock;
import org.caden.simulations.Entities.SimulatorEntity;
import org.caden.simulations.Peripherals.SimulatorPeripheral;
import org.caden.simulations.Types.Utilities.QueuedBlockRegistry;

public class BlockRegistry {;
    public static final QueuedBlockRegistry<SimulatorEntity> SIMULATOR_BLOCK = new QueuedBlockRegistry<>(Identifier.of("simulations", "simulator"), new SimulatorBlock(Block.Settings.create().nonOpaque().strength(4.0f)), SimulatorEntity::new, (f) -> new SimulatorPeripheral((SimulatorEntity) f));
    public static void register() {
        SIMULATOR_BLOCK.register();
    }
}
