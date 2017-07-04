package net.dark_roleplay.drpcore.api.skills;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SkillTree {

	private ResourceLocation backgroundImage;
	private ItemStack displayItem;
	private String registryName;
	private String unlocalizedName;
	
	private List<Skill> skills = Lists.newArrayList();
	
	public SkillTree(String registryName, String unlocalizedName, ItemStack displayItem, ResourceLocation backgroundImage){
		this.registryName = registryName;
		this.unlocalizedName = unlocalizedName;
		this.displayItem = displayItem;
		this.backgroundImage = backgroundImage;
	}
	
	public void addSkill(Skill skill){
		this.skills.add(skill);
	}
	
	public ItemStack getDisplayTexture() {
		return displayItem;
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	public ResourceLocation getBackground() {
		return backgroundImage;
	}
}
