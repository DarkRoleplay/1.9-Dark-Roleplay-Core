package net.dark_roleplay.core.modules.date.events;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.core.modules.date.capabilities.IDateHandler;
import net.dark_roleplay.library.capabilities.CapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class EventManager {
	
	@SubscribeEvent
    public static void attachCapabilityWorld(AttachCapabilitiesEvent<World> event){
        if (!(event.getObject() instanceof World)) return;
        
        event.addCapability(new ResourceLocation(References.MODID, "date_handler"), new CapabilityProvider<IDateHandler>(DRPCoreCapabilities.DATE_HANDLER));
	}
}
