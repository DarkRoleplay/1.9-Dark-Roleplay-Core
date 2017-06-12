package net.dark_roleplay.drpcore.common.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.skills.SkillTree;

public class SkillRegistry {
	
	private static List<SkillTree> skillTrees = Lists.newArrayList();
	
	private static List<SkillPoint> skillPoints = Lists.newArrayList();
	
	private static Map<String, Skill> skillKeys = Maps.newHashMap();
	
	private static Map<String, SkillPoint> skillPointKeys = Maps.newHashMap();

	
	public static void registerSkillPoint(SkillPoint point){
		if(point != null){
			skillPoints.add(point);
			skillPointKeys.put(point.getRegistryName(), point);
		}
	}
	
	public static void registerSkillTree(SkillTree tree){
		if(tree != null){
			skillTrees.add(tree);
			
			for(Skill skill : tree.getSkills()){
				if(!skillKeys.containsKey(skill.getRegistryName())){
					skillKeys.put(skill.getRegistryName(), skill);
				}
			}
		}
	}
	
	public static SkillTree getSkillTree(int pos){
		return skillTrees.get(pos);
	}
	
	public static List<SkillTree> getSkillTrees(){
		return skillTrees;
	}
	
	public static Skill getSkillByName(String registryName){
		return skillKeys.containsKey(registryName) ? skillKeys.get(registryName) : null;
	}
	
	public static SkillPoint getSkillPointByName(String registryName){
		return skillPointKeys.containsKey(registryName) ? skillPointKeys.get(registryName) : null;
	}
	
	public static List<SkillPoint> getSkillPoints(){
		return skillPoints;
	}
}
