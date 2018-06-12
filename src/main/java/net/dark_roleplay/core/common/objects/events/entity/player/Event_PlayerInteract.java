package net.dark_roleplay.core.common.objects.events.entity.player;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.core.api.old.modules.locks.ILock;
import net.dark_roleplay.core.api.old.modules.locks.ILockHandler;
import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

@EventBusSubscriber(modid = References.MODID)
public class Event_PlayerInteract {

	@SubscribeEvent
	public static void onInteractBlock(PlayerInteractEvent.RightClickBlock event){
		Chunk chunk = event.getWorld().getChunkFromBlockCoords(event.getPos());
		ILockHandler handler = chunk.getCapability(DRPCoreCapabilities.LOCK_HANDLER, null);

		EntityPlayer player = event.getEntityPlayer();
		if(handler.isLocked(event.getPos())){
			ItemStack heldItem = player.getHeldItemMainhand();
			if(heldItem.getItem() instanceof ILock && ((ILock)heldItem.getItem()).getType(heldItem) == ILock.TYPE.KEY){
				if(handler.canOpen(event.getPos(), heldItem)){
					event.setCanceled(true);
					player.sendStatusMessage(new TextComponentTranslation("dprcore.locks.removed", event.getWorld().getBlockState(event.getPos()).getBlock().getLocalizedName()), true);
					ItemHandlerHelper.giveItemToPlayer(player, handler.extractLock(event.getPos()));
					return;
				}
			}
			for(ItemStack stack : getKeysInInventory(player)){
				if(handler.canOpen(event.getPos(), stack)){
					return;
				}
			}
			event.setCanceled(true);
			player.sendStatusMessage(new TextComponentTranslation("dprcore.locks.locked", event.getWorld().getBlockState(event.getPos()).getBlock().getLocalizedName()), true);
		}else{
			ItemStack heldItem = player.getHeldItemMainhand();
			if(heldItem.getItem() instanceof ILock){
				ILock lock = (ILock) heldItem.getItem();
				if(lock.getType(heldItem) == ILock.TYPE.LOCK){
					event.setCanceled(true);
					player.sendStatusMessage(new TextComponentTranslation("dprcore.locks.applied", event.getWorld().getBlockState(event.getPos()).getBlock().getLocalizedName()), true);
					player.setHeldItem(EnumHand.MAIN_HAND, handler.insertLock(event.getPos(), heldItem));
				}
			}
		}
	}
	
	public static ItemStack[] getKeysInInventory(EntityPlayer player){
		List<ItemStack> keys = new ArrayList<ItemStack>();
		
		NonNullList<ItemStack> inventory = player.inventory.mainInventory;
		for(int i = 0; i < inventory.size(); i++){
			if(inventory.get(i).getItem() instanceof ILock && ((ILock)inventory.get(i).getItem()).getType(inventory.get(i)) == ILock.TYPE.KEY){
				keys.add(inventory.get(i));
			}
		}
		return keys.toArray(new ItemStack[keys.size()]);
	}
}
