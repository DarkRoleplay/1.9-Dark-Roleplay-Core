package net.drpcore.common;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.drpcore.client.events.ClientConnectedToServer;
import net.drpcore.client.events.ClientDisconnectFromServer;
import net.drpcore.common.blocks.DRPCoreBlocks;
import net.drpcore.common.config.ConfigurationManager;
import net.drpcore.common.entities.player.PlayerCapabilityController;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.AdvancedPlayerStorage;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.DefaultImplementation;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.IPlayerInventoryAdvanced;
import net.drpcore.common.events.AttachCapabilitiesEntity;
import net.drpcore.common.events.EntityJoinWorld;
import net.drpcore.common.events.LivingDrop;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.items.DebugFood1;
import net.drpcore.common.items.DebugPurse;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.proxy.CommonProxy;
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

@Mod(modid = DarkRoleplayCore.MODID, version = DarkRoleplayCore.VERSION, name = DarkRoleplayCore.NAME ,acceptedMinecraftVersions = DarkRoleplayCore.ACCEPTEDVERSIONS)
public class DarkRoleplayCore {
	
	public static final String NAME = "Dark Roleplay Core";
	public static final String VERSION = "0.1.3c";
	public static final String MODID = "drpcore";
	public static final String ACCEPTEDVERSIONS = "1.8,1.8.8,1.8.9";
	
	public static File schematicPath;
	
	public static final Logger log = LogManager.getLogger("Baubles");
	
	public static ConfigurationManager configManager;
	public static CraftingManager CM = new CraftingManager();
	
	@SidedProxy(clientSide ="net.drpcore.client.ClientProxy", serverSide ="net.drpcore.common.proxy.CommonProxy")
    public static CommonProxy proxy;
	
	@net.minecraftforge.fml.common.Mod.Instance(MODID)
    public static DarkRoleplayCore instance;
	
	@CapabilityInject(IPlayerInventoryAdvanced.class)
	public static final Capability<IPlayerInventoryAdvanced> DRPCORE_INV = null;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
		
		CapabilityManager.INSTANCE.register(IPlayerInventoryAdvanced.class, new AdvancedPlayerStorage() , DefaultImplementation.class);
		
		
		
		
		configManager = new ConfigurationManager(new File(event.getModConfigurationDirectory().getPath() + "\\Advanced Configuration"));
		schematicPath = configManager.CConfigFolder;
		proxy.checkForUpdates();
		GameRegistry.registerItem(new DebugFood1(), "DebugFood1");
		GameRegistry.registerItem(new DebugPurse(), "DebugPurse");
	}
	
	
	
	@Mod.EventHandler
    public void init(FMLInitializationEvent event){
		
		schematicPath = new File(configManager.getConfigFolder().getPath() +  "\\schematics");
		schematicPath.mkdir();
		
		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler()); 
		proxy.registerEvents();
		GameRegistry.registerBlock(DRPCoreBlocks.blockStructureManager,"blockStructureManager");
		registerEvents();
		
		SchematicController.findSchematics();
		SchematicController.debug();
		
		CM.RegisterRecipe(new CraftingRecipe(null, "TEST", new ItemStack(Items.bread,1), new ItemStack[]{new ItemStack(Blocks.planks,4)},new ItemStack[]{new ItemStack(Items.coal,1), new ItemStack(Items.iron_ingot)}){
			@Override
			public ItemStack getOutput(ItemStack[] addIngredients){
				if(addIngredients == null)
					return this.getDefaultOutput();
				else if(addIngredients.length == 1){
					return new ItemStack(Items.diamond,5);
				}else{
					return new ItemStack(Items.emerald,10);
				}
			}
		});
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	public static void registerEvents(){
		proxy.registerEvents();
		MinecraftForge.EVENT_BUS.register(new AttachCapabilitiesEntity());
		MinecraftForge.EVENT_BUS.register(new EntityJoinWorld());
		MinecraftForge.EVENT_BUS.register(new LivingDrop());
	}
}
