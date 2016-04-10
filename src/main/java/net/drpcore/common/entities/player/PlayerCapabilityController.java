package net.drpcore.common.entities.player;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.IPlayerInventoryAdvanced;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.PlayerCapabilityProvider;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerCapabilityController {

	@SubscribeEvent
    public void onInteract(PlayerInteractEvent event)
    {
        if (event.getEntityPlayer().getHeldItemMainhand() == null) return;
        if (event.getEntityPlayer().getHeldItemMainhand().getItem() != Items.stick) return;

        // This is just a example of how to interact with the TE, note the strong type binding that getCapability has
        Entity entity = event.getEntity();
        if (entity != null && entity.hasCapability(DarkRoleplayCore.DRPCORE_INV, event.getFace()))
        {
            IPlayerInventoryAdvanced inv = entity.getCapability(DarkRoleplayCore.DRPCORE_INV, event.getFace());
            
            AdvancedPlayerInventory inventory = event.getEntity().getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
            inventory.setInventorySlotContents(1, new ItemStack(Items.stone_axe));
        }
    }
	
}
