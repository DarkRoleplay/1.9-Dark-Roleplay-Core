package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.objects.entities.util.sitting.Sittable;
import net.dark_roleplay.drpcore.testing.Testing_Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class DRPCoreEntities {

	public static final void init(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation(DRPCoreReferences.MODID,"sittable"), Sittable.class, "Sittable", 0, DarkRoleplayCore.instance, 10, 1, false);
//		EntityRegistry.registerModEntity(new ResourceLocation(DRPCoreReferences.MODID,"testing"), Testing_Entity.class, "testing", 1, DarkRoleplayCore.instance, 10, 1, false);
	}
	
}

