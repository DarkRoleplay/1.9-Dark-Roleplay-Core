package net.drpcore.common.capabilities;

import net.drpcore.common.capabilities.entities.player.moral.IMoral;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class DRPCoreCapabilities {
	
	@CapabilityInject(IMoral.class)
	public static final Capability<IMoral> MORAL = null;
	
}
