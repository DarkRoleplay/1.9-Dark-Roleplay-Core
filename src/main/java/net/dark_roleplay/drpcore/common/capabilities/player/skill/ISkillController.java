package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.skills.SkillPointData;

public interface ISkillController {

	// Saving unspend Skill Points for different skill trees
	List<SkillPointData> skillPoints = Lists.newArrayList();
	List<Skill> unlockedSkills = new ArrayList<Skill>();

	public default void addSkillPoint(SkillPoint skillPoint) {
		addSkillPoint(skillPoint, 1);
	}

	public void addSkillPoint(SkillPoint skillPoint, int amount);

	public default boolean consumeSkillPoint(SkillPoint skillPoint) {
		return consumeSkillPoint(skillPoint, 1);
	}

	public boolean consumeSkillPoint(SkillPoint skillPoint, int amount);

	public boolean unlockSkill(Skill item);

	public boolean hasSkill(Skill item);

	public List<SkillPointData> getSkillPoints();

	public List<Skill> getUnlockedSkills();
	
	public void increaseSkillXP(SkillPoint point, int amount);
	
	public default void increaseSkillLevel(SkillPoint point){
		increaseSkillLevel(point, 1);
	}
	
	public void increaseSkillLevel(SkillPoint point, int amount);
	
	public SkillPointData getSkillPointData(SkillPoint point);
	
	public void unlockSkills(List<Skill> skills);
	
	public void addPoints(List<SkillPointData> datas);
	
	public void setSkillPointData(SkillPointData data);
	
}
