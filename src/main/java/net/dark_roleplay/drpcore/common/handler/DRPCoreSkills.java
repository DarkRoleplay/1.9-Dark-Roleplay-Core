package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.skills.SkillTree;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.blocks.blueprint_controller.BlueprintController;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DRPCoreSkills {

	private static final SkillPoint point;
	
	private static final Skill skill0;
	private static final Skill skill1;
	private static final Skill skill2;
	private static final Skill skill3;
	private static final Skill skill4;
	private static final Skill skill5;
	private static final Skill skill6;
	private static final Skill skill7;
	private static final Skill skill8;	
	private static final Skill skill9;
	
	private static final SkillTree skillTree0;
	private static final SkillTree skillTree1;
	private static final SkillTree skillTree2;
	
	static{
		point = new SkillPoint(new ResourceLocation(DRPCoreInfo.MODID, "proffesion_point"), null);
		
		skillTree0 = new SkillTree(new ResourceLocation(DRPCoreInfo.MODID, "lumberjack"));
		skillTree1 = new SkillTree(new ResourceLocation(DRPCoreInfo.MODID, "miner"));
		skillTree2 = new SkillTree(new ResourceLocation(DRPCoreInfo.MODID, "debug"));

		skill0 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "lumberjack"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill0.addToSkillTree(skillTree0, 0, 2);
		
		skill1 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug0"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill1.addToSkillTree(skillTree0, 1, 2);
		
		skill2 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug1"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill2.addToSkillTree(skillTree0, 2, 1);
		
		skill3 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug2"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill3.addToSkillTree(skillTree0, 2, 3);
		
		skill4 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug3"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill4.addToSkillTree(skillTree1, 0, 2);
		
		skill5 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug4"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill5.addToSkillTree(skillTree1, 1, 2);
		
		skill6 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug5"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill6.addToSkillTree(skillTree1, 2, 2);
		
		skill7 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug6"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill7.addToSkillTree(skillTree2, 0, 2);
		
		skill8 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug7"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill8.addToSkillTree(skillTree2, 2, 2);
		
		skill9 = new Skill(new ResourceLocation(DRPCoreInfo.MODID, "debug8"), new ResourceLocation(DRPCoreInfo.MODID, "textures/skills/lumberjack.png"));
		skill9.addToSkillTree(skillTree2, 3, 2);
	}
	
	@SubscribeEvent
	public static final void register(RegistryEvent.Register<SkillPoint> event) {
//		event.getRegistry().register(point);
	}
	
	@SubscribeEvent
	public static final void register2(RegistryEvent.Register<Skill> event) {
		event.getRegistry().registerAll(
				skill0,
				skill1,
				skill2,
				skill3,
				skill4,
				skill5,
				skill6,
				skill7,
				skill8,
				skill9
				);
	}
}
