package net.drpcore.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.crafting.AdvancedRecipe;
import net.drpcore.common.crafting.CraftingController;
import net.drpcore.common.network.PacketBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketCraft extends PacketBase<PacketCraft>{

	private AdvancedRecipe recipe;
	
	private String stationName;
	
	private String CategoryName;
	
	private int recipeID;
	
	private int craftAmount;
	
	private BlockPos pos;
	
	private ItemStack[] primaryInput = new ItemStack[0];
	
	private ItemStack[] secondaryInput = new ItemStack[0];
	
	public PacketCraft(){};
	
	public PacketCraft(AdvancedRecipe recipe, ItemStack[] primaryInput ,ItemStack[] secondaryInput,BlockPos pos,int craftAmount){
		this.recipe = recipe;
		this.stationName = recipe.getStation().getRegistryName().getResourceDomain() + "_" + recipe.getStation().getRegistryName().getResourcePath();
		this.CategoryName = recipe.getCategory();
		this.recipeID = recipe.getRegisteredID();
		this.primaryInput = primaryInput;
		this.secondaryInput = secondaryInput;
		this.pos = pos;
		this.craftAmount = craftAmount;
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = null;
		try {
			compound = (NBTTagCompound) JsonToNBT.getTagFromJson(ByteBufUtils.readUTF8String(buf));
		}
		catch(NBTException e) {}
		finally{
			NBTTagCompound recipe = (NBTTagCompound) compound.getTag("recipe");
			
			this.CategoryName = recipe.getString("Category");
			this.stationName = recipe.getString("Station");
			this.recipeID = recipe.getInteger("registrationID");
			this.craftAmount = recipe.getInteger("amount");
			
			this.recipe = CraftingController.getRecipe(this.stationName,this.CategoryName,recipeID);
			
			NBTTagList primaryInputNBT = (NBTTagList) compound.getTag("primaryInput");
			
			primaryInput = new ItemStack[primaryInputNBT.tagCount()];
			if(primaryInput != null)
			for(int i = 0; i < primaryInputNBT.tagCount(); i++){
				if(primaryInputNBT.get(i) != null) 
					primaryInput[i] = ItemStack.loadItemStackFromNBT((NBTTagCompound) primaryInputNBT.get(i));
			}
		
			NBTTagList secondaryInputNBT = null;
			if(compound.getTag("secondaryInput") instanceof NBTTagList)
			secondaryInputNBT = (NBTTagList) compound.getTag("secondaryInput");
			
			if(secondaryInputNBT != null && secondaryInputNBT.tagCount() > 0){
				secondaryInput = new ItemStack[secondaryInputNBT.tagCount()];
				for(int i = 0; i < secondaryInputNBT.tagCount(); i++){
					secondaryInput[i] = ItemStack.loadItemStackFromNBT((NBTTagCompound) secondaryInputNBT.get(i));
				}
			}
			
			
		}
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();
		
		NBTTagCompound recipe = new NBTTagCompound();
		if(this.recipe != null){
			writeToNBT(this.recipe.getStation(),recipe);
			recipe.setString("Category", this.CategoryName);
			recipe.setString("Station", this.stationName);
			recipe.setInteger("registrationID", this.recipe.getRegisteredID());
			recipe.setInteger("amount", this.craftAmount);
		}
		compound.setTag("recipe", recipe);
		
		NBTTagList primaryInputNBT = new NBTTagList();
		for(int i = 0; i < this.primaryInput.length; ++i) {
			if(primaryInput[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				primaryInput[i].writeToNBT(item);
				primaryInputNBT.appendTag(item);
			}
		}
		compound.setTag("primaryInput", primaryInputNBT);
		
		NBTTagList secondaryInputNBT = null;
		if(secondaryInput != null){
			secondaryInputNBT = new NBTTagList();
			for(int i = 0; i < this.secondaryInput.length; ++i) {
				if(secondaryInput[i] != null) {
					NBTTagCompound item = new NBTTagCompound();
					secondaryInput[i].writeToNBT(item);
					secondaryInputNBT.appendTag(item);
				}
			}
		}
		compound.setTag("secondaryInput", secondaryInputNBT);
		
		ByteBufUtils.writeUTF8String(buf, compound.toString());
	}
	
	

	@Override
	public void handleClientSide(PacketCraft message, EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(PacketCraft message, EntityPlayer player) {
		CraftingController.craftRecipe(player, message.recipe, message.primaryInput, message.secondaryInput, message.craftAmount);
	}
	
	 private NBTTagCompound writeToNBT(Block block, NBTTagCompound nbt){
	     ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(block);
	     nbt.setString("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());

	     return nbt;
	  }
}
