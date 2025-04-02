package org.caden.simulations.Types.Utilities;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class QueuedBlockRegistry<T extends BlockEntity> {
    private final BlockEntityType.BlockEntityFactory<T> blockEntityFactory;
    private final Function<T, IPeripheral> peripheral;
    private final Identifier identifier;
    private final Block block;
    private final BlockItem blockItem;
    private final BlockEntityType<T> blockEntityType;

    // Base constructor for common initialization
    public QueuedBlockRegistry(Identifier identifier, Block block, BlockEntityType.BlockEntityFactory<T> blockEntityFactory, Function<T, IPeripheral> peripheral) {
        this.identifier = identifier;
        this.block = block;
        this.blockEntityFactory = blockEntityFactory;
        this.peripheral = peripheral;
        this.blockItem = new BlockItem(block, new Item.Settings());

        if (blockEntityFactory != null) {
            this.blockEntityType = BlockEntityType.Builder.create(blockEntityFactory, block).build(null);
        } else {
            this.blockEntityType = null;
        }
    }

    // Public convenience constructors chaining to base constructor
    public QueuedBlockRegistry(Identifier identifier, Block block) {
        this(identifier, block, null, null);
    }

    public QueuedBlockRegistry(Identifier identifier, Block block, BlockEntityType.BlockEntityFactory<T> blockEntityFactory) {
        this(identifier, block, blockEntityFactory, null);
    }

    public void register() {
        Registry.register(Registries.BLOCK, identifier, block);
        Registry.register(Registries.ITEM, identifier, blockItem);

        if (blockEntityType != null) {
            Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, blockEntityType);

            if (peripheral != null) {
                PeripheralLookup.get().registerForBlockEntity((blockEntity, direction) -> {
                    if (blockEntity == null) return null; // defensive null check
                    return peripheral.apply(blockEntity);
                }, blockEntityType);
            }
        }
    }

    // Getters if needed
    public Block getBlock() {
        return block;
    }

    public BlockEntityType<T> getBlockEntityType() {
        return blockEntityType;
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
