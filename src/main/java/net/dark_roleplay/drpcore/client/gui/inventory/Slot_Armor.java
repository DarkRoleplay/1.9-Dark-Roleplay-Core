package net.dark_roleplay.drpcore.client.gui.inventory;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Slot_Armor extends Slot{
	
	private EntityEquipmentSlot entityequipmentslot;
	private EntityPlayer player;

    public Slot_Armor(IInventory inventoryIn, int index, int xPosition, int yPosition, EntityEquipmentSlot entityequipmentslot, EntityPlayer player) {
		super(inventoryIn, index, xPosition, yPosition);
		this.entityequipmentslot = entityequipmentslot;
		this.player = player;
	}
    
    @Override
	public int getSlotStackLimit(){
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack){
        return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn){
        ItemStack itemstack = this.getStack();
        return !itemstack.isEmpty() && (!playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn));
    }
    
    @Nullable
    @SideOnly(Side.CLIENT)
    @Override
    public String getSlotTexture(){
        return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
    }
}
