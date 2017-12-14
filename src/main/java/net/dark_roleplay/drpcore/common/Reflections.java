package net.dark_roleplay.drpcore.common;

import java.util.List;

import net.dark_roleplay.drpcore.modules.wood.GeneratedResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Reflections {

	public static void preInit(){
		//Minecraft.defaultResourcePacks
		((List<IResourcePack>)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao", "defaultResourcePacks")).add(new GeneratedResourcePack(DRPCoreReferences.DARK_ROLEPLAY_AUTOMATIC_RESOURCES));
	}
	
}
