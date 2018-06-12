package net.dark_roleplay.core.api.old.modules.time;

import net.dark_roleplay.core.api.old.modules.crops.ICropHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class DateStorage implements IStorage<IDateHandler>{

	@Override
	public NBTBase writeNBT(Capability<IDateHandler> capability, IDateHandler instance, EnumFacing side) {
		NBTTagCompound tag = new NBTTagCompound();
		Date date = instance.getDate();
		tag.setInteger("year", date.getYear());
		tag.setInteger("day", date.getDayOfSeason());
		tag.setString("season", date.getSeason().getName());
		tag.setLong("last_tick", instance.getLastTick());
		return tag;
	}

	@Override
	public void readNBT(Capability<IDateHandler> capability, IDateHandler instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		instance.setDate(new Date(tag.getInteger("day"), Season.getSeasonByID(tag.getString("season")), tag.getInteger("year")));
		instance.setLastTick(tag.getLong("last_tick"));
	}

}
