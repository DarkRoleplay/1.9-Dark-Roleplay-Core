package net.drpcore.common.capabilities.entities.player.moral;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MoralStorage implements IStorage<IMoral>{
	
	@Override
    public NBTBase writeNBT(Capability<IMoral> capability, IMoral instance, EnumFacing side) 
    {
        NBTTagCompound compound = new NBTTagCompound();
        
        compound.setFloat("moralLevel", instance.getMoral());

        return compound;
    }

    @Override
    public void readNBT(Capability<IMoral> capability, IMoral instance, EnumFacing side, NBTBase nbt) 
    {
        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Thirst must be read from an NBTTagCompound!");
        
        NBTTagCompound compound = (NBTTagCompound)nbt;
        
        if (compound.hasKey("moralLevel"))
        {
            instance.setMoral(compound.getFloat("moralLevel"));
        }

    }
	
}
