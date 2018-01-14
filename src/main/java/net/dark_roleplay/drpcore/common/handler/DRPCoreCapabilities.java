package net.dark_roleplay.drpcore.common.handler;

import java.util.concurrent.Callable;

import net.dark_roleplay.drpcore.common.capabilities.player.crafting.*;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.*;
import net.dark_roleplay.drpcore.modules.crops.CropStorage;
import net.dark_roleplay.drpcore.modules.crops.ICropHandler;
import net.dark_roleplay.drpcore.modules.time.DateStorage;
import net.dark_roleplay.drpcore.modules.time.IDateHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class DRPCoreCapabilities {

	@CapabilityInject(IRecipeController.class)
	public static final Capability<IRecipeController> DRPCORE_RECIPE_CONTROLLER = null;
	
	@CapabilityInject(ISkillController.class)
	public static final Capability<ISkillController> DRPCORE_SKILL_CONTROLLER = null;
	
	@CapabilityInject(ICropHandler.class)
	public static final Capability<ICropHandler> CROP_HANDLER = null;
	
	@CapabilityInject(IDateHandler.class)
	public static final Capability<IDateHandler> DATE_HANDLER = null;
	
	public static void preInit(){
	}
	
	public static final void preInit(FMLPreInitializationEvent event) {}
	
	public static final void init(FMLInitializationEvent event) {
		CapabilityManager.INSTANCE.register(IRecipeController.class, new RecipeControllerStorage(), (Callable<IRecipeController>)() -> {return new RecipeControllerDefault();});;
		CapabilityManager.INSTANCE.register(ISkillController.class, new SkillControllerStorage(), (Callable<ISkillController>)() -> {return new ISkillController.Impl();});

		CapabilityManager.INSTANCE.register(ICropHandler.class, new CropStorage(), (Callable<ICropHandler>)() -> {return new ICropHandler.Impl();});
		CapabilityManager.INSTANCE.register(IDateHandler.class, new DateStorage(), (Callable<IDateHandler>)() -> {return new IDateHandler.Impl();});
	}

	public static final void postInit(FMLPostInitializationEvent event) {}
}
