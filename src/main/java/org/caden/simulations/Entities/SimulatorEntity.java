package org.caden.simulations.Entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import org.caden.simulations.BlockRegistry;
import org.caden.simulations.Utilities.SimulationManager;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

import java.util.UUID;

public class SimulatorEntity extends BlockEntity {
    public int time = 0;
    public UUID uuid;
    public RuntimeWorldHandle worldHandle;
    public SimulatorEntity(BlockPos pos, BlockState state, UUID uuid) {
        super(BlockRegistry.SIMULATOR_BLOCK.getBlockEntityType(), pos, state);
        this.uuid = uuid;
        worldHandle = SimulationManager.CreateDimension(uuid);
    }

    public SimulatorEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.SIMULATOR_BLOCK.getBlockEntityType(), pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putUuid("uuid",uuid);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        uuid = nbt.getUuid("uuid");
        worldHandle = SimulationManager.CreateDimension(uuid);
        super.readNbt(nbt);
    }
}
