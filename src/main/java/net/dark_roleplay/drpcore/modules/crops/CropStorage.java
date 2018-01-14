package net.dark_roleplay.drpcore.modules.crops;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.dark_roleplay.drpcore.modules.crops.ICropHandler.CropInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CropStorage implements IStorage<ICropHandler>{
	@Override
	public NBTBase writeNBT(Capability<ICropHandler> capability, ICropHandler instance, EnumFacing side) {
		Map<BlockPos, CropInfo> crops = instance.getCrops();
		if(crops.isEmpty())
			return new NBTTagShort((short) 0);
		else{
			NBTTagList list = new NBTTagList();
			
			Iterator<BlockPos> it = crops.keySet().iterator();
			
			int i = 0;
			while(it.hasNext()){
				NBTTagCompound tag = new NBTTagCompound();
				BlockPos pos = it.next();
				tag.setTag("pos", NBTUtil.createPosTag(pos));
				tag.setInteger("age", crops.get(pos).getAge());
				list.appendTag(tag);
			}
			return list;
		}
	}

	@Override
	public void readNBT(Capability<ICropHandler> capability, ICropHandler instance, EnumFacing side, NBTBase nbt) {
		if(nbt == null || nbt instanceof NBTTagEnd || nbt instanceof NBTTagShort){
			instance.setCrops(new HashMap<BlockPos, CropInfo>());
			return;
		}
		NBTTagList list = (NBTTagList) nbt;
		Map<BlockPos, CropInfo> crops = new HashMap<BlockPos, CropInfo>();
		
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound tag = list.getCompoundTagAt(i);
			BlockPos pos = NBTUtil.getPosFromTag(tag.getCompoundTag("pos"));
			crops.put(pos, new CropInfo(tag.getInteger("age")));
		}
		instance.setCrops(crops);
	}
}
