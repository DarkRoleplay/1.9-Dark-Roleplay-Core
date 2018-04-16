package net.dark_roleplay.drpcore.testing.skills;

import net.dark_roleplay.drpcore.common.References;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = References.MODID)
@ObjectHolder(value = "drpcore")
public class SkillRegistries {

	protected static ForgeRegistry<SkillPoint> SKILL_POINTS = null;
	protected static ForgeRegistry<Skill> SKILLS = null;
	protected static ForgeRegistry<SkillTree> SKILL_TREES = null;
	
	protected static final SkillPoint TEST_POINT_1 = null;
	protected static final SkillPoint TEST_POINT_2 = null;

	@SubscribeEvent
	public static void register(RegistryEvent.NewRegistry event) {
		System.out.println("DEBUG SERACH ME");
		
		RegistryBuilder builder = new RegistryBuilder();
		builder.setType(SkillPoint.class);
		builder.setName(new ResourceLocation(References.MODID, "skill_points"));
		builder.allowModification();
		builder.disableSaving();
		builder.disableOverrides();
		SKILL_POINTS = (ForgeRegistry<SkillPoint>) builder.create();
		
		RegistryBuilder builderSkills = new RegistryBuilder();
		builderSkills.setType(Skill.class);
		builderSkills.setName(new ResourceLocation(References.MODID, "skills"));
		builderSkills.allowModification();
		builderSkills.disableSaving();
		builderSkills.disableOverrides();
		SKILLS = (ForgeRegistry<Skill>) builderSkills.create();

		RegistryBuilder builderSkillTrees = new RegistryBuilder();
		builderSkillTrees.setType(SkillTree.class);
		builderSkillTrees.setName(new ResourceLocation(References.MODID, "skill_trees"));
		builderSkillTrees.allowModification();
		builderSkillTrees.disableSaving();
		builderSkillTrees.disableOverrides();
		SKILL_TREES = (ForgeRegistry<SkillTree>) builderSkillTrees.create();
	}
	

	@SubscribeEvent
	public static void registerSkillPoints(RegistryEvent.Register<SkillPoint> e) {
		e.getRegistry().registerAll(
			new SkillPoint().setRegistryName("drpcore:test_point_1"),
			new SkillPoint().setRegistryName("drpcore:test_point_2")
		);
	}
	
	@SubscribeEvent
	public static void registerSkill(RegistryEvent.Register<Skill> e) {
		e.getRegistry().registerAll(
			new Skill("drpcore:test_1", TEST_POINT_1),
			new Skill("drpcore:test_2", TEST_POINT_2),
			new Skill("drpcore:test_3", TEST_POINT_1, TEST_POINT_2)
		);
	}
}
