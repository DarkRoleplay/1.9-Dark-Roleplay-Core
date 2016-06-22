package net.drpcore.common.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.drpcore.common.util.crafting.CraftingRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ConfigurationManager {

	/** Used By Client and Server to save default Config */
	public static File CConfigFolder;

	/** Only used by Client to save Configs recieved from server */
	public static File SConfigFolder;

	/** Used By Client and Server to save default Config */
	public static File CRecipeConfigFolder;

	/** Only used by Client to save Configs recieved from server */
	public static File SRecipeConfigFolder;

	/** Used By Client and Server to save default Config */
	public static File CSchematicConfigFolder;

	/** Only used by Client to save Configs recieved from server */
	public static File SSchematicConfigFolder;

	// Default Categorys and Keys
	public ConfigurationManager(File config) {
		this.CConfigFolder = new File(config.getPath() + "\\Client");
		this.CRecipeConfigFolder = new File(CConfigFolder + "\\Crafting Recipes");
		this.CSchematicConfigFolder = new File(CConfigFolder + "\\Schematics");
		this.CConfigFolder = new File(config.getPath() + "\\Server");
		this.SRecipeConfigFolder = new File(SConfigFolder + "\\Crafting Recipes");
		this.SSchematicConfigFolder = new File(SConfigFolder + "\\Schematics");
	}

	public void readMainRecipes() {

		for(File Type : CRecipeConfigFolder.listFiles()) {
			if(Type.isDirectory()) {
				for(File StationConfig : Type.listFiles()) {
					HashMap<String, HashMap<String, ArrayList<ItemStack>>> Recipes = new HashMap<String, HashMap<String, ArrayList<ItemStack>>>();
					Configuration CRConfig = new Configuration(StationConfig);
					CRConfig.load();
					String stationName = CRConfig.get("AStationName", "Station", "").getString();
					System.out.println(CRConfig.getCategoryNames());
					ArrayList<String> disabledRecipes = new ArrayList<String>();
					Block Station = getBlockByName(stationName);
					String CurrentCategory;
					ItemStack Output;
					ItemStack[] MainInput = new ItemStack[0];
					for(String category : CRConfig.getCategoryNames()) {
						String[] categorys = category.split("[.]");
						switch (categorys.length) {
							case 0:
								break;
							case 1:
								break;
							case 2:
								if( ! CRConfig.get(category, "enabled", true).getBoolean()) {
									disabledRecipes.add(categorys[1]);
								} else {
									CurrentCategory = categorys[0];
								}
								continue;
							case 3:
								if( ! disabledRecipes.contains(categorys[1]))
									System.out.println(category);
								break;
							case 4:
								if(categorys[3].contains("output")) {
									String name = CRConfig.get(category, "name", "null").getString();
									if(getBlockByName(name) != null) {
										Output = new ItemStack(getBlockByName(name), CRConfig.get(category, "amount", 0).getInt(), CRConfig.get(category, "metadata", 0).getInt());
									} else if(getItemByName(name) != null) {
										Output = new ItemStack(getItemByName(name), CRConfig.get(category, "amount", 0).getInt(), CRConfig.get(category, "metadata", 0).getInt());
									}
								}
								break;
							case 5:
								if(categorys[3].contains("maininput")) {
									ItemStack[] MainInputTemp = new ItemStack[MainInput.length + 1];
									for(int i = 0; i < MainInput.length; i++ ) {
										MainInputTemp[i] = MainInput[i];
									}
									MainInputTemp[MainInput.length + 1] = new ItemStack(Item.getByNameOrId(CRConfig.get(category, "name", "null").getString()), CRConfig.get(category, "amount", 0).getInt(), CRConfig.get(category, "metadata", 0).getInt());
									MainInput = MainInputTemp;
								}
								break;
						}
					}
					CRConfig.save();
				}
			} else if(Type.getName().endsWith(".cfg")) {}
		}
	}

	public Block getBlockByName(String name) {

		String[] splitted = name.split(":");
		return GameRegistry.findBlock(splitted[1], splitted[0]);
	}

	public Item getItemByName(String name) {

		String[] splitted = name.split(":");
		return GameRegistry.findItem(splitted[1], splitted[0]);
	}

	public File getConfigFolder() {

		return this.CConfigFolder;
	}
}
