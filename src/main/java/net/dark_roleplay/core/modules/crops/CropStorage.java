package net.dark_roleplay.core.modules.crops;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.dark_roleplay.core.modules.date.Date;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CropStorage implements IStorage<ICropHandler> {
	@Override
	public NBTBase writeNBT(Capability<ICropHandler> capability, ICropHandler instance, EnumFacing side) {
		Map<BlockPos, CropInfo> crops = instance.getCrops();
		if (crops.isEmpty())
			return new NBTTagCompound();
		else {
			NBTTagCompound comp = new NBTTagCompound();
			NBTTagList list = new NBTTagList();

			Iterator<BlockPos> it = crops.keySet().iterator();

			int i = 0;
			while (it.hasNext()) {
				NBTTagCompound tag = new NBTTagCompound();
				BlockPos pos = it.next();
				tag.setTag("pos", NBTUtil.createPosTag(pos));
				tag.setInteger("age", crops.get(pos).getAge());
				list.appendTag(tag);
			}
			comp.setTag("crops", list);
			comp.setTag("last_grown", instance.getLastGrown().toNBT());
			return comp;
		}
	}

	@Override
	public void readNBT(Capability<ICropHandler> capability, ICropHandler instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag;
		if (nbt == null || !(nbt instanceof NBTTagCompound) || !(tag = (NBTTagCompound) nbt).hasKey("crops")) {
			instance.setCrops(new HashMap<BlockPos, CropInfo>());
			return;
		}
		

		

		NBTTagList list = (NBTTagList) tag.getTag("crops");
		;
		Map<BlockPos, CropInfo> crops = new HashMap<BlockPos, CropInfo>();

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound cropTag = list.getCompoundTagAt(i);
			BlockPos pos = NBTUtil.getPosFromTag(cropTag.getCompoundTag("pos"));
			crops.put(pos, new CropInfo(cropTag.getInteger("age")));
		}
		instance.setCrops(crops);
		if (tag.hasKey("last_grown"))
			instance.setLastGrown(new Date((NBTTagCompound) (((NBTTagCompound) nbt).getTag("last_grown"))));
	}
}
