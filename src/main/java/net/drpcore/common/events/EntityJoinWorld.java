package net.drpcore.common.events;

import java.util.List;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.PacketOpenPurse;
import net.drpcore.common.network.PacketSyncAdvancedInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EntityJoinWorld {

	/*@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {

		if(!event.getWorld().isRemote){
			if(event.getEntity() instanceof EntityPlayer){

				if(event.getEntity().hasCapability(DarkRoleplayCore.DRPCORE_INV, null)){

					AdvancedPlayerInventory inventory = event.getEntity().getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();

					for(int i = 0; i < inventory.getSizeInventory(); i++){
						PacketHandler.sendTo(new PacketSyncAdvancedInventory(i, (EntityPlayer) event.getEntity()), (EntityPlayerMP) event.getEntity());
					}
				}
			}
		}
	}*/

	@SubscribeEvent
	public void handleEvent(PlayerLoggedInEvent event) {
		
		if(event.player instanceof EntityPlayerMP){
			AdvancedPlayerInventory inventory = event.player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
			inventory.setInventorySlotContents(8, new ItemStack(Items.diamond_chestplate));

			for(int i = 0; i < inventory.getSizeInventory(); i++)
				PacketHandler.sendTo(new PacketSyncAdvancedInventory(i,event.player), (EntityPlayerMP) event.player);

			for(EntityPlayerMP other : (List<EntityPlayerMP>) FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList()){
				if(other == event.player) continue;
				inventory = other.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();// getCosArmorInventory(other.getUniqueID());
				for(int i = 0; i < inventory.getSizeInventory(); i++)
					PacketHandler.sendTo(new PacketSyncAdvancedInventory(i,other), (EntityPlayerMP) event.player);
				for(int i = 0; i < inventory.getSizeInventory(); i++)
					PacketHandler.sendTo(new PacketSyncAdvancedInventory(i,event.player), other);
			}
		}
	}
}
