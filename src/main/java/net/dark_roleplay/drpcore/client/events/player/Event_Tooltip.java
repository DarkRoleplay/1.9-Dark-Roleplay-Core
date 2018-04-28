package net.dark_roleplay.drpcore.client.events.player;

import net.dark_roleplay.drpcore.api.old.util.lore.LoreUtil;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = References.MODID)
public class Event_Tooltip {
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onItemTooltipEvent(ItemTooltipEvent event) {
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
