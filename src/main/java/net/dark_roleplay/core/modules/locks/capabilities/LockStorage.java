package net.dark_roleplay.core.modules.locks.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class LockStorage implements IStorage<ILockHandler>{

	@Override
	public NBTBase writeNBT(Capability<ILockHandler> capability, ILockHandler instance, EnumFacing side) {
		return instance.serializeNBT();
	}
	
	@Override
	public void readNBT(Capability<ILockHandler> capability, ILockHandler instance, EnumFacing side, NBTBase nbt) {
		instance.deserializeNBT((NBTTagCompound) nbt);
	}

}
