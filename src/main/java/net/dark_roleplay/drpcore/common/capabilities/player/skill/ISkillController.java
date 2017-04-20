package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dark_roleplay.drpcore.common.skills.SkillItem;
import net.dark_roleplay.drpcore.common.skills.SkillPoint;

public interface ISkillController {

	// Saving unspend Skill Points for different skill trees
	Map<SkillPoint, Integer> skillPoints = new HashMap<SkillPoint, Integer>();
	Map<SkillPoint, Integer> skillPointLevel = new HashMap<SkillPoint, Integer>();
	Map<SkillPoint, Integer> skillPointXP = new HashMap<SkillPoint, Integer>();
	List<SkillItem> unlockedSkills = new ArrayList<SkillItem>();

	public default void addSkillPoint(SkillPoint skillPoint) {
		addSkillPoint(skillPoint, 1);
	}

	public void addSkillPoint(SkillPoint skillPoint, int amount);

	public default boolean consumeSkillPoint(SkillPoint skillPoint) {
		return consumeSkillPoint(skillPoint, 1);
	}

	public boolean consumeSkillPoint(SkillPoint skillPoint, int amount);

	public boolean unlockSkill(SkillItem item);

	public boolean hasSkill(SkillItem item);

	public Map<SkillPoint, Integer> getSkillPoints();

	public List<SkillItem> getUnlockedSkills();

	public Map<SkillPoint, Integer> getSkillXP();
	
	public void increaseSkillXP(SkillPoint point, int amount);
	
	public Map<SkillPoint, Integer> getSkillLevel();
	
	public default void increaseSkillLevel(SkillPoint point){
		increaseSkillLevel(point, 1);
	}
	
	public void increaseSkillLevel(SkillPoint point, int amount);
	
}
