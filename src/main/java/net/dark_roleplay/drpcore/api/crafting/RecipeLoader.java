package net.dark_roleplay.drpcore.api.crafting;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RecipeLoader {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
    private static Map<String, IRecipeLoader> recipeLoaders = new HashMap<String, IRecipeLoader>();
    
    public static List<String> debugList = new ArrayList<String>();
    
	public static void loadRecipes() {

		Loader.instance().getActiveModList().forEach(RecipeLoader::loadRecipes);
	}

	private static boolean loadRecipes(ModContainer mod) {
		JsonContext ctx = new JsonContext(mod.getModId());

		return findFiles(mod, "data/" + mod.getModId() + "/drp/recipes", null,
			(root, file) -> {
				Loader.instance().setActiveModContainer(mod);
	
				String relative = root.relativize(file).toString();
				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
					return true;
	
				String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
				ResourceLocation key = new ResourceLocation(ctx.getModId(), name);
	
				debugList.add(file.getFileName().toString());
				
//				BufferedReader reader = null;
//				try {
//					reader = Files.newBufferedReader(file);
//					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
//					if(!json.has("type"))
//						return true;
//					
//					String loader = json.get("type").getAsString();
//					if(!recipeLoaders.containsKey(loader))
//						return true;
//					
//					
////					recipeLoaders.get(loader).loadRecipe(obj)
////					if (json.has("conditions")
////							&& !CraftingHelper.processConditions(JsonUtils.getJsonArray(json, "conditions"), ctx))
////						return true;
////					IRecipe recipe = CraftingHelper.getRecipe(json, ctx);
////					ForgeRegistries.RECIPES.register(recipe.setRegistryName(key));
//				} catch (JsonParseException e) {
//					FMLLog.log.error("Parsing error loading recipe {}", key, e);
//					return false;
//				} catch (IOException e) {
//					FMLLog.log.error("Couldn't read recipe {} from {}", key, file, e);
//					return false;
//				} finally {
//					IOUtils.closeQuietly(reader);
//				}
				return true;
			},
		true, true);
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
	private static boolean findFiles(ModContainer mod, String base, Function<Path, Boolean> preprocessor,
			BiFunction<Path, Path, Boolean> processor, boolean defaultUnfoundRoot, boolean visitAllFiles) {
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
