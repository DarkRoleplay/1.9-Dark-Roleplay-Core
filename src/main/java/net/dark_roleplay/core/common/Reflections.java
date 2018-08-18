package net.dark_roleplay.core.common;

import java.io.File;
import java.util.List;

import net.dark_roleplay.core.modules.automatic_resource_generation.GeneratedResourcePack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;

import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Reflections {
	
	public void init(){
		File resourcesFile = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/dark roleplay/argh/");
		File resourcesFolder = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/dark roleplay/argh/assets/");
		resourcesFolder.mkdirs();
		((List<IResourcePack>)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao", "defaultResourcePacks")).add(new GeneratedResourcePack(resourcesFile));
	}
}
