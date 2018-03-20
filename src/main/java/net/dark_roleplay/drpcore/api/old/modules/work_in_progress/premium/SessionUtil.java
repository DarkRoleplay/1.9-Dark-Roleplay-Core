package net.dark_roleplay.drpcore.api.old.modules.work_in_progress.premium;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class SessionUtil {
	/**
	 * as the Session field in Minecraft.class is private we have to access it via reflection
	 */
	private static Field sessionField = ReflectionHelper.findField(Minecraft.class, "session", "S", "field_71449_j");

	public static Session get() throws IllegalArgumentException, IllegalAccessException {
		return Minecraft.getMinecraft().getSession();
	}
}
