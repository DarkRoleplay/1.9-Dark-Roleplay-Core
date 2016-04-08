package net.drpcore.common.events;

import java.util.Random;

import net.drpcore.common.entities.player.ExtendedPlayer;
import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingDrop {

	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event)
	{
		//TODO Player drop on Death
		/*if(event.getEntityLiving() instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			PlayerInventory inventory = ExtendedPlayer.get(player).inventory;
			for(int i = 0; i < inventory.getSizeInventory();i++){
				if(inventory.getStackInSlot(i) != null){
					event.getEntityLiving().worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, inventory.getStackInSlot(i)));
				}
			}
			inventory.clear();
		}*/
	}
	
}
