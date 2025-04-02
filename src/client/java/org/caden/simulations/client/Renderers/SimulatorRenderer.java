package org.caden.simulations.client.Renderers;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.caden.simulations.Entities.SimulatorEntity;

import java.util.ArrayList;
import java.util.Objects;

public class SimulatorRenderer implements BlockEntityRenderer<SimulatorEntity> {
    private final BlockEntityRendererFactory.Context ctx;
    private final ArrayList<Block> blocks = new ArrayList<>();
    public SimulatorRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }
    private Block getBlock(int indx) {
        if (blocks.isEmpty()) {
            for (Block block : Registries.BLOCK) {
                if (block.getDefaultState().isOpaqueFullCube(MinecraftClient.getInstance().world,BlockPos.ORIGIN)) {
                    blocks.add(block);
                }
            }
        }
        return blocks.get(Math.abs(indx%blocks.size()));
    }

    @Override
    public void render(SimulatorEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) (((double) (entity.time+entity.getPos().asLong()) /50+ (double) entity.getPos().asLong() /3)%(Math.PI*2))));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((float) (((double) (entity.time+entity.getPos().asLong()) /50+ (double) entity.getPos().asLong())%(Math.PI*2))));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float) (((double) (entity.time+entity.getPos().asLong()) /50+ (double) entity.getPos().asLong() /1.5)%(Math.PI*2))));
        matrices.scale(0.45f,0.45f,0.45f);
        matrices.translate(-0.5, -0.5, -0.5);
        ctx.getRenderManager().renderBlock(getBlock((int) ((entity.time+entity.getPos().asLong())/20)).getDefaultState(),entity.getPos().withY(319),entity.getWorld(),matrices,vertexConsumers.getBuffer(RenderLayer.getSolid()),false, Objects.requireNonNull(entity.getWorld()).random);
        matrices.pop();
        entity.time += 1;
        entity.time %= Integer.MAX_VALUE;
    }
}
