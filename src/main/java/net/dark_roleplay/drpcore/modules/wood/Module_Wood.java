package net.dark_roleplay.drpcore.modules.wood;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.modules.Module;
import net.dark_roleplay.drpcore.modules.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class Module_Wood  extends Module{

	public Module_Wood(String name, Module... requiredModules) {
		super(name, requiredModules);
	}
	
	private static IForgeRegistry<Wood> registryWoods;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.NewRegistry event) {
		RegistryBuilder<Wood> rbWoods = new RegistryBuilder<Wood>();
		rbWoods.setName(new ResourceLocation(DRPCoreReferences.MODID, "woods"));
		rbWoods.setType(Wood.class);
		rbWoods.disableSaving();
		registryWoods = rbWoods.create();
	}
	
	public IForgeRegistry<Wood> getSkillRegistry(){
		return registryWoods;
	}
}
