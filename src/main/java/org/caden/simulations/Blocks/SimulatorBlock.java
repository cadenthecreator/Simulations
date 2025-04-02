package org.caden.simulations.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.caden.simulations.Entities.SimulatorEntity;
import org.caden.simulations.Simulations;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.fantasy.Fantasy;

import java.util.UUID;

public class SimulatorBlock extends BlockWithEntity {
    public SimulatorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SimulatorEntity(pos,state, UUID.randomUUID());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
