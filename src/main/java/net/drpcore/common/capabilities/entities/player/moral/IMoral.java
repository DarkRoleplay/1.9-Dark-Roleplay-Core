package net.drpcore.common.capabilities.entities.player.moral;

import net.drpcore.common.capabilities.entities.player.IPlayerData;

public interface IMoral extends IPlayerData{
	
	public void setMoral(int moral);
	
	public int getMoral();
	
	public void setChangeTime(int ticks);
	public int getChangeTime();
}
