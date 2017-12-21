package net.dark_roleplay.drpcore.modules.wood;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import net.dark_roleplay.drpcore.api.Modules;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.modules.Module;
import net.dark_roleplay.drpcore.modules.skill.Skill;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class Module_Wood  extends Module{

	private static final List<Wood> woods = new ArrayList<Wood>();
	private static final Map<String, Wood> woodMap = new HashMap<String, Wood>();
	private static final List<WoodenBlock> woodenBlocks = new ArrayList<WoodenBlock>();
	
	private static Map<String, BufferedImage> barkTextures = new HashMap<String, BufferedImage>();
	private static Map<String, BufferedImage> logTopTextures = new HashMap<String, BufferedImage>();
	private static Map<String, BufferedImage> plankTextures = new HashMap<String, BufferedImage>();
	private static Map<String, BufferedImage> cleanPlankTextures = new HashMap<String, BufferedImage>();
	
	public Module_Wood(String name, Module... requiredModules) {
		super(name, requiredModules);
	}

	
	public void addWoodType(Wood wood){
		if(!woodMap.containsKey(wood.getName())){
			woods.add(wood);
			woodMap.put(wood.name, wood);
		}
	}
	
	public void addWoodenBlock(WoodenBlock block){
		woodenBlocks.add(block);
	}


	public List<Wood> getWoods() {
		return woods;
	}


	public Map<String, Wood> getWoodMap() {
		return woodMap;
	}


	public List<WoodenBlock> getWoodenBlocks() {
		return woodenBlocks;
	}

	private static void coypFileForWoods(InputStream input, File dest){
		if(dest.exists())
			return;

	    Charset charset = StandardCharsets.UTF_8;
	    String content;
		try {
			content = IOUtils.toString(input, charset);
			dest.getParentFile().mkdirs();
			for(Wood wood : woods){
			   String base = content.replaceAll("%wood%", wood.getName());
			   File woodDest = new File(dest.getPath().replaceAll("%wood%", wood.getName()));
			   Files.write(woodDest.toPath(), base.getBytes(charset));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		DarkRoleplayCore.EVENT_BUS.post(new Event_AddWoods());
		
		Modules.WOODS.addWoodType(new Wood("oak")
			.setBarkTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/bark_oak.png"))
			.setLogTopTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/log_oak_top.png"))
			.setPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/planks_oak.png"))
			.setCleanPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/clean_planks_oak.png"))
		);
			
		Modules.WOODS.addWoodType(new Wood("spruce")
			.setBarkTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/bark_spruce.png"))
			.setLogTopTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/log_spruce_top.png"))
			.setPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/planks_spruce.png"))
			.setCleanPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/clean_planks_spruce.png"))
		);
			
		Modules.WOODS.addWoodType(new Wood("birch")
			.setBarkTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/bark_birch.png"))
			.setLogTopTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/log_birch_top.png"))
			.setPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/planks_birch.png"))
			.setCleanPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/clean_planks_birch.png"))
		);
		
		Modules.WOODS.addWoodType(new Wood("jungle")
			.setBarkTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/bark_jungle.png"))
			.setLogTopTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/log_jungle_top.png"))
			.setPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/planks_jungle.png"))
			.setCleanPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/clean_planks_jungle.png"))
		);
			
		Modules.WOODS.addWoodType(new Wood("acacia")
			.setBarkTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/bark_acacia.png"))
			.setLogTopTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/log_acacia_top.png"))
			.setPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/planks_acacia.png"))
			.setCleanPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/clean_planks_acacia.png"))
		);
				
		Modules.WOODS.addWoodType(new Wood("dark_oak")
			.setBarkTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/bark_dark_oak.png"))
			.setLogTopTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/log_dark_oak_top.png"))
			.setPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/planks_dark_oak.png"))
			.setCleanPlankTexture(new ResourceLocation(DRPCoreReferences.MODID, "argh/textures/wood/clean_planks_dark_oak.png"))
		);
		

		DarkRoleplayCore.EVENT_BUS.post(new Event_AddBlocks());
		
		ProgressBar progressBar = ProgressManager.push("Loading Wood Textures", woods.size());

		for(Wood wood : woods){
			try {
				progressBar.step(wood.getName());
				barkTextures.put(wood.getName(), ImageIO.read(ClientProxy.getResource(wood.getBarkTexture()).getInputStream()));
				logTopTextures.put(wood.getName(), ImageIO.read(ClientProxy.getResource(wood.getLogTopTexture()).getInputStream()));
				plankTextures.put(wood.getName(), ImageIO.read(ClientProxy.getResource(wood.getPlankTexture()).getInputStream()));
				cleanPlankTextures.put(wood.getName(), ImageIO.read(ClientProxy.getResource(wood.getCleanPlankTexture()).getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ProgressManager.pop(progressBar);
		
		progressBar = ProgressManager.push("Generating Resourcers for Wooden Blocks", woodenBlocks.size());
		for(WoodenBlock block : woodenBlocks){
			progressBar.step(block.getRegistryName());
			
			IResource blockState = ClientProxy.getResource(block.getBaseBlockState());
			InputStream blockStateInput = blockState.getInputStream();
				
			String[] nameInformation = block.getRegistryName().split(":");
			String domain = nameInformation[0];
			String regName = nameInformation[1];
			 
			coypFileForWoods(blockStateInput, new File(DRPCoreReferences.DARK_ROLEPLAY_ARGH.getPath() + "/" + domain + "/blockstates/" + regName + ".json"));
			
			TextureGenerator texGen = new TextureGenerator(ClientProxy.getResourceAsJson(block.getTextureGenerator()));
			texGen.generateTextures(woods, barkTextures, logTopTextures, plankTextures, cleanPlankTextures);
			
		}
		ProgressManager.pop(progressBar);
		
		barkTextures = null;
		logTopTextures = null;
		plankTextures = null;
		cleanPlankTextures = null;
		
	}
}
