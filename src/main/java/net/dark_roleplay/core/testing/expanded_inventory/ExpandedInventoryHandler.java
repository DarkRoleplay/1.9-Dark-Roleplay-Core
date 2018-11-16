package net.dark_roleplay.core.testing.expanded_inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ExpandedInventoryHandler extends ItemStackHandler implements IExpandedInventory{

    public ExpandedInventoryHandler() {
        this(1);
    }

    public ExpandedInventoryHandler(int size) {
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public ExpandedInventoryHandler(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }
}
