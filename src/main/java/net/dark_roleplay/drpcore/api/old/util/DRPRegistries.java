package net.dark_roleplay.drpcore.api.old.util;

import net.dark_roleplay.drpcore.api.old.modules.hud.Hud;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class DRPRegistries {

	private static IForgeRegistry<Hud> registryHUDs;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.NewRegistry event) {
//		RegistryBuilder<SkillPoint> rbSkillPoints = new RegistryBuilder<SkillPoint>();
//		rbSkillPoints.setName(new ResourceLocation(DRPCoreReferences.MODID, "skill_points"));
//		rbSkillPoints.setType(SkillPoint.class);
//		registrySkillPoints = rbSkillPoints.create();
		

		RegistryBuilder<Hud> rbHuds = new RegistryBuilder<Hud>();
		rbHuds.setName(new ResourceLocation(References.MODID, "huds"));
		rbHuds.setType(Hud.class);
		rbHuds.disableSaving();
		registryHUDs = rbHuds.create();
		

//		rbSkills.add((AddCallback<Skill>)((IForgeRegistryInternal<Skill> owner, RegistryManager stage, int id, Skill obj, @Nullable Skill oldObj) -> {
//			for(SkillTreeData treeData : obj.getSkillTrees()){
//				SkillTree tree = treeData.getSkillTree();
//				if(!registrySkillTrees.containsValue(tree)){
//					registrySkillTrees.register(tree);
//				}
//				tree.addSkill(obj);
//			}
//		}));
		
//		RegistryBuilder<SkillTree> rbSkillTrees = new RegistryBuilder<SkillTree>();
//		rbSkillTrees.setName(new ResourceLocation(DRPCoreReferences.MODID, "skill_trees"));
//		rbSkillTrees.setType(SkillTree.class);
//		rbSkillTrees.disableSaving();
//
//		registrySkillTrees = rbSkillTrees.create();
	}
	
	public static IForgeRegistry<Hud> getHudRegistry() {
		return registryHUDs;
	}
}
