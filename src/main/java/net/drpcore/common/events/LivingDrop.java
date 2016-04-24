package net.drpcore.common.events;

import java.util.Random;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingDrop {

	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event) {

		if(event.getEntityLiving() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
			for(int i = 0; i < inventory.getSizeInventory(); i++){
				if(inventory.getStackInSlot(i) != null){
					event.getEntityLiving().worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, inventory.getStackInSlot(i)));
				}
			}
			inventory.clear();
		}
	}

}
