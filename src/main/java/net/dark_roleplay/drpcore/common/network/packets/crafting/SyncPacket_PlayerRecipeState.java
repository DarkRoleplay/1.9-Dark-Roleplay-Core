package net.dark_roleplay.drpcore.common.network.packets.crafting;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SyncPacket_PlayerRecipeState extends PacketBase<SyncPacket_PlayerRecipeState>{

	private String recipeID;
	private int type = 0;
	private float percentage = 0F;
	
	public SyncPacket_PlayerRecipeState(){
		this.recipeID = null;
		this.type = 3;
		this.percentage = 0F;
	}
	
	/**
	 * User to sync RecipeControllerCapability to Player
	 * @param recipe registryName of Recipe
	 * @param type Type 0 = unlocked, 1 = locked, 2 = progressed (requires percentage)
	 * @param percentage Unlock progress for type 2
	 */
	public SyncPacket_PlayerRecipeState(String recipe, int type, float percentage){
		this.recipeID = recipe;
		this.type = type;
		if(this.type == 2)
			this.percentage = percentage;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {        
		this.recipeID = ByteBufUtils.readUTF8String(buf);
		this.type = buf.readInt();
		this.percentage = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.recipeID);
		buf.writeInt(type);
		buf.writeFloat(percentage);
	}

	@Override
	public void handleClientSide(SyncPacket_PlayerRecipeState message, EntityPlayer player) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
		  public void run() {
		    processMessage(message);
		  }
		});
	} 

	@Override
	public void handleServerSide(SyncPacket_PlayerRecipeState message, EntityPlayer player) {
		System.out.println("Wrong Receiver");
	}

	@SideOnly(Side.CLIENT)
	public void processMessage(SyncPacket_PlayerRecipeState message){
		EntityPlayer player = Minecraft.getMinecraft().player;
		switch(message.type){
		case 0:
//			player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).unlockRecipe(message.recipeID);
			break;
		case 1:
//			player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).lockRecipe(message.recipeID);
			break;
		case 2:
//			player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).progressRecipe(message.recipeID,message.percentage);
			break;
		}
	}
}
