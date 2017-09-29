package net.dark_roleplay.drpcore.api.skills;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SkillTree extends IForgeRegistryEntry.Impl<SkillTree>{
	
	private List<Skill> skills;
	
	private String unlocalizedName;
	
	public SkillTree(ResourceLocation registryName){
		this(registryName, registryName.getResourcePath());
	}
	
	public SkillTree(ResourceLocation registryName, String unlocalizedName){
		this.setRegistryName(registryName);
		this.unlocalizedName = "skill.tree." + unlocalizedName + ".name";
		this.skills = new ArrayList<Skill>();
	}

	public void addSkill(Skill skill){
		this.skills.add(skill);
	}
	
	public String getUnlocalizedName(){
		return this.unlocalizedName;
	}
	
	public List<Skill> getSkills(){
		return this.skills;
	}

}
