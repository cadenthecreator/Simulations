package org.caden.simulations.Peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.caden.simulations.Entities.SimulatorEntity;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    private Map<String, Object> serializeBlockState(@NotNull BlockState state) {
        Map<String, Object> out = new HashMap<>();
        out.put("name",state.getBlock().getName().toString());
        out.put("id",Registries.BLOCK.getId(state.getBlock()).toString());
        for (Property<?> property : state.getProperties()) {
            Comparable<?> value = state.get(property);

            // Add value based on type
            if (value instanceof Boolean) {
                out.put(property.getName(), (Boolean) value);
            } else if (value instanceof Integer) {
                out.put(property.getName(), (Integer) value);
            } else if (value instanceof Float) {
                out.put(property.getName(), (Float) value);
            } else if (value instanceof Double) {
                out.put(property.getName(), (Double) value);
            } else {
                out.put(property.getName(), value.toString()); // fallback for enums like Direction
            }
        }
        return out;
    }

    @LuaFunction
    public final MethodResult getBlock(int x,int y,int z) {
        BlockState blockState = simulator.worldHandle.asWorld().getBlockState(new BlockPos(x,y,z));
        return MethodResult.of(serializeBlockState(blockState));
    }

    @LuaFunction
    public final MethodResult setBlock(int x,int y,int z, String block) {
        if (Identifier.tryParse(block) != null) {
            Optional<Block> newblock = Registries.BLOCK.getOrEmpty(Identifier.tryParse(block));
            if (newblock.isPresent()) {
                simulator.worldHandle.asWorld().setBlockState(new BlockPos(x, y, z), newblock.get().getDefaultState());
                return MethodResult.of(true);
            }
        }
        return MethodResult.of(false);
    }

    @LuaFunction
    public final MethodResult setBlocks(int x1,int y1,int z1,int x2,int y2,int z2, String block) {
        if (Identifier.tryParse(block) != null) {
            Optional<Block> newblock = Registries.BLOCK.getOrEmpty(Identifier.tryParse(block));
            if (newblock.isPresent()) {
                for (int x = x1; x <= x2; x++) {
                    for (int y = y1; y <= y2; y++) {
                        for (int z = z1; z <= z2; z++) {
                            simulator.worldHandle.asWorld().setBlockState(new BlockPos(x, y, z), newblock.get().getDefaultState());
                        }
                    }
                }
                return MethodResult.of(true);
            }
        }
        return MethodResult.of(false);
    }

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
    }
}
