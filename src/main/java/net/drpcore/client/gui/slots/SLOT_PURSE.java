package net.drpcore.client.gui.slots;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.items.templates.PurseBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SLOT_PURSE extends Slot{
	
	public SLOT_PURSE(IInventory inventory, int par2, int par3, int par4){
		super(inventory, par2, par3, par4);
		this.setBackgroundLocation(new ResourceLocation(DarkRoleplayCore.MODID, "items/empty_slot_purse"));
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack){
		return itemstack.getItem() instanceof PurseBase;
	}

}