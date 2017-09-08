package net.dark_roleplay.drpcore.api.util.lore;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;

public class LoreUtil {
	
	private static HashMap<String,HashMap<Item, List<String>>> CTRL_DESC 	= new HashMap<String,HashMap<Item, List<String>>>();
	private static HashMap<String,HashMap<Item, List<String>>> ALT_DESC 		= new HashMap<String,HashMap<Item, List<String>>>();
	private static HashMap<String,HashMap<Item, List<String>>> SHIFT_DESC 	= new HashMap<String,HashMap<Item, List<String>>>();
	private static HashMap<String,HashMap<Item, List<String>>> PERM_DESC 	= new HashMap<String,HashMap<Item, List<String>>>();
	
	public static void registerCTRL(Item item, List<String> lore){
		String domain = item.getRegistryName().getResourceDomain();
		if(CTRL_DESC.containsKey(domain)){
			CTRL_DESC.get(domain).put(item, lore);
		}else{
			HashMap<Item, List<String>> newMap = new HashMap<Item, List<String>>();
			newMap.put(item, lore);
			CTRL_DESC.put(domain, newMap);
		}
	}
	
	public static void registerALT(Item item, List<String> lore){
		String domain = item.getRegistryName().getResourceDomain();
		if(ALT_DESC.containsKey(domain)){
			ALT_DESC.get(domain).put(item, lore);
		}else{
			HashMap<Item, List<String>> newMap = new HashMap<Item, List<String>>();
			newMap.put(item, lore);
			ALT_DESC.put(domain, newMap);
		}
	}
	
	public static void registerSHIFT(Item item, List<String> lore){
		String domain = item.getRegistryName().getResourceDomain();
		if(SHIFT_DESC.containsKey(domain)){
			SHIFT_DESC.get(domain).put(item, lore);
		}else{
			HashMap<Item, List<String>> newMap = new HashMap<Item, List<String>>();
			newMap.put(item, lore);
			SHIFT_DESC.put(domain, newMap);
		}
	}
	
	public static void registerPERM(Item item, List<String> lore){
		String domain = item.getRegistryName().getResourceDomain();
		if(PERM_DESC.containsKey(domain)){
			PERM_DESC.get(domain).put(item, lore);
		}else{
			HashMap<Item, List<String>> newMap = new HashMap<Item, List<String>>();
			newMap.put(item, lore);
			PERM_DESC.put(domain, newMap);
		}
	}
	
	
	public static boolean hasCTRL(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		return CTRL_DESC.containsKey(domain) && CTRL_DESC.get(domain).containsKey(item);
	}
	
	public static boolean hasALT(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		return ALT_DESC.containsKey(domain) && ALT_DESC.get(domain).containsKey(item);
	}
	
	public static boolean hasSHIFT(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		return SHIFT_DESC.containsKey(domain) && SHIFT_DESC.get(domain).containsKey(item);
	}
	
	public static boolean hasPERM(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		return PERM_DESC.containsKey(domain) && PERM_DESC.get(domain).containsKey(item);
	}
	
	public static List<String> getCTRL(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		if(CTRL_DESC.containsKey(domain) && CTRL_DESC.get(domain).containsKey(item)){
			return CTRL_DESC.get(domain).get(item);
		}
		return null;
	}

	public static List<String> getALT(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		if(ALT_DESC.containsKey(domain) && ALT_DESC.get(domain).containsKey(item)){
			return ALT_DESC.get(domain).get(item);
		}
		return null;
	}
	
	public static List<String> getSHIFT(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		if(SHIFT_DESC.containsKey(domain) && SHIFT_DESC.get(domain).containsKey(item)){
			return SHIFT_DESC.get(domain).get(item);
		}
		return null;
	}
	
	public static List<String> getPERM(Item item){
		String domain = item.getRegistryName().getResourceDomain();
		if(PERM_DESC.containsKey(domain) && PERM_DESC.get(domain).containsKey(item)){
			return PERM_DESC.get(domain).get(item);
		}
		return null;
	}
}
