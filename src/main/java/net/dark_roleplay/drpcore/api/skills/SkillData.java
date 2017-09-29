package net.dark_roleplay.drpcore.api.skills;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.util.DRPUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SkillData {
	
	private Skill skill;
	private int level;
	private float xp;
	
	public SkillData(){
		this(null, 0, 0F);
	}
	
	public SkillData(Skill skill){
		this(skill, 0, 0F);
	}
	
	public SkillData(Skill skill, int level){
		this(skill, level, 0F);
	}
	
	public SkillData(Skill skill, int level, float xp){
		this.skill = skill;
		this.level = level;
		this.xp = xp;
	}
	
	public void writeToBuf(ByteBuf buf){
		ByteBufUtils.writeUTF8String(buf, skill.getRegistryName().toString());
		buf.writeInt(level);
		buf.writeFloat(xp);
	}
	
	public SkillData readFromBuf(ByteBuf buf){
		ResourceLocation name = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
		this.level = buf.readInt();
		this.xp = buf.readFloat();
		if(DRPUtil.getRegistrySkills().containsKey(name)){
			this.skill = DRPUtil.getRegistrySkills().getValue(name);
		}
		return this;
	}

	public Skill getSkill(){
		return this.skill;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public float getXP(){
		return this.xp;
	}
	
	private void setLevel(int level){
		this.level = level;
	}
	
	public void addLevel(int level){
		this.setLevel(this.level + level);
	}
	
	public void removeLevel(int level){
		this.setLevel(this.level - level);
	}
	
	private void setXP(float xp){
		if(this.skill.canLevel(this.level)){
			float requiredXP = this.skill.requiredXP(this.level);
			if(xp >= requiredXP){
				this.xp = xp - requiredXP;
				this.level++;
			}else{
				this.xp = xp;
			}
		}
	}
	
	public void addXP(float xp){
		this.setXP(this.xp + xp);
	}
	
	public void removeXP(float xp){
		this.setXP(this.xp - xp);
	}
}
