package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.dark_roleplay.drpcore.api.Modules;
import net.dark_roleplay.drpcore.modules.skill.Skill;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.registries.IForgeRegistry;

public class SkillControllerStorage implements IStorage<ISkillController>{
	
    @Override
    public NBTTagCompound writeNBT(Capability<ISkillController> capability, ISkillController instance, EnumFacing side) {
    	NBTTagCompound tag = new NBTTagCompound();

    	NBTTagList skillList = new NBTTagList();
    	for(Skill skill : instance.getSkills()){
    		skillList.appendTag(new NBTTagString(skill.getRegistryName().toString()));
    	}
    	tag.setTag("skills", skillList);
    	
        return tag;
    }

	@Override
	public void readNBT(Capability<ISkillController> capability, ISkillController instance, EnumFacing side, NBTBase nbt) {
		NBTTagList skillList = ((NBTTagCompound ) nbt).getTagList("skills", 8);
		
		List<Skill> skills = new ArrayList<Skill>();
		IForgeRegistry<Skill> registry = Modules.SKILL.getSkillRegistry();
		for(int i = 0; i < skillList.tagCount(); i++){
			ResourceLocation loc;
			if(registry.containsKey(loc = new ResourceLocation(skillList.getStringTagAt(i)))){
				skills.add(registry.getValue(loc));
			}
		}
		
		instance.setSkills(skills);
	}
}