package net.dark_roleplay.drpcore.common.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillRegistry {
	
	private static List<SkillTree> skillTrees = new ArrayList<SkillTree>();
	
	private static Map<String, SkillItem> skills = new HashMap<String, SkillItem>();
	
	private static Map<String, SkillPoint> skillPoints = new HashMap<String, SkillPoint>();
	
	private static void registerSkillPoint(SkillPoint point){
		if(point != null){
			if(!skillPoints.containsKey(point.getRegistryName())){
				skillPoints.put(point.getRegistryName(), point);
			}
		}
	}
	
	public static void registerSkillTree(SkillTree tree){
		if(tree != null){
			skillTrees.add(tree);
			List<SkillItem> items = tree.getSkills();
			for(SkillItem item : items){
				skills.put(item.getRegistryName(), item);
				registerSkillPoint(item.getRequiredPoint());
			}
		}
	}
	
	public static SkillTree getSkillTree(int pos){
		return skillTrees.get(pos);
	}
	
	public static SkillItem getSkillByName(String registryName){
		if(skills.containsKey(registryName)){
			return skills.get(registryName);
		}
		return null;
	}
	
	public static SkillPoint getSkillPointByName(String registryName){
		if(skillPoints.containsKey(registryName)){
			return skillPoints.get(registryName);
		}
		return null;
	}
}
