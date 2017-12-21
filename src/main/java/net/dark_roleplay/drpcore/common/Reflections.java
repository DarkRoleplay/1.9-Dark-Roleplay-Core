package net.dark_roleplay.drpcore.common;

import java.io.File;
import java.util.List;

import net.dark_roleplay.drpcore.modules.wood.GeneratedResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Reflections {

	public static void preInit(){
		File resourcesFile = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/dark roleplay/argh/");
		resourcesFile.mkdirs();
		((List<IResourcePack>)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao", "defaultResourcePacks")).add(new GeneratedResourcePack(resourcesFile));
	}
	
}
