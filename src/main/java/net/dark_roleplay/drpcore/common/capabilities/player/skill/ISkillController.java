package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.modules.work_in_progress.skill.Skill;
import net.minecraft.entity.player.EntityPlayer;

public interface ISkillController {

	public boolean hasSkill(Skill skill);
	public void unlockSkill(Skill skill, EntityPlayer player);
	public void lockSkill(Skill skill, EntityPlayer player);
	public List<Skill> getSkills();
	public void setSkills(List<Skill> skills);
	
	public static class Impl implements ISkillController{

		private List<Skill> skills = new ArrayList<Skill>();
		
		@Override
		public boolean hasSkill(Skill skill) {
			return skills.contains(skill);
		}

		@Override
		public void unlockSkill(Skill skill, EntityPlayer player) {
			if(!skills.contains(skill)){
				skills.add(skill);
				skill.onUnlocked(player);
			}
		}

		@Override
		public void lockSkill(Skill skill, EntityPlayer player) {
			if(skills.contains(skill)){
				skills.remove(skill);
				skill.onLocked(player);
			}
		}

		@Override
		public List<Skill> getSkills() {
			return skills;
		}

		@Override
		public void setSkills(List<Skill> skills) {
			this.skills = skills;
		}	
	}
}
