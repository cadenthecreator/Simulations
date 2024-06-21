package org.cadencoaster.simulations.peripherals.simulator;

import dan200.computercraft.api.lua.*;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimulatorPeripheral implements IPeripheral {
    private final SimulatorBlockEntity BlEnt;

    public SimulatorPeripheral(SimulatorBlockEntity inbe) {
        this.BlEnt = inbe;
    }

    @Override
    public String getType() {
        return "Simulator";
    }

    @Override
    public Set<String> getAdditionalTypes() {
        return IPeripheral.super.getAdditionalTypes();
    }

    @Override
    public void attach(IComputerAccess computer) {
        BlEnt.Computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        BlEnt.Computers.remove(computer);
    }

    @Nullable
    @Override
    public Object getTarget() {
        return IPeripheral.super.getTarget();
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return this == other;
    }

    @LuaFunction
    final public int startSim(IArguments args) throws LuaException {
        Map<?,?> params = args.getTable(0);
        if (params.get("generation").getClass() == HashMap.class) {
            return 1;
        }
        return -1;
    }
}