package net.dark_roleplay.drpcore.common.capabilities.player.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillData;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.skills.SkillPointData;

public interface ISkillController {

	public void addSkillPoint(SkillPoint point);
	public void addSkillPoint(SkillPoint point, int amount);
	public void addSkillPoint(SkillPoint point, int amount, int level);
	public void addSkillPoint(SkillPoint point, int amount, int level, float xp);
	public void addSkillPoint(SkillPointData data);
	public List<SkillPointData> getSkillPoints();
	public void setSkillPoints(List<SkillPointData> data);
	
	public void addSkill(Skill point);
	public void addSkill(Skill point, int level);
	public void addSkill(Skill point, int level, float xp);
	public void addSkill(SkillData data);
	public List<SkillData> getSkills();
	public void setSkills(List<SkillData> data);
	
	public void consumeSkillPoint(SkillPoint point, int amount);
	public void xpSkillPoint(SkillPoint point, float xp);
	public void levelSkillPoint(SkillPoint point);
	public SkillPointData getSkillPointData(SkillPoint point);
	
	public void unlockSkill(Skill skill);
	public void xpSkill(Skill skill);
	public void levelSkill(Skill skill);
	public boolean hasSkill(Skill skill);
	public SkillData getSkillData(Skill skill);
	
	public static class Impl implements ISkillController{
		
		HashMap<SkillPoint, SkillPointData> skillPoints = new HashMap<SkillPoint, SkillPointData>();
		HashMap<Skill, SkillData> skills = new HashMap<Skill, SkillData>();
		
		
//		@Override
//		public void readBufSkillPoints(ByteBuf buf) {
//			buf.writeInt(skillPoints.size());
//			for(Map.Entry<SkillPoint, SkillPointData> entry : skillPoints.entrySet()){
//				entry.getValue().writeToBuf(buf);
//			}
//		}
//		
//		@Override
//		public void writeBufSkillPoints(ByteBuf buf) {
//			int size = buf.readInt();
//			for(int i = 0; i < size; i++){
//				SkillPointData data = new SkillPointData().readFromBuf(buf);
//				skillPoints.put(data.getPoint(), data);
//			}
//		}

		@Override
		public void addSkillPoint(SkillPoint point) {
			this.addSkillPoint(new SkillPointData(point, 0, 0, 0F));
		}

		@Override
		public void addSkillPoint(SkillPoint point, int amount) {
			this.addSkillPoint(new SkillPointData(point, amount, 0, 0F));
		}

		@Override
		public void addSkillPoint(SkillPoint point, int amount, int level) {
			this.addSkillPoint(new SkillPointData(point, amount, level, 0F));
		}

		@Override
		public void addSkillPoint(SkillPoint point, int amount, int level, float xp) {
			this.addSkillPoint(new SkillPointData(point, amount, level, xp));
		}

		@Override
		public void addSkillPoint(SkillPointData data) {
			if(this.skillPoints.containsKey(data.getPoint())){
				SkillPointData oldData = this.skillPoints.get(data.getPoint());
				oldData.addAmount(data.getAmount());
				oldData.addLevel(data.getLevel());
				oldData.addXP(data.getXP());
			}else{
				this.skillPoints.put(data.getPoint(), data);
			}
		}

		@Override
		public List<SkillPointData> getSkillPoints() {
			return new ArrayList<SkillPointData>(this.skillPoints.values());
		}

		@Override
		public void setSkillPoints(List<SkillPointData> data){
			
		}
		
		@Override
		public void addSkill(Skill point) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addSkill(Skill point, int level) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addSkill(Skill point, int level, float xp) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addSkill(SkillData data) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public List<SkillData> getSkills() {
			return new ArrayList<SkillData>(this.skills.values());
		}
		
		@Override
		public void setSkills(List<SkillData> data){
			
		}

		@Override
		public void consumeSkillPoint(SkillPoint point, int amount) {
			if(this.skillPoints.containsKey(point)){
				SkillPointData data = this.skillPoints.get(point);
				data.removeAmount(amount);
			}
		}

		@Override
		public void xpSkillPoint(SkillPoint point, float xp) {
			if(this.skillPoints.containsKey(point)){
				SkillPointData data = this.skillPoints.get(point);
				data.addXP(xp);;
			}
		}

		@Override
		public void levelSkillPoint(SkillPoint point) {
			if(this.skillPoints.containsKey(point)){
				SkillPointData data = this.skillPoints.get(point);
				data.addLevel(1);;
			}
		}

		@Override
		public SkillPointData getSkillPointData(SkillPoint point) {
			if(this.skillPoints.containsKey(point))
				return this.skillPoints.get(point);
			else
				return null;
		}

		@Override
		public void unlockSkill(Skill skill) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void xpSkill(Skill skill) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void levelSkill(Skill skill) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean hasSkill(Skill skill) {
			return this.skills.containsKey(skill);
		}

		@Override
		public SkillData getSkillData(Skill skill) {
			if(this.skills.containsKey(skill))
				return this.skills.get(skill);
			else
				return null;
		}
	}
}
