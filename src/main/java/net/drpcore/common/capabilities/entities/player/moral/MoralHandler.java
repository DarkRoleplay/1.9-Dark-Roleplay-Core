package net.drpcore.common.capabilities.entities.player.moral;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class MoralHandler implements IMoral{
	
	private float moralLevel;
	private float prevMoralLevel;
	
	private int moralTimer;

	public MoralHandler(){
		this.moralLevel = 1F;
	}

	@Override
	public void update(EntityPlayer player, World world, Phase phase) {
		if (phase == Phase.START)
        {
			if(moralTimer ++ >= 5000){
				moralTimer = 0;
				moralLevel -= 0.01F;
			}
			else moralTimer ++;
        }
	}

	@Override
	public boolean hasChanged() {
		return this.prevMoralLevel != this.moralLevel;
	}

	@Override
	public void onSendClientUpdate() {
		this.prevMoralLevel = this.moralLevel;
	}

	@Override
	public void setMoral(float newMoralLevel) {
		this.moralLevel = newMoralLevel;
	}

	@Override
	public void addMoral(float addMoralLevel) {
		this.moralLevel = Math.max(this.moralLevel + addMoralLevel, 1F);
	}

	@Override
	public float getMoral() {
		return this.moralLevel;
	}
	
}
