package net.dark_roleplay.drpcore.api.old.modules.materials;

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

import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.api.old.modules.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class Module_Material extends Module{

	public static final String WOOD_KEY = "wood";
	
	private static Map<String, List<Material>> materials = new HashMap<String, List<Material>>();
	
	public static Side side;
	
	public Module_Material(String name, Module... requiredModules) {
		super(name, requiredModules);
	}

	private static void copyFileForMaterials(String material, InputStream input, File dest){
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
		this.side = event.getSide();
		AddMaterials<MaterialWood> woods = new AddMaterials<MaterialWood>();
		MinecraftForge.EVENT_BUS.post(woods);
		List<Material> materialsWood = new ArrayList<Material>();
		for(MaterialWood wood : woods.getMaterials()){
			materialsWood.add(wood);
		}
		materials.put(WOOD_KEY, materialsWood);
		
		if(Modules.MATERIALS.side.isClient()){
			AddResourceGenerators resGens = new AddResourceGenerators();
			MinecraftForge.EVENT_BUS.post(resGens);
			ProgressBar bar = ProgressManager.push("Generating Resources", resGens.getResourceGenerators().size());
			for(ResourceGenerator resGen : resGens.getResourceGenerators()){
				bar.step(resGen.toString());
				if(materials.containsKey(resGen.getResourceType())){
					resGen.generate(materials.get(resGen.getResourceType()));
				}
			}
			ProgressManager.pop(bar);
		}
	}
	
	public List<Material> getMaterials(String type){
		if(materials.containsKey(type)){
			return materials.get(type);
		}else
			return new ArrayList<Material>();
	}
}
