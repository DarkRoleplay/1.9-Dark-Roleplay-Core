package net.dark_roleplay.drpcore.modules.materials;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import net.dark_roleplay.drpcore.modules.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Module_Material extends Module{

	public static final String WOOD_KEY = "wood";
	
	private static Map<String, List<Material>> materials = new HashMap<String, List<Material>>();
	
	public Module_Material(String name, Module... requiredModules) {
		super(name, requiredModules);
	}

	private static void coypFileForMaterials(String material, InputStream input, File dest){
		if(!materials.containsKey(material) || dest.exists())
			return;

	    Charset charset = StandardCharsets.UTF_8;
	    String content;
		try {
			content = IOUtils.toString(input, charset);
			input.close();
			dest.getParentFile().mkdirs();
			for(Material mat : materials.get(material)){
			   String base = mat.getFormatted(content);
			   File woodDest = new File(mat.getFormatted(dest.getPath()));
			   Files.write(woodDest.toPath(), base.getBytes(charset));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event){
		AddMaterials<MaterialWood> woods = new AddMaterials<MaterialWood>();
		MinecraftForge.EVENT_BUS.post(woods);
		List<Material> materialsWood = new ArrayList<Material>();
		for(MaterialWood wood : woods.getMaterials()){
			materialsWood.add(wood);
		}
		materials.put(WOOD_KEY, materialsWood);
		

//		MinecraftForge.EVENT_BUS.post(new AddMaterialObjects.AddWoodBlocks());
//		MinecraftForge.EVENT_BUS.post(new AddMaterialObjects.AddWoodItems());
//		for(WoodenBlock block : woodenBlocks){
//			
//			IResource blockState = ClientProxy.getResource(block.getBaseBlockState());
//			InputStream blockStateInput = blockState.getInputStream();
//				
//			String[] nameInformation = block.getRegistryName().split(":");
//			String domain = nameInformation[0];
//			String regName = nameInformation[1];
//			 
//			coypFileForWoods(blockStateInput, new File(DRPCoreReferences.DARK_ROLEPLAY_ARGH.getPath() + "/" + domain + "/blockstates/" + regName + ".json"));
//			
//			TextureGenerator texGen = new TextureGenerator(ClientProxy.getResourceAsJson(block.getTextureGenerator()));
//			texGen.generateTextures(woods, barkTextures, logTopTextures, plankTextures, cleanPlankTextures);
//			
//			ModelGenerator modelGen = new ModelGenerator(ClientProxy.getResourceAsJson(block.getModelGenerator()));
//			modelGen.generateModels(woods);
//		}		
	}
}
