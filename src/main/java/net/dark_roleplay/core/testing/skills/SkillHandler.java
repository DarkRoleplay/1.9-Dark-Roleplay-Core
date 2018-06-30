package net.dark_roleplay.core.testing.skills;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.dark_roleplay.core.common.References;

public class SkillHandler {

	public Map<Skill, SkillHolder> skills = new HashMap<Skill, SkillHolder>();
	public Map<SkillPoint, SkillPointHolder> skillPoints = new HashMap<SkillPoint, SkillPointHolder>();
	
	public void setSkillHolders(Set<SkillHolder> holders) {
		for(SkillHolder holder : holders) {
			skills.put(holder.getSkill(), holder);
		}
	}
	
	public void setSkillPointHolders(Set<SkillPointHolder> holders) {
		for(SkillPointHolder holder : holders) {
			skillPoints.put(holder.getSkillPoint(), holder);
		}
	}
	
	public Collection<SkillHolder> getSkillHolders(){
		return skills.values();
	}
	
	public Collection<SkillPointHolder> getSkillPointHolders(){
		return skillPoints.values();
	}
	
	public boolean hasSkill(Skill skill) {
		return skills.containsKey(skill);
	}
	
	public SkillHolder getSkillHolder(Skill skill) {
		return skills.containsKey(skill) ? skills.get(skill) : null;
	}
	
	public SkillPointHolder getSkillPointHolder(SkillPoint skillPoint) {
		return skillPoints.containsKey(skillPoint) ? skillPoints.get(skillPoint) : null;
	}
	
	public boolean hasSkillPoint(SkillPoint skill) {
		return skillPoints.containsKey(skill);
	}
	
	public void unlockSkill(Skill skill) {
		if(skills.containsKey(skill))
			return;
		skills.put(skill, new SkillHolder(skill));
	}
	
	public void lockSkill(Skill skill) {
		if(!skills.containsKey(skill))
			return;
		skills.remove(skill);
	}
	
	public void levelSkillByLevel(Skill skill, int level) {
		if(skills.containsKey(skill) && skill.isLevelable()) {
			SkillHolder holder = skills.get(skill);
			holder.setLevel(Math.max(holder.getLevel() + level, skill.getMaxLevel()));
		}else {
			unlockSkill(skill);
			levelSkillByLevel(skill, level -1);
		}
	}
	
	public void addSkillPoint(SkillPoint skillPoint) {
		if(skillPoints.containsKey(skillPoint)) {
			SkillPointHolder holder = skillPoints.get(skillPoint);
			holder.setAmount(holder.getAmount() + 1);
		}
		skillPoints.put(skillPoint, new SkillPointHolder(skillPoint));
	}
	
	public void levelSkillPointByLevel(SkillPoint skillPoint, int level) {
		if(skillPoints.containsKey(skillPoint) && skillPoint.isLevelable()) {
			SkillPointHolder holder = skillPoints.get(skillPoint);
			holder.setLevel(Math.max(holder.getLevel() + level, skillPoint.getMaxLevel()));
		}else {
			addSkillPoint(skillPoint);
			levelSkillPointByLevel(skillPoint, level -1);
		}
	}
	
	public void levelSkillPointByExperience(SkillPoint skillPoint, int experience) {
		if(skillPoints.containsKey(skillPoint) && skillPoint.isLevelable()) {
			try {
				SkillPointHolder holder = skillPoints.get(skillPoint);
				int newExperience = experience + holder.getExperience();
				int requiredExperience = skillPoint.getRequiredExperienceForLevel(holder.getLevel());
				if(newExperience >= requiredExperience) {
					holder.setLevel(holder.getLevel() + 1);
					newExperience -= requiredExperience;
				}
				holder.setExperience(newExperience);
			} catch(InvalidLevelException e) {
				References.LOGGER.debug(e.getStackTrace());
			}
		}
	}
}
