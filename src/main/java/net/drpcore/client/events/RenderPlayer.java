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

		if(event.getEntityPlayer().inventory.armorInventory[0] != null) head = event.getEntityPlayer().inventory.armorInventory[0].copy();
		if(event.getEntityPlayer().inventory.armorInventory[1] != null) chest = event.getEntityPlayer().inventory.armorInventory[1].copy();
		if(event.getEntityPlayer().inventory.armorInventory[2] != null) legs = event.getEntityPlayer().inventory.armorInventory[2].copy();
		if(event.getEntityPlayer().inventory.armorInventory[3] != null) feet = event.getEntityPlayer().inventory.armorInventory[3].copy();

		ItemStack[] armor = event.getEntityPlayer().inventory.armorInventory;
		ItemStack[] cosmetic = new ItemStack[] {inventory.getStackInSlot(7), inventory.getStackInSlot(8), inventory.getStackInSlot(9), inventory.getStackInSlot(10)};

		for(int i = 0; i < 4; i++){
			if(cosmetic[3 - i] != null) armor[i] = cosmetic[3 - i];
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void handleCanceledEvent(RenderPlayerEvent.Post event) {

		ItemStack[] armor = event.getEntityPlayer().inventory.armorInventory;
		if(head != null && head.stackSize > 0)
			armor[0] = head;
		else
			armor[0] = null;
		if(chest != null && chest.stackSize > 0)
			armor[1] = chest;
		else
			armor[1] = null;
		if(legs != null && legs.stackSize > 0)
			armor[2] = legs;
		else
			armor[2] = null;
		if(feet != null && feet.stackSize > 0)
			armor[3] = feet;
		else
			armor[3] = null;
	}

}
