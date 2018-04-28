package net.dark_roleplay.drpcore.client.build_mode;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class BuildingViewerHelper {

	private static EntityBuildingViewer viewer = null;

	private static boolean isInitialized = false;
	
	public static void initialize(BlockPos pos) {
		if(!isInitialized) {
			isInitialized = true;
			if(viewer == null) {
				viewer = new EntityBuildingViewer(Minecraft.getMinecraft().player.world);
				Minecraft.getMinecraft().player.world.spawnEntity(viewer);
				viewer.setPosition(pos.getX(), pos.getY(), pos.getZ());				
			}
			Minecraft.getMinecraft().setRenderViewEntity(viewer);
			Minecraft.getMinecraft().displayGuiScreen(new GuiBuildingViewer(viewer));
		}
	}
	
	public static void exit() {
		if(isInitialized) {
			viewer.setDead();
			viewer = null;
			isInitialized = false;
			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
		}
	}
}
