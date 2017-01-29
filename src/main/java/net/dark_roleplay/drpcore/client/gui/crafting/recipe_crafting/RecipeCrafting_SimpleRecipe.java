package net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class RecipeCrafting_SimpleRecipe extends DRPGuiScreen{

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/recipe_crafting_simple.png");
	
	public InventoryPlayer inventory;
	
	public RecipeCrafting_SimpleRecipe() {
		super(bg, 178, 161);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		inventory = Minecraft.getMinecraft().thePlayer.inventory;
		
		this.itemRender.renderItemIntoGUI(inventory.mainInventory.get(0), this.guiLeft + 22 + (26), this.guiTop + 44 + (26));
		
	}

}
