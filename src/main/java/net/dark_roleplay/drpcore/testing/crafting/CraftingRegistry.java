package net.dark_roleplay.drpcore.testing.crafting;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.testing.crafting.factories.IngredientFactory;
import net.dark_roleplay.drpcore.testing.crafting.factories.RecipeFactory;
import net.dark_roleplay.drpcore.testing.crafting.impl.SimpleRecipe;
import net.dark_roleplay.library.resources.ResourceHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = References.MODID)
public class CraftingRegistry {
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().setPrettyPrinting().create();
	
	//TODO Make private!
	public static List<Recipe> recipes = new ArrayList<Recipe>();

	private static boolean hasLoadedCustomRecipes = false;
	private static boolean hasLoadedIDs = false;
	private static File worldFolder = null;
	
	private static IForgeRegistry<IngredientFactory> ingredientFactoryRegistry;
	private static IForgeRegistry<RecipeFactory> recipeFactoryRegistry;

	@SubscribeEvent
	public static void registerRegistries(RegistryEvent.NewRegistry reg) {
		RegistryBuilder builder = new RegistryBuilder();
		builder.disableSaving();
		builder.setName(new ResourceLocation(References.MODID, "ingredient_factories"));
		builder.setType(IngredientFactory.class);
		builder.disableOverrides();
		ingredientFactoryRegistry = builder.create();
		
		builder.setName(new ResourceLocation(References.MODID, "recipe_factories"));
		builder.setType(RecipeFactory.class);
		recipeFactoryRegistry = builder.create();
	}
	
	@SubscribeEvent
	public static void registerRecipeFactories(RegistryEvent.Register<RecipeFactory> e) {
		e.getRegistry().register(new SimpleRecipe.Factory().setRegistryName(References.MODID, "simple_recipe"));
	}
	
	@SubscribeEvent
	public static void registerIngredientFacotires(RegistryEvent.Register<IngredientFactory> e) {
//		e.getRegistry().register(new SimpleRecipe.Factory());
	}
	
	public static List<Recipe> getRecipesForStation(Block station){
		return recipes.parallelStream().filter(r -> r.isAllowedCraftingStation(station)).collect(Collectors.toList());
	}
	
	
	
	public static void loadModRecipes() {
		
		Loader.instance().getActiveModList().forEach(mod ->{
			ResourceHelper.findFiles(mod, "data/" + mod.getModId() + "/drp/recipes2", null,
					(root, file) -> {
						Loader.instance().setActiveModContainer(mod);
			
						String relative = root.relativize(file).toString();
						if (!"json".equals(FilenameUtils.getExtension(file.toString())))
							return true;
			
						String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
						ResourceLocation key = new ResourceLocation(mod.getModId(), name);
							
						BufferedReader reader = null;
						try {
							reader = Files.newBufferedReader(file);
							JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
							ResourceLocation recipeLoader = new ResourceLocation(JsonUtils.getString(json, "type", "drpcore:simple_recipe"));
							
							if(recipeFactoryRegistry.containsKey(recipeLoader)) {
								RecipeFactory factory = recipeFactoryRegistry.getValue(recipeLoader);
								List<Block> craftingStations = new ArrayList<Block>();
								
								JsonArray stationsArr = json.get("crafting_stations").getAsJsonArray();
								for(int i = 0; i < stationsArr.size(); i++) {
									ResourceLocation station = new ResourceLocation(stationsArr.get(i).getAsString());
									if(Block.REGISTRY.containsKey(station))
										craftingStations.add(Block.REGISTRY.getObject(station));
								}
								
								JsonArray recipes = json.get("recipes").getAsJsonArray();
								for(int i = 0; i < recipes.size(); i++) {
									JsonObject recipeObj = recipes.get(i).getAsJsonObject();
									Recipe recipe = factory.readRecipe(true ,recipeObj);
									CraftingRegistry.recipes.add(recipe);
								}
							}
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

	}
	
	protected static void loadCustomRecipes() {
		worldFolder  = DimensionManager.getCurrentSaveRootDirectory();
		if(worldFolder != null) {
			worldFolder = new File(worldFolder.getPath() + "/data/drp/");
			if(!worldFolder.exists())
				worldFolder.mkdirs();
			
			
		}
	}
	
	protected static void getIDMappings() throws IOException {
		if(worldFolder != null) {
			
			File recipesIDMapping = new File(worldFolder.getPath() + "/data/drp/recipes.dat");
			if(!recipesIDMapping.exists()) {
				recipesIDMapping.createNewFile();

				
				
				
			}else {
				
			}
			
		}
	}
	
	protected static void resetRegistry() {
		recipes = recipes.parallelStream().filter(s -> s.isModded()).collect(Collectors.toList());
		hasLoadedCustomRecipes = false;
		hasLoadedIDs = false;
	}
	
	public static void clear() {
		recipes = new ArrayList<Recipe>();
		hasLoadedCustomRecipes = false;
		hasLoadedIDs = false;
	}

	
	private static void writeToStream(OutputStream os, NBTTagCompound compound){
		try {
			CompressedStreamTools.writeCompressed(compound, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static NBTTagCompound readFromFile(InputStream is){
		try {
			return CompressedStreamTools.readCompressed(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
