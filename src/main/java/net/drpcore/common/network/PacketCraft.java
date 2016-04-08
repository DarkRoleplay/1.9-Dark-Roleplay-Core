package net.drpcore.common.network;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.util.crafting.CraftingManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketCraft extends PacketBase<PacketCraft>{

	private int recipeID;
	private Block craftingStation;
	private int stationID;
	private String category;
	public PacketCraft(){}
	
	public PacketCraft(Block craftingStation,String Category, int recipeID){
		this.recipeID = recipeID;
		this.stationID = Block.getIdFromBlock(craftingStation);
		this.category = Category;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		recipeID = buf.readInt();
	    stationID = buf.readInt();
	    category = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(recipeID);
		buf.writeInt(stationID);
		ByteBufUtils.writeUTF8String(buf, category);
	}
	

	@Override
	public void handleClientSide(PacketCraft message, EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(PacketCraft message, EntityPlayer player) {
		CraftingManager.craftItem(message.stationID,message.category,message.recipeID,player);
	}

}
