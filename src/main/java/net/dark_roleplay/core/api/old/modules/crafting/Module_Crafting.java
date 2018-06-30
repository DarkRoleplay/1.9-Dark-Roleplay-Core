package net.dark_roleplay.core.api.old.modules.crafting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.dark_roleplay.core.api.old.crafting.simple_recipe.RecipeCategory;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.core.api.old.crafting2.IRecipe;
import net.dark_roleplay.core.api.old.modules.Module;
import net.dark_roleplay.core.common.crafting.CraftingRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class Module_Crafting extends Module{

	HashMap<String, RecipeCategory> categorys = new HashMap<String, RecipeCategory>();
	
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	public Module_Crafting(String name) {
		super(name);
	}

	public void init(FMLInitializationEvent event){
		
		Loader.instance().getActiveModList().forEach(mod ->{
			findFiles(mod, "data/" + mod.getModId() + "/drp/recipes", null,
					(root, file) -> {
						Loader.instance().setActiveModContainer(mod);
			
						String relative = root.relativize(file).toString();
						if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
							return true;
			
						String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
						ResourceLocation key = new ResourceLocation(mod.getModId(), name);
							
						BufferedReader reader = null;
						try {
							reader = Files.newBufferedReader(file);
							JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
							readRecipe(json);
						} catch (JsonParseException e) {
							FMLLog.log.error("Parsing error loading recipe {}", key, e);
							return false;
						} catch (IOException e) {
							FMLLog.log.error("Couldn't read recipe {} from {}", key, file, e);
							return false;
						} finally {
							IOUtils.closeQuietly(reader);
						}
						return true;
					},
				true, true);
		});
		
		for(String key : categorys.keySet()){
			CraftingRegistry.register(categorys.get(key));
		}
	}
	
	public void readRecipe(JsonObject obj){
		if(obj == null)
			return;
		
		SimpleRecipe rec;
		ItemStack[] output;
		JsonArray outputArr = obj.get("outputs").getAsJsonArray();
		output = new ItemStack[outputArr.size()];
		for(int i = 0; i < outputArr.size(); i++){
			output[i] = deserializeItem(outputArr.get(i).getAsJsonObject());
		}

		ItemStack[] input;
		JsonArray inputArr = obj.get("ingredients").getAsJsonArray();
		input = new ItemStack[inputArr.size()];
		for(int i = 0; i < inputArr.size(); i++){
			input[i] = deserializeItem(inputArr.get(i).getAsJsonObject());
		}
		
		rec = new SimpleRecipe(new ResourceLocation("custom:" + obj.get("name").getAsString()),output, input);
		
		String station = obj.has("station") ? obj.get("station").getAsString() : "minecraft:air";
		
		Block block = Block.REGISTRY.getObject(new ResourceLocation(station));
		
        if (block == null) {
        	block = Blocks.AIR;
        }
		
		String category = station.replace(":", ".") + "." + obj.get("category").getAsString();
		if(categorys.containsKey(category)){
			categorys.get(category).add(rec);
		}else{
			RecipeCategory cat = new RecipeCategory(block, category);
			cat.add(rec);
			categorys.put(category, cat);
		}
	}
	
	public static ItemStack deserializeItem(JsonObject stackObj) {
        String s = stackObj.get("item").getAsString();
        Item item = Item.REGISTRY.getObject(new ResourceLocation(s));

        if (item == null) {
//            throw new JsonSyntaxException("Unknown item '" + s + "'");
        	return ItemStack.EMPTY;
        }else{
            int i = stackObj.has("data") ? stackObj.get("data").getAsInt() : 0;
            int j = stackObj.has("count") ? stackObj.get("count").getAsInt() : 1;
            return new ItemStack(item, j, i);
        }
    }
	
	/**
	 * @link{Copied out of net.minecraftforge.common.craftingCraftingHelper.class}
	 * @param mod
	 * @param base
	 * @param preprocessor
	 * @param processor
	 * @param defaultUnfoundRoot
	 * @param visitAllFiles
	 * @return
	 */
	private static boolean findFiles(ModContainer mod, String base, Function<Path, Boolean> preprocessor, BiFunction<Path, Path, Boolean> processor, boolean defaultUnfoundRoot, boolean visitAllFiles) {
		FileSystem fs = null;
		try {
			File source = mod.getSource();

			Path root = null;
			if (source.isFile()) {
				try {
					fs = FileSystems.newFileSystem(source.toPath(), null);
					root = fs.getPath("/" + base);
				} catch (IOException e) {
					FMLLog.log.error("Error loading FileSystem from jar: ", e);
					return false;
				}
			} else if (source.isDirectory()) {
				root = source.toPath().resolve(base);
			}

			if (root == null || !Files.exists(root))
				return defaultUnfoundRoot;

			if (preprocessor != null) {
				Boolean cont = preprocessor.apply(root);
				if (cont == null || !cont.booleanValue())
					return false;
			}

			boolean success = true;

			if (processor != null) {
				Iterator<Path> itr = null;
				try {
					itr = Files.walk(root).iterator();
				} catch (IOException e) {
					FMLLog.log.error("Error iterating filesystem for: {}", mod.getModId(), e);
					return false;
				}

				while (itr != null && itr.hasNext()) {
					Boolean cont = processor.apply(root, itr.next());

					if (visitAllFiles) {
						success &= cont != null && cont;
					} else if (cont == null || !cont) {
						return false;
					}
				}
			}

			return success;
		} finally {
			IOUtils.closeQuietly(fs);
		}
	}
}
