package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.testing.skills.SkillHandler;
import net.dark_roleplay.core.testing.skills.SkillStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class DRPCoreCapabilities {
	
	
	/**
	 * TODO Move to Interface
	 */
	@CapabilityInject(SkillHandler.class)
	public static final Capability<SkillHandler> SKILL_HANDLER = null;
	
	public static void preInit(){
	}
	
	public static final void init(FMLInitializationEvent event) {
		CapabilityManager.INSTANCE.register(SkillHandler.class, new SkillStorage(), SkillHandler::new);
	}
}
