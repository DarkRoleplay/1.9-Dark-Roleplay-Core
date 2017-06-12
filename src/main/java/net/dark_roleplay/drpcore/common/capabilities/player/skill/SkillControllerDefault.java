package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.skills.SyncPacket_SkillPoint;
import net.dark_roleplay.drpcore.common.skills.SkillPointData;

public class SkillControllerDefault implements ISkillController{

	private Map<SkillPoint, SkillPointData> skillPointDataKeys = Maps.newHashMap();
	private List<Skill> unlockedSkills = Lists.newArrayList();

	@Override
	public void addSkillPoint(SkillPoint skillPoint, int amount) {
		SkillPointData data;
		if(skillPointDataKeys.containsKey(skillPoint)){
			data = skillPointDataKeys.get(skillPoint);
			data.setAmount(data.getAmount() + amount);
			skillPointDataKeys.replace(skillPoint, data);
		}else{
			data = new SkillPointData(skillPoint, amount);
			skillPointDataKeys.put(skillPoint, data);
		}
	}
	
	@Override
	public boolean consumeSkillPoint(SkillPoint skillPoint, int amount) {
		if(skillPointDataKeys.containsKey(skillPoint)){
			SkillPointData data = skillPointDataKeys.get(skillPoint);
			if((data.getAmount() - amount) < 0){
				return false;
			}else if(data.getAmount() - amount == 0){
				skillPointDataKeys.remove(skillPoint);
				return true;
			}else{
				data.setAmount(data.getAmount() - amount);
				skillPointDataKeys.replace(skillPoint, data);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unlockSkill(Skill item) {
		if(unlockedSkills.contains(item)){
			return false;
		}else{
			unlockedSkills.add(item);
			return true;
		}
	}

	@Override
	public boolean hasSkill(Skill item) {
		if(unlockedSkills.contains(item)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<SkillPointData> getSkillPoints() {
		return new ArrayList<SkillPointData>(skillPointDataKeys.values());
	}

	@Override
	public List<Skill> getUnlockedSkills() {
		return unlockedSkills;
	}

	@Override
	public void increaseSkillXP(SkillPoint point, int amount) {
		SkillPointData data;
		if(skillPointDataKeys.containsKey(point)){
			data = skillPointDataKeys.get(point);
			data.setXP(data.getXP() + amount);
		}else{
			data = new SkillPointData(point, 0, 0, amount);
		}
		if(data.getXP() > point.getRequiredXP(data.getLevel() + 1) && point.getMaxLevel() <= data.getLevel() + 1){
			data.setLevel(data.getLevel() + 1);
			data.setXP(data.getXP() - point.getRequiredXP(data.getLevel() + 1));
		}
		skillPointDataKeys.put(point, data);
	}

	@Override
	public void increaseSkillLevel(SkillPoint point, int amount) {
		SkillPointData data;
		if(skillPointDataKeys.containsKey(point)){
			data = skillPointDataKeys.get(point);
			data.setLevel(data.getLevel() + amount);
			if(point.getMaxLevel() > data.getLevel()){
				data.setLevel(point.getMaxLevel());
			}
		}else{
			data = new SkillPointData(point, 0, amount, 0);
		}
		skillPointDataKeys.put(point, data);
	}

	@Override
	public SkillPointData getSkillPointData(SkillPoint point) {
		if(skillPointDataKeys.containsKey(point)){
			return skillPointDataKeys.get(point);
		}else{
			return new SkillPointData(point);
		}
	}

	@Override
	public void unlockSkills(List<Skill> skills) {
		this.unlockedSkills.addAll(skills);
		
	}

	@Override
	public void addPoints(List<SkillPointData> datas) {
		for(SkillPointData data : datas)
			this.skillPointDataKeys.put(data.getPoint(), data);	
	}

	@Override
	public void setSkillPointData(SkillPointData data) {
		this.skillPointDataKeys.put(data.getPoint(), data);	
	}


}
