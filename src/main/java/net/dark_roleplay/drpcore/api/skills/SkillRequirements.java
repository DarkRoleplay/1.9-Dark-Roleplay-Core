package net.dark_roleplay.drpcore.api.skills;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SkillRequirements {

	private List<SkillPoint> requiredPoints = Lists.newArrayList();
	private Map<SkillPoint, Byte> requiredAmounts = Maps.newHashMap(); 
	
	public void addRequirement(SkillPoint skillPoint){
		addRequirement(skillPoint, (byte) 1);
	}
	
	public void addRequirement(SkillPoint skillPoint, byte amount){
		this.requiredPoints.add(skillPoint);
		this.requiredAmounts.put(skillPoint, amount);
	}
	
	public List<SkillPoint> getRequiredPoints(){
		return requiredPoints;
	}
	
	public byte getRequiredAmount(SkillPoint point){
		if(requiredAmounts.containsKey(point))
			return requiredAmounts.get(point);
		else return -1;
	}
}
