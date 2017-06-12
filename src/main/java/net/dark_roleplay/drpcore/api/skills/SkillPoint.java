package net.dark_roleplay.drpcore.api.skills;

import org.lwjgl.util.Color;

import net.minecraft.item.ItemStack;

public class SkillPoint {

	/** Thats the maximum Level of this Skill *
	 * -1 for infinity **/
	private int maxLevel;
	
	/** INTERN STUFF **/
	/** Intern Name for handling this skill, (don't change it or players will loose their skill level) **/
	private String registryName;

	/** DISPLAY STUFF **/
	/** Unlocalized Display name **/
	private String unlocalizedName;
	
	/** ItemStack to use for display **/
	private ItemStack displayStack;
	
	/** Used as text Color for places where displayStack can't be used **/
	private Color displayColor;

	
	public SkillPoint(String registryName, String unlocalizedName, int maxLevel, ItemStack displayStack){
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
		this.maxLevel = maxLevel;
		this.displayStack = displayStack;
	}
	
	public int getRequiredXP(int level){
		return level * 100;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public ItemStack getDisplayStack() {
		return displayStack;
	}
	
	
}
