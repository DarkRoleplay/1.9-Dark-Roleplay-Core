package net.drpcore.common.capabilitiesOld;

import net.drpcore.common.capabilitiesOld.entities.player.moral.IMoral;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class DarkRoleplayCoreCapabilities {
	
	@CapabilityInject(IMoral.class)
    public static final Capability<IMoral> THIRST = null;
	
}
