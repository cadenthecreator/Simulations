package org.cadencoaster.simulations.peripherals;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import org.cadencoaster.simulations.Simulations;
import org.cadencoaster.simulations.peripherals.simulator.SimulatorBlockEntity;
import org.cadencoaster.simulations.peripherals.simulator.SimulatorPeripheral;

public class PeripheralRegistration {
    public static void register() {
        PeripheralLookup.get().registerForBlockEntity(
                (blockEntity, direction) -> new SimulatorPeripheral((SimulatorBlockEntity) blockEntity),
                Simulations.SIMULATOR_BLOCK_ENTITY
        );
    }
}