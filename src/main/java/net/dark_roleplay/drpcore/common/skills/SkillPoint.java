package net.dark_roleplay.drpcore.common.skills;

import net.minecraft.util.ResourceLocation;

public class SkillPoint {

	private String registryName;
	
	private boolean hasSkillLevel = false;
	
	private int maxLevel = 5;
	
	public SkillPoint(ResourceLocation registryName){
		this.registryName = registryName.getResourceDomain() + ":" + registryName.getResourcePath();
	}

	public String getRegistryName() {
		return registryName;
	}

	public int getMaxLevel() {
		return maxLevel;
	}
	
	
	
}
