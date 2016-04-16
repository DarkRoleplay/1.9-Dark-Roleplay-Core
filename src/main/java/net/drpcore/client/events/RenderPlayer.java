package net.drpcore.client.events;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayer {

	public static boolean isCosmeticEnabled = true;
	
	public ItemStack head = null;
	public ItemStack chest = null;
	public ItemStack legs = null;
	public ItemStack feet = null;
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void handleEvent(RenderPlayerEvent.Pre event) {
			AdvancedPlayerInventory inventory = event.getEntityPlayer().getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
			
			if(event.getEntityPlayer().inventory.armorInventory[0] != null)
				head = event.getEntityPlayer().inventory.armorInventory[0].copy();
			if(event.getEntityPlayer().inventory.armorInventory[1] != null)
				chest = event.getEntityPlayer().inventory.armorInventory[1].copy();
			if(event.getEntityPlayer().inventory.armorInventory[2] != null)
				legs = event.getEntityPlayer().inventory.armorInventory[2].copy();
			if(event.getEntityPlayer().inventory.armorInventory[3] != null)
				feet = event.getEntityPlayer().inventory.armorInventory[3].copy();
			
			ItemStack[] armor = event.getEntityPlayer().inventory.armorInventory;
			ItemStack[] cosmetic = new ItemStack[]{inventory.getStackInSlot(7),inventory.getStackInSlot(8),inventory.getStackInSlot(9),inventory.getStackInSlot(10)};
		
			for(int i = 0; i < 4; i++){
				if(cosmetic[3 - i] != null)
					armor[i] = cosmetic[3-i];
			}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void handleCanceledEvent(RenderPlayerEvent.Post event) {
		ItemStack[] armor = event.getEntityPlayer().inventory.armorInventory;
		armor[0] = head;
		armor[1] = chest;
		armor[2] = legs;
		armor[3] = feet;
	}

}
