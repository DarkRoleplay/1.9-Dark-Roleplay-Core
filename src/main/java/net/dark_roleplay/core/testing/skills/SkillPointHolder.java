package net.dark_roleplay.core.testing.skills;

import java.security.InvalidParameterException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class SkillPointHolder {

	private SkillPoint skillPoint = null;
	private int level = 0;
	private int experience = 0;
	private int amount = 0;
	
	public SkillPointHolder(SkillPoint skill) {
		this(skill, 0, 0);
	}
	
	public SkillPointHolder(SkillPoint skill, int amount) {
		this(skill, amount, 0, 0);
	}
	
	public SkillPointHolder(SkillPoint skill, int amount, int level) {
		this(skill, amount, level, 0);
	}
	
	public SkillPointHolder(SkillPoint skill, int amount, int level, int experience) {
		this.skillPoint = skill;
		this.level = level;
		this.experience = experience;
		this.amount = amount;
	}
	
	public SkillPoint getSkillPoint() {
		return skillPoint;
	}
	
	public void setSkillPoint(SkillPoint skill) {
		this.skillPoint = skill;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	public
	int getExperience() {
		return experience;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public NBTTagCompound serialize() {
		return this.serialize(null);
	}
	
	public NBTTagCompound serialize(NBTTagCompound comp) {
		if(comp == null) {
			comp = new NBTTagCompound();
		}
		comp.setString("skill_point", this.skillPoint.getRegistryName().toString());
		if(this.level != 1)
			comp.setInteger("level", this.level);
		if(this.experience != 0)
			comp.setInteger("experience", this.experience);
		if(this.amount > 0){
			comp.setInteger("amount", this.amount);
		}
		return comp;
	}
	
	public static SkillPointHolder deserialize(NBTTagCompound comp) {
		if(comp.hasKey("skill_point")) {
			String name = comp.getString("skill_point");
			ResourceLocation skillPointName = new ResourceLocation(name);
			SkillPoint skillPoint = SkillRegistries.SKILL_POINTS.containsKey(skillPointName) ? SkillRegistries.SKILL_POINTS.getValue(skillPointName) : null;
			if(skillPoint == null)
				throw new InvalidParameterException("SkillPoint [" + name + "] couldn't be found!");
			return new SkillPointHolder(skillPoint, comp.hasKey("amount") ? comp.getInteger("amount") : 1, comp.hasKey("level") ? comp.getInteger("level") : 0, comp.hasKey("experience") ? comp.getInteger("experience") : 0);
		}

		throw new InvalidParameterException("NBTTagCompound doesn't contain a Skill");
	}
}
