package net.drpcore.common.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketSyncAdvancedInventory  extends PacketBase<PacketSyncAdvancedInventory>{

	int slotID;
	ItemStack stack;
	
	public PacketSyncAdvancedInventory(){}
	
	public PacketSyncAdvancedInventory(int slotID , EntityPlayer player){
		this.slotID = slotID;
		
		if (player.hasCapability(DarkRoleplayCore.DRPCORE_INV, null)){
			
			AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();

			stack = inventory.getStackInSlot(slotID);
		}
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		slotID = pb.readInt();
		try{
			stack = pb.readItemStackFromBuffer();
		}catch(IOException e){}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		
		pb.writeInt(slotID);
		pb.writeItemStackToBuffer(stack);
	}

	@Override
	public void handleClientSide(PacketSyncAdvancedInventory message, EntityPlayer player) {
		AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
		inventory.setInventorySlotContents(message.slotID, message.stack);
	}

	@Override
	public void handleServerSide(PacketSyncAdvancedInventory message, EntityPlayer player) {
		AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
		inventory.setInventorySlotContents(message.slotID, message.stack);
	}

}