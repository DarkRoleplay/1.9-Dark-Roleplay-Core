package net.dark_roleplay.drpcore.common;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.dark_roleplay.drpcore.common.commands.crafting.Command_Recipe;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.dark_roleplay.drpcore.common.events.capabilities.Event_CapabilityEntity;
import net.dark_roleplay.drpcore.common.events.entity.player.Event_PlayerClone;
import net.dark_roleplay.drpcore.common.events.entity.player.Event_PlayerLoggedIn;
import net.dark_roleplay.drpcore.common.handler.DRPCoreBlocks;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEvents;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.dark_roleplay.drpcore.common.skills.SkillItem;
import net.dark_roleplay.drpcore.common.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.dark_roleplay.drpcore.common.skills.SkillTree;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = DRPCoreInfo.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATECHECK, guiFactory = DRPCoreInfo.CONFIG_GUI_FACTORY)
public class DarkRoleplayCore {

	public static File configDir;
	
	public static final Logger LOGGER = LogManager.getLogger(DRPCoreInfo.MODID);

	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@net.minecraftforge.fml.common.Mod.Instance(DRPCoreInfo.MODID)
	public static DarkRoleplayCore instance;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		SyncedConfigRegistry.setSide(event.getSide());
		
		configDir = event.getSuggestedConfigurationFile();
		DRPCoreConfigs.loadConfig(event.getSuggestedConfigurationFile());

		DRPCoreItems.preInit(event);
		DRPCoreBlocks.preInit(event);
		DRPCoreCapabilities.preInit(event);
		DRPCoreGuis.preInit(event);
		DRPCoreEvents.preInit(event);
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//TODO Create EVENT HANDLER
		FMLCommonHandler.instance().bus().register(new Event_ConfigChange());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerClone());
		MinecraftForge.EVENT_BUS.register(new Event_CapabilityEntity());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerLoggedIn());
		
		DRPCoreItems.init(event);
		DRPCoreBlocks.init(event);
		DRPCoreCapabilities.init(event);
		DRPCoreGuis.init(event);
		DRPCorePackets.init();
		DRPCoreEvents.init(event);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreItems.postInit(event);
		DRPCoreBlocks.postInit(event);
		DRPCoreCapabilities.postInit(event);
		DRPCoreGuis.postInit(event);
		DRPCoreEvents.postInit(event);
		proxy.postInit(event);
		
//		CraftingRegistry.registerRecipe(Blocks.AIR, "test", new SimpleRecipe(new ResourceLocation(DRPCoreInfo.MODID, "test"), new ItemStack[]{new ItemStack(Items.APPLE)}, new ItemStack[]{new ItemStack(Items.ARROW)}), false);
		
//		SkillTree craftingTree = new SkillTree("drpcore:debug_skill_tree", "drpcore:debug_skill_tree", null);
//		SkillPoint carpenterPoint = new SkillPoint(new ResourceLocation(DRPCoreInfo.MODID, "carpenterPoint"));
//		
//		SkillItem craftSkill1 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_1"), "drpcore:debug_skill_1", Items.APPLE, carpenterPoint, 1, 1);
//		SkillItem craftSkill2 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_2"), "drpcore:debug_skill_2", Items.ARROW, carpenterPoint, 2, 1);
//		SkillItem craftSkill3 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_3"), "drpcore:debug_skill_3", Items.BAKED_POTATO, carpenterPoint, 3, 1);
//		SkillItem craftSkill4 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_4"), "drpcore:debug_skill_4", Items.BED, carpenterPoint, 1, 3);
//		SkillItem craftSkill5 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_5"), "drpcore:debug_skill_5", Items.BEEF, carpenterPoint, 2, 3);
//		SkillItem craftSkill6 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_6"), "drpcore:debug_skill_6", Items.BEETROOT, carpenterPoint, 3, 3);
//		SkillItem craftSkill7 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_7"), "drpcore:debug_skill_7", Items.BLAZE_POWDER, carpenterPoint, 1, 5);
//		SkillItem craftSkill8 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_8"), "drpcore:debug_skill_8", Items.CAKE, carpenterPoint, 2, 5);
//		SkillItem craftSkill9 = new SkillItem(new ResourceLocation(DRPCoreInfo.MODID, "debug_skill_9"), "drpcore:debug_skill_9", Items.COMPASS, carpenterPoint, 3, 5);
//		
//		craftSkill9.addParent(craftSkill8);
//		craftSkill8.addParent(craftSkill7);
//		craftSkill7.addParent(craftSkill6);
//		craftSkill6.addParent(craftSkill5);
//		craftSkill5.addParent(craftSkill4);
//		craftSkill4.addParent(craftSkill3);
//		craftSkill3.addParent(craftSkill2);
//		craftSkill2.addParent(craftSkill1);
//		
//		craftingTree.addSkill(craftSkill1);
//		craftingTree.addSkill(craftSkill2);
//		craftingTree.addSkill(craftSkill3);
//		craftingTree.addSkill(craftSkill4);
//		craftingTree.addSkill(craftSkill5);
//		craftingTree.addSkill(craftSkill6);
//		craftingTree.addSkill(craftSkill7);
//		craftingTree.addSkill(craftSkill8);
//		craftingTree.addSkill(craftSkill9);
//		
//		SkillRegistry.registerSkillTree(craftingTree);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new Command_Recipe());
	}
}
