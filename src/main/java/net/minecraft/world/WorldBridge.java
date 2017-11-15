package net.minecraft.world;

public class WorldBridge {

	public static void setUpdateLCG(World world, int newUpdateLCG){
		world.updateLCG = newUpdateLCG;
	}
	
	public static int getUpdateLCG(World world){
		return world.updateLCG;
	}
	
}
