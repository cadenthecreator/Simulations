package org.caden.simulations.Peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.block.Blocks;
import org.caden.simulations.Entities.SimulatorEntity;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class SimulatorPeripheral implements IPeripheral {

    private final SimulatorEntity simulator;

    public SimulatorPeripheral(SimulatorEntity simulator) {
        this.simulator = simulator;
    }

    @Override
    public String getType() {
        return "simulator";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof SimulatorPeripheral o && simulator == o.simulator;
    }

    @LuaFunction
    public void test() {
        Objects.requireNonNull(this.simulator.getWorld()).setBlockState(this.simulator.getPos(),Blocks.AIR.getDefaultState());
    }

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
    }
}
