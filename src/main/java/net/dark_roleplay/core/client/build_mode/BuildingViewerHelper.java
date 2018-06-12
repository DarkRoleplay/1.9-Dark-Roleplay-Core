package net.dark_roleplay.core.client.build_mode;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BuildingViewerHelper {

	private static EntityViewer viewer = null;

	private static boolean isInitialized = false;
	
	public static void initialize(BlockPos pos, TileEntity te) {
		if(!isInitialized) {
			isInitialized = true;
			if(viewer == null) {
				viewer = new EntityViewer(Minecraft.getMinecraft().player.world);
				Minecraft.getMinecraft().player.world.spawnEntity(viewer);
			}

			viewer.setPosition(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
			viewer.turn(0f, 0f);
			viewer.setInvisible(true);
			
			Minecraft.getMinecraft().setRenderViewEntity(viewer);
			Minecraft.getMinecraft().displayGuiScreen(new GuiBuildingViewer(viewer, te));
		}
	}
	
	public static void exit() {
		if(isInitialized) {
			viewer.setInvisible(true);
			isInitialized = false;
			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player);
		}
	}
}
