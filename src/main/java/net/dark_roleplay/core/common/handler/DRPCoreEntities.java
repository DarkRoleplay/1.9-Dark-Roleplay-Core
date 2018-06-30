package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.modules.sitting.entities.Sittable;
import net.dark_roleplay.core.testing.Testing_Entity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = References.MODID)
public class DRPCoreEntities {

    private static int entityID = 0;
	
    @SubscribeEvent
	public static void register(RegistryEvent.Register<EntityEntry> e) {
		e.getRegistry().register(
			createBuilder("sittable").entity(Sittable.class).tracker(32, 1, false).build()
		);
		
		e.getRegistry().register(
			createBuilder("testing").entity(Testing_Entity.class).tracker(32, 1, false).build()
		);
	}
	
	private static <E extends Entity> EntityEntryBuilder<E> createBuilder(final String name) {
        final EntityEntryBuilder<E> builder = EntityEntryBuilder.create();
        final ResourceLocation registryName = new ResourceLocation(References.MODID, name);
        return builder.id(registryName, entityID++).name(References.MODID + "." + name);
    }
}

