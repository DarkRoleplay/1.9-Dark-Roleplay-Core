package net.dark_roleplay.drpcore.common.skills;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class SkillTree {

	private ResourceLocation background;
	private String registryName;
	private String unlocalizedName;

	private List<SkillItem> skills = new ArrayList<SkillItem>();
	
	public SkillTree(String registryName, String unlocalizedName, ResourceLocation background){
		this.background = background;
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
	}
	
	public void addSkill(SkillItem item){
		this.skills.add(item);
	}
	
	public List<SkillItem> getSkills(){
		return skills;
	}

	public ResourceLocation getBackground() {
		return background;
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	
}
