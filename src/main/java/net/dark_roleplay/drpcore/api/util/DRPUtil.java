package net.dark_roleplay.drpcore.api.util;

import javax.annotation.Nullable;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.skills.SkillTree;
import net.dark_roleplay.drpcore.api.skills.SkillTreeData;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry.AddCallback;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

@Mod.EventBusSubscriber
public class DRPUtil {

	private static IForgeRegistry<SkillPoint> registrySkillPoints;
	private static IForgeRegistry<Skill> registrySkills;
	private static IForgeRegistry<SkillTree> registrySkillTrees;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.NewRegistry event) {
		RegistryBuilder<SkillPoint> rbSkillPoints = new RegistryBuilder<SkillPoint>();
		rbSkillPoints.setName(new ResourceLocation(DRPCoreInfo.MODID, "skill_points"));
		rbSkillPoints.setType(SkillPoint.class);
		registrySkillPoints = rbSkillPoints.create();
		
		RegistryBuilder<Skill> rbSkills = new RegistryBuilder<Skill>();
		rbSkills.setName(new ResourceLocation(DRPCoreInfo.MODID, "skills"));
		rbSkills.setType(Skill.class);
		rbSkills.add((AddCallback<Skill>)((IForgeRegistryInternal<Skill> owner, RegistryManager stage, int id, Skill obj, @Nullable Skill oldObj) -> {
			for(SkillTreeData treeData : obj.getSkillTrees()){
				SkillTree tree = treeData.getSkillTree();
				if(!registrySkillTrees.containsValue(tree)){
					registrySkillTrees.register(tree);
				}
				tree.addSkill(obj);
			}
		}));
		registrySkills = rbSkills.create();
		
		RegistryBuilder<SkillTree> rbSkillTrees = new RegistryBuilder<SkillTree>();
		rbSkillTrees.setName(new ResourceLocation(DRPCoreInfo.MODID, "skill_trees"));
		rbSkillTrees.setType(SkillTree.class);
		rbSkillTrees.disableSaving();

		registrySkillTrees = rbSkillTrees.create();
	}
	
	public static IForgeRegistry<SkillPoint> getRegistrySkillPoints() {
		return registrySkillPoints;
	}
	
	public static IForgeRegistry<Skill> getRegistrySkills() {
		return registrySkills;
	}
	
	public static IForgeRegistry<SkillTree> getRegistrySkillTrees() {
		return registrySkillTrees;
	}
}
