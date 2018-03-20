package net.dark_roleplay.drpcore.api.old.crafting;

import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

public class Crafting_Util {

	public static void openRecipeSelection(Block block){
		if(References.SIDE.isClient())
			Minecraft.getMinecraft().displayGuiScreen(new RecipeSelection(block));
	}
	
}
