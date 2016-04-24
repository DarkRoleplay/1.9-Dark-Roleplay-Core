package net.drpcore.common;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.drpcore.client.events.ClientConnectedToServer;
import net.drpcore.client.events.ClientDisconnectFromServer;
import net.drpcore.client.keybinding.DRPCoreKeybindings;
import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.blocks.DRPCoreBlocks;
import net.drpcore.common.config.ConfigurationManager;
import net.drpcore.common.config.DRPCoreConfig;
import net.drpcore.common.crafting.DRPCoreCrafting;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.AdvancedPlayerStorage;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.DefaultImplementation;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.IPlayerInventoryAdvanced;
import net.drpcore.common.events.AttachCapabilitiesEntity;
import net.drpcore.common.events.DRPCoreEvents;
import net.drpcore.common.events.EntityJoinWorld;
import net.drpcore.common.events.LivingDrop;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.items.DRPCoreItems;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.proxy.CommonProxy;
import net.drpcore.common.util.DRPCoreInfo;
import net.drpcore.common.util.crafting.CraftingManager;
import net.drpcore.common.util.crafting.CraftingRecipe;
import net.drpcore.common.util.schematic.SchematicController;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = DarkRoleplayCore.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS , updateJSON = DRPCoreInfo.UPDATECHECK , useMetadata = false)
public class DarkRoleplayCore {

	public static final String MODID = "drpcore";

	@net.minecraftforge.fml.common.Mod.Instance(MODID)
	public static DarkRoleplayCore instance;
	
	@SidedProxy(clientSide = "net.drpcore.client.ClientProxy", serverSide = "net.drpcore.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static File schematicPath;

	public static final Logger log = LogManager.getLogger("Dark Roleplay Core");

	public static Side isServer;

	public static ConfigurationManager configManager;
	public static CraftingManager CM = new CraftingManager();

	@CapabilityInject(IPlayerInventoryAdvanced.class)
	public static final Capability<IPlayerInventoryAdvanced> DRPCORE_INV = null;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		UpdateCheck.checkForUpdate();
		
		DRPCoreBlocks.preInit(event);

		DRPCoreItems.preInit(event);

		DRPCoreConfig.preInit(event);

		DRPCoreEvents.preInit(event);

		DRPCoreCrafting.preInit(event);

		DRPCoreKeybindings.preInit(event);

		CapabilityManager.INSTANCE.register(IPlayerInventoryAdvanced.class, new AdvancedPlayerStorage(), DefaultImplementation.class);

		isServer = event.getSide();

		configManager = new ConfigurationManager(new File(event.getModConfigurationDirectory().getPath() + "\\Advanced Configuration"));
		schematicPath = configManager.CConfigFolder;
		
		log.info("Pre Initialization has been Finished!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		DRPCoreBlocks.init(event);

		DRPCoreItems.init(event);

		DRPCoreConfig.init(event);

		DRPCoreEvents.init(event);

		DRPCoreCrafting.init(event);

		DRPCoreKeybindings.init(event);

		schematicPath = new File(configManager.getConfigFolder().getPath() + "\\schematics");
		schematicPath.mkdir();

		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		SchematicController.findSchematics();
		SchematicController.debug();
		
		log.info("Initialization has been Finished!");

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		DRPCoreBlocks.postInit(event);

		DRPCoreItems.postInit(event);

		DRPCoreConfig.postInit(event);

		DRPCoreEvents.postInit(event);

		DRPCoreCrafting.postInit(event);

		DRPCoreKeybindings.postInit(event);
		
		log.info("Post Initialization has been Finished!");
	}
}
