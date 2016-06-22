package net.drpcore.client.events;

import java.util.HashMap;
import java.util.UUID;
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

	public HashMap<UUID, ItemStack> head = new HashMap<UUID, ItemStack>();

	public HashMap<UUID, ItemStack> chest = new HashMap<UUID, ItemStack>();

	public HashMap<UUID, ItemStack> legs = new HashMap<UUID, ItemStack>();

	public HashMap<UUID, ItemStack> feet = new HashMap<UUID, ItemStack>();

	public ItemStack head2 = null;

	public ItemStack chest2 = null;

	public ItemStack legs2 = null;

	public ItemStack feet2 = null;

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void handleEvent(RenderPlayerEvent.Pre event) {

		UUID id = event.getEntityPlayer().getGameProfile().getId();
		AdvancedPlayerInventory inventory = event.getEntityPlayer().getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
		if(event.getEntityPlayer().inventory.armorInventory[0] != null)
			head.put(id, event.getEntityPlayer().inventory.armorInventory[0].copy());
		if(event.getEntityPlayer().inventory.armorInventory[1] != null)
			chest.put(id, event.getEntityPlayer().inventory.armorInventory[1].copy());
		if(event.getEntityPlayer().inventory.armorInventory[2] != null)
			legs.put(id, event.getEntityPlayer().inventory.armorInventory[2].copy());
		if(event.getEntityPlayer().inventory.armorInventory[3] != null)
			feet.put(id, event.getEntityPlayer().inventory.armorInventory[3].copy());
		ItemStack[] armor = event.getEntityPlayer().inventory.armorInventory;
		ItemStack[] cosmetic = new ItemStack[] {inventory.getStackInSlot(7), inventory.getStackInSlot(8), inventory.getStackInSlot(9), inventory.getStackInSlot(10)};
		for(int i = 0; i < 4; i++ ) {
			if(cosmetic[3 - i] != null)
				armor[i] = cosmetic[3 - i];
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void handleCanceledEvent(RenderPlayerEvent.Post event) {

		UUID id = event.getEntityPlayer().getGameProfile().getId();
		ItemStack[] armor = event.getEntityPlayer().inventory.armorInventory;
		if(head.containsKey(id) && head.get(id).stackSize > 0)
			armor[0] = head.get(id);
		else
			armor[0] = null;
		if(chest.containsKey(id) && chest.get(id).stackSize > 0)
			armor[1] = chest.get(id);
		else
			armor[1] = null;
		if(legs.containsKey(id) && legs.get(id).stackSize > 0)
			armor[2] = legs.get(id);
		else
			armor[2] = null;
		if(feet.containsKey(id) && feet.get(id).stackSize > 0)
			armor[3] = feet.get(id);
		else
			armor[3] = null;
		head.remove(id);
		chest.remove(id);
		legs.remove(id);
		feet.remove(id);
	}
}
