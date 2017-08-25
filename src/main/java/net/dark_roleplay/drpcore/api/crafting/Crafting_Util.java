package net.dark_roleplay.drpcore.api.crafting;

import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

public class Crafting_Util {

	public static void openRecipeSelection(Block block){
		if(DRPCoreInfo.SIDE.isClient())
			Minecraft.getMinecraft().displayGuiScreen(new RecipeSelection(block));
	}
	
}
