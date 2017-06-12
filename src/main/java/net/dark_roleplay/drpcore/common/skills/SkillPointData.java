package net.dark_roleplay.drpcore.common.skills;

import net.dark_roleplay.drpcore.api.skills.SkillPoint;

public class SkillPointData {

	private SkillPoint point;
	private int amount;
	private int level;
	private int xp;
	
	public SkillPointData(SkillPoint point){
		this(point, 0, 0 ,0);
	}
	
	public SkillPointData(SkillPoint point, int amount){
		this(point, amount, 0 ,0);
	}
	
	public SkillPointData(SkillPoint point, int amount, int level, int xp){
		this.point = point;
		this.amount = amount;
		this.level = level;
		this.xp = xp;
	}

	public SkillPoint getPoint() {
		return point;
	}

	public void setPoint(SkillPoint point) {
		this.point = point;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXP() {
		return xp;
	}

	public void setXP(int xp) {
		this.xp = xp;
	}
	
	
	
}
