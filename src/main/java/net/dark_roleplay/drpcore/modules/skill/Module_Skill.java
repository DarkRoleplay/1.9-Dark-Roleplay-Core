package net.dark_roleplay.drpcore.modules.skill;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class Module_Skill extends Module{

	public Module_Skill(String name, Module... requiredModules) {
		super(name, requiredModules);
	}

	private static IForgeRegistry<Skill> registrySkills;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.NewRegistry event) {
		RegistryBuilder<Skill> rbSkills = new RegistryBuilder<Skill>();
		rbSkills.setName(new ResourceLocation(DRPCoreReferences.MODID, "skills"));
		rbSkills.setType(Skill.class);
		rbSkills.disableSaving();
		registrySkills = rbSkills.create();
	}
	
	public IForgeRegistry<Skill> getSkillRegistry(){
		return registrySkills;
	}
	
	public ISkillController getSkillController(EntityPlayer player){
		return player.hasCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null) ? player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null) : null;
	}
}
