package net.dark_roleplay.drpcore.testing.skills;

import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;

public class SkillHolder {

	private Skill skill = null;
	private int level = 0;
	private int experience = 0;
	
	public SkillHolder(Skill skill) {
		this(skill, 0, 0);
	}
	
	public SkillHolder(Skill skill, int level) {
		this(skill, level, 0);
	}
	
	public SkillHolder(Skill skill, int level, int experience) {
		this.skill = skill;
		this.level = level;
		this.experience = experience;
	}
	
	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public NBTTagCompound serialize() {
		return this.serialize(null);
	}
	
	public NBTTagCompound serialize(NBTTagCompound comp) {
		if(comp == null) {
			comp = new NBTTagCompound();
		}
		comp.setString("skill", this.skill.getRegistryName().toString());
		if(this.level != 1)
			comp.setInteger("level", this.level);
		if(this.experience != 0)
			comp.setInteger("experience", this.experience);
		
		return comp;
	}
	
	public static SkillHolder deserialize(NBTTagCompound comp) {
		if(comp.hasKey("skill")) {
			String name = comp.getString("skill");
			ResourceLocation skillName = new ResourceLocation(name);
			Skill skill = SkillRegistries.SKILL_POINTS.containsKey(skillName) ? SkillRegistries.SKILLS.getValue(skillName) : null;
			if(skill == null)
				throw new InvalidParameterException("Skill [" + name + "] couldn't be found!");
			return new SkillHolder(skill, comp.hasKey("level") ? comp.getInteger("level") : 0, comp.hasKey("experience") ? comp.getInteger("experience") : 0);
		}

		throw new InvalidParameterException("NBTTagCompound doesn't contain a Skill");
	}
}
