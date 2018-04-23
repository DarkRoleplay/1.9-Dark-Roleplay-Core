package net.dark_roleplay.drpcore.client.events.player;

import net.dark_roleplay.drpcore.api.old.util.lore.LoreUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_Tooltip {
	
	@SubscribeEvent
	public void onItemTooltipEvent(ItemTooltipEvent event) {
	    ItemStack itemStack = event.getItemStack();

		if(!GuiScreen.isCtrlKeyDown())
			return;
	    event.getToolTip().add("                             ");
	    event.getToolTip().add("                             ");
	    event.getToolTip().add("                             ");
	    event.getToolTip().add("                             ");
	    event.getToolTip().add("                             ");
	    event.getToolTip().add("                             ");
	}
}
