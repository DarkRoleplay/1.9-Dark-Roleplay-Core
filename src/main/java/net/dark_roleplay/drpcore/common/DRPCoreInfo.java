package net.dark_roleplay.drpcore.common;

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.common.ForgeVersion.Status;
import net.minecraftforge.fml.relauncher.Side;

public class DRPCoreInfo {

	public static final String MODID = "drpcore";
	public static final String NAME = "Dark Roleplay Core";
	public static final String VERSION = "0.3.2";
	public static final String ACCEPTEDVERSIONS = "[1.12, 1.12.1]";
	public static final String UPDATECHECK = "http://dark-roleplay.net/version_files/DarkRoleplayCore.json";
	public static File DARK_ROLEPLAY_CORE_FOLDER;
	public static Logger LOGGER;
	
	
	public static Side SIDE;
	
	public static CheckResult VERSION_STATUS;

	
	public static final String CONFIG_GUI_FACTORY = "net.dark_roleplay.drpcore.client.gui.config.GuiFactory_Config";
	
}
