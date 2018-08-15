package net.dark_roleplay.core.testing.skills;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.dark_roleplay.core.References;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class SkillStorage implements IStorage<SkillHandler>{

	@Override
	public NBTBase writeNBT(Capability<SkillHandler> capability, SkillHandler instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList skillsTag = new NBTTagList();
		NBTTagList skillPointsTag = new NBTTagList();

		Collection<SkillHolder> skills = instance.getSkillHolders();
		skills.forEach(skill ->{
			NBTTagCompound skillTag = skill.serialize();
			skillsTag.appendTag(skillTag);
		});
		
		if(!skillsTag.hasNoTags())
			tag.setTag("skills", skillsTag);
		
		Collection<SkillPointHolder> skillPoints = instance.getSkillPointHolders();
		skillPoints.forEach(skillPoint ->{
			NBTTagCompound skillTag = skillPoint.serialize();
			skillPointsTag.appendTag(skillTag);
		});

		if(!skillPointsTag.hasNoTags())
			tag.setTag("skill_points", skillPointsTag);
		
		return tag;
	}

	@Override
	public void readNBT(Capability<SkillHandler> capability, SkillHandler instance, EnumFacing side, NBTBase nbt) {
		if(!(nbt instanceof NBTTagCompound))
			return;
		
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		if(tag.hasKey("skills")) {
			Set<SkillHolder> skills = new HashSet<SkillHolder>();
			NBTTagList skillList = tag.getTagList("skills", NBT.TAG_COMPOUND);
			for(int i = 0; i < skillList.tagCount(); i ++) {
				try {
					SkillHolder holder = SkillHolder.deserialize(skillList.getCompoundTagAt(i));
					skills.add(holder);
				}catch(InvalidParameterException e) {
					References.LOGGER.debug(e.getStackTrace());
				}
			}
			instance.setSkillHolders(skills);
		}
		
		if(tag.hasKey("skill_points")) {
			Set<SkillPointHolder> skillPoints = new HashSet<SkillPointHolder>();
			NBTTagList skillPointList = tag.getTagList("skill_points", NBT.TAG_COMPOUND);
			for(int i = 0; i < skillPointList.tagCount(); i ++) {
				try {
					SkillPointHolder holder = SkillPointHolder.deserialize(skillPointList.getCompoundTagAt(i));
					skillPoints.add(holder);
				}catch(InvalidParameterException e) {
					References.LOGGER.debug(e.getStackTrace());
				}
			}
			instance.setSkillPointHolders(skillPoints);
		}
	}

}
