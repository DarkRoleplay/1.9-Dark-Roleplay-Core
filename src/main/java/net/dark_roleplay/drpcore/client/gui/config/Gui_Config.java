package net.dark_roleplay.drpcore.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class Gui_Config  extends GuiConfig {
	public Gui_Config(GuiScreen parent) {
	    super(parent, getConfigElements(), "drpcore", false, false, "Dark Roleplay Core Configuration");
	 }
	
	/** Compiles a list of config elements */
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
      
        //Add categories to config GUI
        list.add(categoryElement("debug", "Debug", "drpcore.configgui.ctgy.debug"));
        list.add(categoryElement("general", "General", "drpcore.configgui.ctgy.general"));
        list.add(categoryGuiElement("gui", "Gui", "drpcore.configgui.ctgy.gui"));
      
        return list;
    }
  
    /** Creates a button linking to another screen where all options of the category are available */
    private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
                new ConfigElement(DRPCoreConfigs.config.getCategory(category)).getChildElements());
    }
    
    private static IConfigElement categoryGuiElement(String category, String name, String tooltip_key) {
    	List<IConfigElement> list = new ArrayList<IConfigElement>();
    	list.add(categoryElement("gui.crafting.selection", "Crafting Gui Recipe Selection", "drpcore.configgui.ctgy.gui.crafting.selection"));
    	list.add(categoryElement("gui.crafting.crafting", "Crafting Gui Recipe Crafting", "drpcore.configgui.ctgy.gui.crafting.craftomg"));
        return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
        		list);
    }
}
