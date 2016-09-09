package net.drpcore.common.capabilities.entities.player.moral;

import net.drpcore.common.capabilities.entities.player.IPlayerData;

public interface IMoral extends IPlayerData{
	
	public void setMoral(float newMoralLevel);
	public void addMoral(float addMoralLevel);
	public float getMoral();
	
}
