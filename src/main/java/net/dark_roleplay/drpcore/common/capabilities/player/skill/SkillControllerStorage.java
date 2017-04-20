package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dark_roleplay.drpcore.common.skills.SkillItem;
import net.dark_roleplay.drpcore.common.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SkillControllerStorage implements IStorage<ISkillController>{
	
    @Override
    public NBTTagCompound writeNBT(Capability<ISkillController> capability, ISkillController instance, EnumFacing side) {
    	NBTTagCompound tag = new NBTTagCompound();
    	
    	Map<SkillPoint, Integer> skillPoints = instance.getSkillPoints();
    	Map<SkillPoint, Integer> skillLevel = instance.getSkillLevel();
    	Map<SkillPoint, Integer> skillXP = instance.getSkillXP();
    	List<SkillItem> unlockedSkills = instance.getUnlockedSkills();
    	
    	
    	NBTTagCompound skills = new NBTTagCompound();
    	int i = 0;
    	for(SkillItem item : unlockedSkills){
    		skills.setString("skill_" + i, item.getRegistryName());
    		i++;
    	}
    	
    	tag.setInteger("skill_amount", i);
    	tag.setTag("skills", skills);
    	
    	NBTTagCompound points = new NBTTagCompound();
    	i = 0;
    	Set<SkillPoint> requiredPoints = skillPoints.keySet();
    	requiredPoints.addAll(skillLevel.keySet());
    	requiredPoints.addAll(skillXP.keySet());
    	for(SkillPoint item : requiredPoints){
    		skills.setString("point_" + i, item.getRegistryName());
    		skills.setInteger("point_amount_" + i, skillPoints.containsKey(item) ? skillPoints.get(item) : 0);
    		skills.setInteger("points_level_" + i, skillLevel.containsKey(item) ? skillLevel.get(item) : 0);
    		skills.setInteger("points_xp_" + i, skillXP.containsKey(item) ? skillXP.get(item) : 0);
    		i++;
    	}
    	
    	tag.setInteger("skill_points", i);
    	tag.setTag("skills", skills);
    	
        return tag;
    }

	@Override
	public void readNBT(Capability<ISkillController> capability, ISkillController instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		NBTTagCompound skills = (NBTTagCompound) tag.getTag("skills");
		int skillAmount = tag.getInteger("skill_amount");
		
		for(int i = 0; i < skillAmount; i ++){
			String name = skills.getString("skill_" + i);
			instance.unlockSkill(SkillRegistry.getSkillByName(name));
		}
	}
}