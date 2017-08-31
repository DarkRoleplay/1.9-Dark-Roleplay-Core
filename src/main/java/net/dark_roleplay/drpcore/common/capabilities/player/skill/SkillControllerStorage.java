package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.handler.DRPCoreSkills;
import net.dark_roleplay.drpcore.common.skills.SkillPointData;
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
    	
    	List<SkillPointData> skillPointDatas = instance.getSkillPoints();
    	List<Skill> unlockedSkills = instance.getUnlockedSkills();
    	Map<String, Integer> skillVersion = instance.getVersionKeys();
    	List<String> versionKeys = new ArrayList<String>(skillVersion.keySet());
    	
    	NBTTagCompound skills = new NBTTagCompound();
    	int i = 0;
    	for(Skill item : unlockedSkills){
    		skills.setString("skill_" + i, item.getRegistryName());
    		i++;
    	}
    	
    	tag.setInteger("skill_amount", i);
    	tag.setTag("skills", skills);
    	
    	NBTTagCompound points = new NBTTagCompound();
    	i = 0;
    	for(SkillPointData data : skillPointDatas){
    		points.setString("point_" + i, data.getPoint().getRegistryName());
    		points.setInteger("point_amount_" + i, data.getAmount());
    		points.setInteger("points_level_" + i, data.getLevel());
    		points.setInteger("points_xp_" + i, data.getXP());
    		i++;
    	}
    	
    	tag.setInteger("skill_point_amount", i);
    	tag.setTag("skill_points", points);
    	
		NBTTagCompound versionKeysTag = new NBTTagCompound();
		versionKeysTag.setInteger("key_amount", versionKeys.size());
		
		i = 0;
    	for(String key : versionKeys){
    		versionKeysTag.setString("key_" + i, key);
    		versionKeysTag.setInteger("value_" + i, skillVersion.get(key));
    		i++;
    	}
    	
    	tag.setTag("version_keys", versionKeysTag);
    	
        return tag;
    }

	@Override
	public void readNBT(Capability<ISkillController> capability, ISkillController instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		NBTTagCompound skills = (NBTTagCompound) tag.getTag("skills");
		if(!(tag.getTag("skill_points") instanceof NBTTagCompound))
			return;
		NBTTagCompound skillPoints = (NBTTagCompound) tag.getTag("skill_points");
		NBTTagCompound versionKeysTag = (NBTTagCompound) tag.getTag("version_keys");
		
		int skillAmount = tag.getInteger("skill_amount");
		int skillPointAmount = tag.getInteger("skill_point_amount");
		int versionKeyAmount = versionKeysTag == null ? 0 : versionKeysTag.getInteger("key_amount");
		
		for(int i = 0; i < skillAmount; i ++){
			String name = skills.getString("skill_" + i);
			if(SkillRegistry.getSkillByName(name) != null)
				instance.unlockSkill(SkillRegistry.getSkillByName(name));
		}
		
		for(int i = 0; i < skillPointAmount; i++){
			String name = skillPoints.getString("point_" + i);
			int amount = skillPoints.getInteger("point_amount_" + i);
			int level = skillPoints.getInteger("points_level_" + i);
			int xp = skillPoints.getInteger("points_xp_" + i);
			if(SkillRegistry.getSkillPointByName(name) != null){
				SkillPoint point = SkillRegistry.getSkillPointByName(name);
				instance.addSkillPoint(point, amount);
				instance.increaseSkillLevel(point, level);
				instance.increaseSkillXP(point, xp);
			}
		}

		for(int i = 0; i < versionKeyAmount; i++){
			String key = versionKeysTag.getString("key_" + i);
			int version = versionKeysTag.getInteger("value_" + i);
			instance.setSkillVersion(key, version);
		}
	}
}