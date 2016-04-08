package net.drpcore.common.entities.player;

import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExtendedPlayer{ //TODO implements IExtendedEntityProperties{
	/*
	public final static String EXT_PROP_NAME = "DRPCorePlayerData";
	
	private final EntityPlayer player;
	
	public ExtendedPlayer(EntityPlayer player){
		this.player = player;
	}
	
	public final PlayerInventory inventory = new PlayerInventory();
	
	public static final void register(EntityPlayer player){
		player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
	}
	
	public static final ExtendedPlayer get(EntityPlayer player){
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbtData = new NBTTagCompound();
		
		inventory.writeToNBT(nbtData);

		compound.setTag(EXT_PROP_NAME, nbtData);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

		inventory.readFromNBT(properties);
	}

	@Override
	public void init(Entity entity, World world) {}
	*/
}
