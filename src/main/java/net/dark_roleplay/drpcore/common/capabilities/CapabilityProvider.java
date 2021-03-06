package net.dark_roleplay.drpcore.common.capabilities;

import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityProvider <C> implements ICapabilitySerializable<NBTBase>{
    /** The capability this is for */
    private final Capability<C> capability;
    private final C instance;
    
    public CapabilityProvider(Capability<C> capability){
        this.capability = capability;
        this.instance = capability.getDefaultInstance();
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability != null && capability == this.capability;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        return capability != null && capability == this.capability ? (T)this.instance : null;
    }

    @Override
    public NBTBase serializeNBT(){
        return capability.getStorage().writeNBT(capability, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt){
    	capability.getStorage().readNBT(capability, this.instance, null, nbt);
    }
}
