package net.dark_roleplay.core.testing.skills;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dark_roleplay.core.testing.crafting.IIcon;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Skill extends IForgeRegistryEntry.Impl<Skill>{
	
	private IIcon icon = null;
	
	private boolean isLevelable = false;
	private int maxLevel = 1;
	
	private List<Set<Skill>> requiredSkills = new ArrayList<Set<Skill>>();
	private List<Set<SkillPoint>> requiredSkillPoints = new ArrayList<Set<SkillPoint>>();
	
	public Skill(String registryName) {
		this.setRegistryName(registryName);
		this.requiredSkillPoints.add(null);
	}
	
	public Skill(String registryName, SkillPoint... skillPoints) {
		this.setRegistryName(registryName);
		Set<SkillPoint> requiredPoints = new HashSet<SkillPoint>();
		for(SkillPoint point : skillPoints) {
			requiredPoints.add(point);
		}
		this.requiredSkillPoints.add(requiredPoints);
	}
	
	/**
	 * Returns the required Skills to initially Unlock this Skill
	 * @return
	 */
	public Set<Skill> getRequiredSkillsToUnlock(){
		return this.requiredSkills.get(0);
	}
	
	/**
	 * Returns the Skills required to unlock a certain level of this Skill
	 * @param level
	 * @return the required Skills to unlock [level] of this Skill
	 */
	public Set<Skill> getRequiredSkillsForLevel(int level) throws InvalidLevelException{
		if(level - 1 <= 0) {
			throw new InvalidLevelException("Level can't be smaller then 2, use Skill#getRequiredSkillsToUnlock for level 1!", InvalidLevelException.Type.TO_SMALL_LEVEL);
		}else if(level - 1 > this.maxLevel){
			throw new InvalidLevelException("Level is exceeding the MaxLevel for this Skill", InvalidLevelException.Type.BEYOND_MAX_LEVEL);
		}else {
			return this.requiredSkills.get(level - 1);
		}
	}
	
	/**
	 * Returns the required SkillPoints to initially Unlock this Skill
	 * @return
	 */
	public Set<SkillPoint> getRequiredSkillPointsToUnlock(){
		return this.requiredSkillPoints.get(0);
	}
	
	/**
	 * Returns the SkillPoints required to unlock a certain level of this Skill
	 * @param level
	 * @return the required SkillPoints to unlock [level] of this Skill
	 */
	public Set<SkillPoint> getRequiredSkillPointsForLevel(int level) throws InvalidLevelException{
		if(level - 1 <= 0) {
			throw new InvalidLevelException("Level can't be smaller then 2, use Skill#getRequiredSkillPointsToUnlock for level 1!", InvalidLevelException.Type.TO_SMALL_LEVEL);
		}else if(level - 1 > this.maxLevel){
			throw new InvalidLevelException("Level is exceeding the MaxLevel for this Skill", InvalidLevelException.Type.BEYOND_MAX_LEVEL);
		}else {
			return this.requiredSkillPoints.get(level - 1);
		}
	}
	
	/**
	 * Adds a SkillLevel to this Skill
	 * @param requiredSkillPointsForLevel the SkillPoints required to unlock this Level
	 */
	public void addSkillLevel(Set<SkillPoint> requiredSkillPointsForLevel, Skill... requiredSkills) {
		this.requiredSkillPoints.add(requiredSkillPointsForLevel);
		this.isLevelable = true;
		this.maxLevel ++; 
		if(requiredSkills != null) {
			Set<Skill> reqSkills = new HashSet<Skill>();
			for(Skill skill : requiredSkills) {
				reqSkills.add(skill);
			};
			this.requiredSkills.add(reqSkills);
		}else {
			this.requiredSkills.add(null);
		}
	}
	
	/**
	 * Used to determine if you can level a SkillPoint or not
	 * @return true if the SkillPoint can be leveled
	 */
	public boolean isLevelable() {
		return this.isLevelable;
	}
	
	/**
	 * Used to get the maximum Level of a Skill
	 * @return the maximum Level of this Skill (-1 if the Skill can't be leveled)
	 */
	public int getMaxLevel() {
		return this.maxLevel;
	}
	
	/**
	 * Used to get the Icon of a Skill
	 * @return the Icon used to symbolize this Skill
	 */
	public IIcon getIcon() {
		return this.icon;
	}
}
