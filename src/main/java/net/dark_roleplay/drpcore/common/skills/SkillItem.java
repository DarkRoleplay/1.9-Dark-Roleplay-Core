package net.dark_roleplay.drpcore.common.skills;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class SkillItem {

	private Item displayTexture;
	private String registryName;
	private String unlocalizedName;
	
	private SkillPoint requiredPoint;
	
	private int x, y;
	
	private List<SkillItem> parents = new ArrayList<SkillItem>();
	
	public SkillItem(ResourceLocation registryName, String unlocalizedName, Item displayTexture, SkillPoint requiredPoint, int x, int y){
		this.displayTexture = displayTexture;
		this.registryName = registryName.getResourceDomain() + ":" + registryName.getResourcePath();
		this.unlocalizedName = unlocalizedName;
		this.x = x * 2;
		this.y = y * 2;
	}
	
	public void addParent(SkillItem item){
		parents.add(item);
	}

	public Item getDisplayTexture() {
		return displayTexture;
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public List<SkillItem> getParents() {
		return parents;
	}

	public SkillPoint getRequiredPoint() {
		return requiredPoint;
	}
	
	
}
