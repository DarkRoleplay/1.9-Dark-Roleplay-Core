package net.dark_roleplay.core.common.handler;

import java.util.concurrent.Callable;

import net.dark_roleplay.core.modules.locks.capabilities.ILockHandler;
import net.dark_roleplay.core.modules.locks.capabilities.LockStorage;
import net.dark_roleplay.core.testing.skills.SkillHandler;
import net.dark_roleplay.core.testing.skills.SkillStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class DRPCoreCapabilities {
	
	
	@CapabilityInject(ILockHandler.class)
	public static final Capability<ILockHandler> LOCK_HANDLER = null;
	
	/**
	 * TODO Move to Interface
	 */
	@CapabilityInject(SkillHandler.class)
	public static final Capability<SkillHandler> SKILL_HANDLER = null;
	
	public static void preInit(){
	}
	
	public static final void init(FMLInitializationEvent event) {
		CapabilityManager.INSTANCE.register(ILockHandler.class, new LockStorage(), (Callable<ILockHandler>)() -> {return new ILockHandler.Impl();});
		CapabilityManager.INSTANCE.register(SkillHandler.class, new SkillStorage(), (Callable<SkillHandler>)() -> {return new SkillHandler();});

	}
}
