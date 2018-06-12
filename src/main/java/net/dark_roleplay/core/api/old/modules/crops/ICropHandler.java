package net.dark_roleplay.core.api.old.modules.crops;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.dark_roleplay.core.api.old.modules.crops.ICropHandler.CropInfo;
import net.dark_roleplay.core.api.old.modules.time.Date;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public interface ICropHandler {

	public void growCrops(World world);
	
	public void addCrop(BlockPos pos);
	
	public void removeCrop(BlockPos pos);
	
	public int getCropAge(BlockPos pos);
	
	public int getAndIncreaseCropAge(BlockPos pos, int amount);
	
	public Map<BlockPos, CropInfo> getCrops();
	
	public void setCrops(Map<BlockPos, CropInfo> crops);
	
	public Date getLastGrown();
	
	public void setLastGrown(Date date);
	
	public static class Impl implements ICropHandler{
		
		protected Map<BlockPos, CropInfo> crops = new HashMap<BlockPos, CropInfo>();
		
		protected Date lastGrown;
		
		@Override
		public void growCrops(World world) {
			if(crops != null && crops.keySet() != null && !crops.keySet().isEmpty()){
				Date date = world.getCapability(DRPCoreCapabilities.DATE_HANDLER, null).getDate();
				if(lastGrown == null){
					lastGrown = date.copy();
				}else if(date.getDayOfSeason() > this.lastGrown.getDayOfSeason()){
					int difference = date.getDayOfSeason() - this.lastGrown.getDayOfSeason();
					for (Iterator<BlockPos> iter = crops.keySet().iterator(); iter.hasNext();) {
						BlockPos pos = iter.next();
						IBlockState state = world.getBlockState(pos);
						if(state.getBlock() instanceof ICrop){
							GrowthResult result = ((ICrop)state.getBlock()).growthUpdate(world, pos, state, getAndIncreaseCropAge(pos, difference));
							if(result == GrowthResult.MATURED || result == GrowthResult.SPOILED){
								iter.remove();
							}
						}else{
							iter.remove();
						}
					}
					this.lastGrown = date.copy();
				}
			}
		}

		@Override
		public void addCrop(BlockPos pos) {
			crops.put(pos, new CropInfo());
		}

		@Override
		public void removeCrop(BlockPos pos) {
			if(crops.containsKey(pos))
				crops.remove(pos);
		}

		@Override
		public int getCropAge(BlockPos pos) {
			if(crops.containsKey(pos))
				return crops.get(pos).getAge();
			else
				return 0;
		}

		@Override
		public Map<BlockPos, CropInfo> getCrops() {
			return crops;
		}

		@Override
		public void setCrops(Map<BlockPos, CropInfo> crops) {
			this.crops = crops;
		}

		@Override
		public int getAndIncreaseCropAge(BlockPos pos, int amount) {
			if(crops.containsKey(pos)){
				crops.get(pos).age(amount);
				return crops.get(pos).getAge();
			}else{
				return 0;
			}
		}

		@Override
		public Date getLastGrown() {
			return lastGrown;
		}

		@Override
		public void setLastGrown(Date date) {
			this.lastGrown = date;
		}
	}
	
	static class CropInfo{
		private int age;
		
		public CropInfo(){
			this.age = 0;
		}
		
		public CropInfo(int age){
			this.age = age;
		}
		
		public int getAge(){
			return age;
		}
		
		public void age(int amount){
			this.age += amount;
		}
	}
}
