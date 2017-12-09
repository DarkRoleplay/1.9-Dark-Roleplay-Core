package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.modules.skill.Skill;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DRPCoreSkills {
	
	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Skill> event) {
		event.getRegistry().registerAll(
				new Skill("skill_1"),
				new Skill("skill_2"),
				new Skill("skill_3"),
				new Skill("skill_4"),
				new Skill("skill_5")
		);
	}}
