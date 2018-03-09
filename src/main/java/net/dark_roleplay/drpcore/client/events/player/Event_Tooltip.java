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
	    
	    if(LoreUtil.hasPERM(event.getItemStack().getItem())){
	    	event.getToolTip().addAll(LoreUtil.getPERM(event.getItemStack().getItem()));
	    }
	    
	    if(LoreUtil.hasCTRL(event.getItemStack().getItem())){
    		if(GuiScreen.isCtrlKeyDown()){
    	    	event.getToolTip().addAll(LoreUtil.getCTRL(event.getItemStack().getItem()));
    	    }else{
    	    	event.getToolTip().add("Press CTRL for Description");
    	    }
    	} 
	    
    	if(LoreUtil.hasALT(event.getItemStack().getItem())){
    		if(GuiScreen.isAltKeyDown()){
    			event.getToolTip().addAll(LoreUtil.getALT(event.getItemStack().getItem()));
    	    }else{
    	    	event.getToolTip().add("Press ALT for Description");
    	    }
    	}
    	
    	if(LoreUtil.hasSHIFT(event.getItemStack().getItem())){
    		if(GuiScreen.isShiftKeyDown()){
    			event.getToolTip().addAll(LoreUtil.getSHIFT(event.getItemStack().getItem()));
    	    }else{
    	    	event.getToolTip().add("Press SHIFT for Description");
    	    }
    	}

	}
}
