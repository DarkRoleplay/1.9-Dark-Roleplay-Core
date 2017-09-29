package net.dark_roleplay.drpcore.api.skills;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.util.DRPUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SkillPointData {

	private SkillPoint point;
	private int amount;
	private int level;
	private float xp;
	
	public SkillPointData(){
		this(null, 0, 0, 0F);
	}
	
	public SkillPointData(SkillPoint point){
		this(point, 0, 0, 0F);
	}
	
	public SkillPointData(SkillPoint point, int amount){
		this(point, amount, 0, 0F);
	}
	
	public SkillPointData(SkillPoint point, int amount, int level){
		this(point, amount, level, 0F);
	}
	
	public SkillPointData(SkillPoint point, int amount, int level, float xp){
		this.point = point;
		this.amount = amount;
		this.level = level;
		this.xp = xp;
	}

	public void writeToBuf(ByteBuf buf){
		ByteBufUtils.writeUTF8String(buf, point.getRegistryName().toString());
		buf.writeInt(amount);
		buf.writeInt(level);
		buf.writeFloat(xp);
	}
	
	public SkillPointData readFromBuf(ByteBuf buf){
		ResourceLocation name = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
		this.amount = buf.readInt();
		this.level = buf.readInt();
		this.xp = buf.readFloat();
		if(DRPUtil.getRegistrySkillPoints().containsKey(name)){
			this.point = DRPUtil.getRegistrySkillPoints().getValue(name);
		}
		return this;
	}
	
	public SkillPoint getPoint(){
		return this.point;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public float getXP(){
		return this.xp;
	}
	
	private void setAmount(int amount){
		this.amount = amount;
	}
	
	public void addAmount(int amount){
		this.setAmount(this.amount + amount);
	}
	
	public void removeAmount(int amount){
		this.setAmount(this.amount - amount);
	}
	
	private void setLevel(int level){
		if(level > this.level){
			this.addAmount(level - this.level);
		}
		this.level = level;
	}
	
	public void addLevel(int level){
		this.setLevel(this.level + level);
	}
	
	public void removeLevel(int level){
		this.setLevel(this.level - level);
	}
	
	private void setXP(float xp){
		if(this.point.canLevel(this.level)){
			float requiredXP = this.point.requiredXP(this.level);
			if(xp >= requiredXP){
				this.xp = xp - requiredXP;
				this.level++;
				this.amount++;
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
