package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.entities.util.sitting.Sittable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DRPCoreEntities {

	public static final void init(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation(DRPCoreInfo.MODID,"sittable"), Sittable.class, "Sittable", 0, DarkRoleplayCore.instance, 10, 1, false);
	}
	
}

