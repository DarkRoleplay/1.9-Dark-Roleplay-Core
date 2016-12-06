package net.drpcore.common.capabilitiesOld;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.capabilities.IRecipeController;
import net.drpcore.common.capabilitiesOld.entities.player.moral.IMoral;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DRPCoreCapabilities {
	
	@CapabilityInject(IMoral.class)
	public static final Capability<IMoral> MORAL = null;
	
	@CapabilityInject(IRecipeController.class)
	public static final Capability<IRecipeController> DRPCORE_RECIPE_CONTROLLER = null;
	
	@SubscribeEvent
    public void onEntityConstruct(AttachCapabilitiesEvent.Entity event){

        event.addCapability(new ResourceLocation(DarkRoleplayCore.MODID, "IRecipeController"), new ICapabilitySerializable<NBTTagCompound>(){
            IRecipeController inst = DRPCORE_RECIPE_CONTROLLER.getDefaultInstance();
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
                return capability == DRPCORE_RECIPE_CONTROLLER;
            }

            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
                return capability == DRPCORE_RECIPE_CONTROLLER ? DRPCORE_RECIPE_CONTROLLER.<T>cast(inst) : null;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound)DRPCORE_RECIPE_CONTROLLER.getStorage().writeNBT(DRPCORE_RECIPE_CONTROLLER, inst, null);
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
            	DRPCORE_RECIPE_CONTROLLER.getStorage().readNBT(DRPCORE_RECIPE_CONTROLLER, inst, null, nbt);
            }
        });
    }
	
}
