package net.drpcore.common.capabilities.entities.player.moral;

import net.drpcore.common.capabilities.entities.player.DataHandlerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MoralHandler extends DataHandlerBase implements IMoral{

	private int moralLevel;
	private int prevMoralLevel;
	private int moralTimer;
	
	public MoralHandler(){
		
	}
	
	@Override
	public void update(EntityPlayer player, World world, Phase phase) {

		// TODO Auto-generated method stub
		
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
	public IMessage createUpdateMessage() {
		//NBTTagCompound data = (NBTTagCompound)TANCapabilities.THIRST.getStorage().writeNBT(TANCapabilities.THIRST, this, null);
        //return new MessageUpdateStat(TANCapabilities.THIRST, data);
		return null;
	}

	@Override
	public void setMoral(int moral) {
		this.moralLevel = moral;
	}

	@Override
	public int getMoral() {
		return this.moralLevel;
	}

	@Override
	public void setChangeTime(int ticks) {

		this.moralTimer = ticks;
		
	}

	@Override
	public int getChangeTime() {
		return this.moralTimer;
	}
	
}
