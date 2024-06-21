package org.cadencoaster.simulations.peripherals.simulator;

import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.cadencoaster.simulations.Simulations;

import java.util.HashSet;

public class SimulatorBlockEntity extends BlockEntity {
    private int SimID = -1;
    public NbtCompound nbt = createNbt();
    public HashSet<IComputerAccess> Computers = new HashSet<>();
    public SimulatorPeripheral Peripheral = new SimulatorPeripheral(this);

    public SimulatorBlockEntity(BlockPos pos, BlockState state) {
        super(Simulations.SIMULATOR_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state1, SimulatorBlockEntity be) {

    }

    public void setSimID(int ID) {
        SimID = ID;
        writeNbt(nbt);
    }

    public int getSimID() {
        readNbt(nbt);
        return SimID;
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        // Save the current value of the number to the nbt
        nbt.putInt("SimID", SimID);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        SimID = nbt.getInt("SimID");
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void attach(IComputerAccess computer) {

    }

    public void detach(IComputerAccess computer) {

    }

//    public Object getTarget() {
//
//    }
}
