package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dark_roleplay.drpcore.common.skills.SkillItem;
import net.dark_roleplay.drpcore.common.skills.SkillPoint;

public class SkillControllerDefault implements ISkillController{

	private Map<SkillPoint, Integer> skillPoints = new HashMap<SkillPoint, Integer>();
	private Map<SkillPoint, Integer> skillPointLevel = new HashMap<SkillPoint, Integer>();
	private Map<SkillPoint, Integer> skillPointXP = new HashMap<SkillPoint, Integer>();
	private List<SkillItem> unlockedSkills = new ArrayList<SkillItem>();


	@Override
	public void addSkillPoint(SkillPoint skillPoint, int amount) {
		if(skillPoints.containsKey(skillPoint)){
			int oldAmount = skillPoints.get(skillPoint);
			skillPoints.replace(skillPoint, oldAmount + amount);
		}else{
			skillPoints.put(skillPoint, amount);
		}
	}
	
	@Override
	public boolean consumeSkillPoint(SkillPoint skillPoint, int amount) {
		if(skillPoints.containsKey(skillPoint)){
			int oldAmount = skillPoints.get(skillPoint);
			if(oldAmount > amount){
				skillPoints.replace(skillPoint, oldAmount - amount);
				return true;
			}else if( oldAmount == amount){
				skillPoints.remove(skillPoint);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unlockSkill(SkillItem item) {
		if(unlockedSkills.contains(item)){
			return false;
		}else{
			unlockedSkills.add(item);
			return true;
		}
	}

	@Override
	public boolean hasSkill(SkillItem item) {
		if(unlockedSkills.contains(item)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Map<SkillPoint, Integer> getSkillPoints() {
		return skillPoints;
	}

	@Override
	public List<SkillItem> getUnlockedSkills() {
		return unlockedSkills;
	}

	@Override
	public Map<SkillPoint, Integer> getSkillXP() {
		return skillPointXP;
	}

	@Override
	public void increaseSkillXP(SkillPoint point, int amount) {
		if(skillPointXP.containsKey(point)){
			int oldAmount = skillPoints.get(point);
			skillPointXP.replace(point, oldAmount + amount);
		}else{
			skillPointXP.put(point, amount);
		}
	}

	@Override
	public Map<SkillPoint, Integer> getSkillLevel() {
		return skillPointLevel;
	}

	@Override
	public void increaseSkillLevel(SkillPoint point, int amount) {
		if(skillPointLevel.containsKey(point)){
			int oldAmount = skillPoints.get(point);
			skillPointLevel.replace(point, oldAmount + amount);
		}else{
			skillPointLevel.put(point, amount);
		}
	}


}
