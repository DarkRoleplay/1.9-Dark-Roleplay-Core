package net.dark_roleplay.core.testing.crafting.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;

public class ItemSelection extends GuiScreen{

	List<Item> items;
	GuiScreen parent;
	
	public ItemSelection(){
		this(null, null);
	}
	
	public ItemSelection(GuiScreen parent){
		this(null, parent);
	}
	
	public ItemSelection(List<Item> items){
		this(items, null);
	}
	
	public ItemSelection(List<Item> items, GuiScreen parent){
		this.parent = parent;
		if(items == null){
			this.items = new ArrayList<Item>();
			Item.REGISTRY.forEach(item -> {
				ItemSelection.this.items.add(item);
			});
		}else{
			this.items = items;
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		if(this.parent != null) this.parent.drawScreen(-1, -1, partialTicks);
		
		this.drawGradientRect(0, 0, 100, 100, 0xFF00FF00, 0xFFFF00FF);
    }
}
