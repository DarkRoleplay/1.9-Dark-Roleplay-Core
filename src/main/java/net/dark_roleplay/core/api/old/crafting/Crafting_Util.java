package net.dark_roleplay.core.api.old.crafting;

import net.dark_roleplay.core.client.build_mode.EntityViewer;
import net.dark_roleplay.core.client.build_mode.GuiBuildingViewer;
import net.dark_roleplay.core.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.core.common.References;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class Crafting_Util {

	private static EntityViewer viewer = null;

	private static boolean isInitialized = false;
	
//	public static void initialize(BlockPos pos, float yaw, float pitch) {
//		if(!isInitialized) {
//			isInitialized = true;
//			if(viewer == null) {
//				viewer = new EntityViewer(Minecraft.getMinecraft().player.world);
//				Minecraft.getMinecraft().player.world.spawnEntity(viewer);
//			}
//
//			viewer.setPosition(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
//			viewer.turn(yaw, pitch);
//			viewer.setInvisible(true);
//			
//			Minecraft.getMinecraft().setRenderViewEntity(viewer);
//		}
//	}
	
	public static void exit() {
		if(isInitialized) {
			viewer.setInvisible(true);
			isInitialized = false;
			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
		}
	}
	
	public static void openRecipeSelection(Block block){
		openRecipeSelection(block, null, 0f, 0f);
	}
	
	public static void openRecipeSelection(Block block, BlockPos pos, float yaw, float pitch){
		if(References.SIDE.isClient()) {
//			if(pos != null)
//				initialize(pos, yaw, pitch);
			Minecraft.getMinecraft().displayGuiScreen(new RecipeSelection(block));
		}
	}
	
}
