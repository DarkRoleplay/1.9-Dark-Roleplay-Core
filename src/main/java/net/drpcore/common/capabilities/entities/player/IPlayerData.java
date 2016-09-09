package net.drpcore.common.capabilities.entities.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public interface IPlayerData {

	public void update(EntityPlayer player, World world, Phase phase);

	public boolean hasChanged();

	public void onSendClientUpdate();

	//public IMessage createUpdateMessage();
}
