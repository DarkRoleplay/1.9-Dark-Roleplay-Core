package net.dark_roleplay.core.testing.skills;

import net.dark_roleplay.core.testing.crafting.IIcon;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SkillPoint extends IForgeRegistryEntry.Impl<SkillPoint>{

	private IIcon icon = null;
	
	private boolean isLevelable = false;
	private int maxLevel = -1;
	
	/**
	 * Returns the required amount of Experience to level this SkillPoint
	 * @param level The Level to get the required experience for
	 * @return the amount of experience needed to Level this SkillPoint
	 * @throws InvalidLevelException if this SkillPoint can't be Leveled or the Level is exceeding the MaxLevel
	 */
	public int getRequiredExperienceForLevel(int level) throws InvalidLevelException{
		if(!this.isLevelable){
			throw new InvalidLevelException("The SkillPoint " + this.toString() + " cannot be leveled!", InvalidLevelException.Type.CANT_LEVEL);
		}else if(this.maxLevel < level){
			throw new InvalidLevelException("This Level is exceeding the MaxLevel: " + this.maxLevel + "!", InvalidLevelException.Type.BEYOND_MAX_LEVEL);
		}else if(level <= 0) {
			throw new InvalidLevelException("Level " + level + "is not a valid level, levels must be bigger then 0!", InvalidLevelException.Type.TO_SMALL_LEVEL);
		}else {
			return (level * (level / 2)) * 10;
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
	 * Used to get the maximum Level of a SkillPoint
	 * @return the maximum Level of this SkillPoint (-1 if the SkillPoint can't be leveled)
	 */
	public int getMaxLevel() {
		return this.maxLevel;
	}
	
	/**
	 * Used to get the Icon of a Skill Point
	 * @return the Icon used to symbolize this SkillPoint
	 */
	public IIcon getIcon() {
		return this.icon;
	}
}
