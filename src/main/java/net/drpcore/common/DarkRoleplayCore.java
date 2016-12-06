package net.drpcore.common;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.drpcore.api.blocks.AdvancedCrop;
import net.drpcore.api.blocks.AdvancedPlant;
import net.drpcore.client.events.ClientConnectedToServer;
import net.drpcore.client.events.ClientDisconnectFromServer;
import net.drpcore.client.keybinding.DRPCoreKeybindings;
import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.blocks.DRPCoreBlocks;
import net.drpcore.common.blocks.tileentities.AdvancedPlantTileEntity;
import net.drpcore.common.capabilities.IRecipeController;
import net.drpcore.common.capabilities.RecipeControlerFactory;
import net.drpcore.common.capabilities.RecipeControllerDefault;
import net.drpcore.common.capabilities.RecipeControllerStorage;
import net.drpcore.common.capabilitiesOld.DRPCoreCapabilities;
import net.drpcore.common.capabilitiesOld.entities.player.advancedInventory.AdvancedInventoryStorage;
import net.drpcore.common.capabilitiesOld.entities.player.advancedInventory.DefaultAdvancedInventory;
import net.drpcore.common.capabilitiesOld.entities.player.advancedInventory.IPlayerAdvancedInventory;
import net.drpcore.common.config.ConfigurationManager;
import net.drpcore.common.config.DRPCoreConfig;
import net.drpcore.common.crafting.CraftingRegistry;
import net.drpcore.common.crafting.SimpleRecipe;
import net.drpcore.common.craftingOld.AdvancedRecipe;
import net.drpcore.common.craftingOld.DRPCoreCrafting;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = DarkRoleplayCore.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATECHECK, useMetadata = true)
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

	@CapabilityInject(IPlayerAdvancedInventory.class)
	public static final Capability<IPlayerAdvancedInventory> DRPCORE_INV = null;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		CraftingRegistry.INSTANCE = new CraftingRegistry();
		
		UpdateCheck.checkForUpdate();
		DRPCoreBlocks.preInit(event);
		DRPCoreItems.preInit(event);
		DRPCoreConfig.preInit(event);
		DRPCoreEvents.preInit(event);
		DRPCoreCrafting.preInit(event);
		MinecraftForge.EVENT_BUS.register(new DRPCoreCapabilities());
		
		if(event.getSide() == Side.CLIENT)
			DRPCoreKeybindings.preInit(event);
		
		CapabilityManager.INSTANCE.register(IPlayerAdvancedInventory.class, AdvancedInventoryStorage.inventoryStorage, DefaultAdvancedInventory.class);
		isServer = event.getSide();
		configManager = new ConfigurationManager(new File(event.getModConfigurationDirectory().getPath() + "\\Advanced Configuration"));
		schematicPath = configManager.CConfigFolder;
		
		CapabilityManager.INSTANCE.register(IRecipeController.class, new RecipeControllerStorage(), new RecipeControlerFactory());
		
		log.info("Pre Initialization has been Finished!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		DRPCoreBlocks.init(event);
		DRPCoreItems.init(event);
		DRPCoreConfig.init(event);
		DRPCoreEvents.init(event);
		DRPCoreCrafting.init(event);
		if(event.getSide() == Side.CLIENT)
			DRPCoreKeybindings.init(event);
		schematicPath = new File(configManager.getConfigFolder().getPath() + "\\schematics");
		schematicPath.mkdir();
		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		proxy.registerEvents();
		
		log.info("Initialization has been Finished!");
		
        GameRegistry.registerTileEntity(AdvancedPlantTileEntity.class, DarkRoleplayCore.MODID + "AdvancedCrop");
		
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		CraftingRegistry.INSTANCE.registerRecipe(Blocks.AIR, "TEST", "drpcore:test", new SimpleRecipe("drpcore:test", new ItemStack[]{new ItemStack(Items.ARROW)}, new ItemStack[]{new ItemStack(Items.STONE_SWORD)}));
		CraftingRegistry.INSTANCE.registerRecipe(Blocks.AIR, "TEST", "drpcore:test2", new SimpleRecipe("drpcore:test2", new ItemStack[]{new ItemStack(Items.IRON_SHOVEL)}, new ItemStack[]{new ItemStack(Items.STONE_SWORD)}));
		
		
		DRPCoreBlocks.postInit(event);
		DRPCoreItems.postInit(event);
		DRPCoreConfig.postInit(event);
		DRPCoreEvents.postInit(event);
		DRPCoreCrafting.postInit(event);
		
		if(event.getSide() == Side.CLIENT)
			DRPCoreKeybindings.postInit(event);
		
		
		log.info("Post Initialization has been Finished!");
	}
}
