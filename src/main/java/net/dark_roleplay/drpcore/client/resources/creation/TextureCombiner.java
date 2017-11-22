package net.dark_roleplay.drpcore.client.resources.creation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class TextureCombiner {

	public static void overlayTextures(ResourceLocation text1, ResourceLocation text2, ResourceLocation newLoc){
		try {
			IResource img1 = Minecraft.getMinecraft().getResourceManager().getResource(text1);
			IResource img2 = Minecraft.getMinecraft().getResourceManager().getResource(text2);
			
			BufferedImage texture1 = ImageIO.read(img1.getInputStream());
			BufferedImage texture2 = ImageIO.read(img2.getInputStream());
			
			Graphics gfx = texture1.getGraphics();
			gfx.drawImage(texture2, 0, 0, null);

			File newFile = new File(DRPCoreReferences.DARK_ROLEPLAY_FOLDER.toString() + "generated_resources/" + newLoc.getResourceDomain() + "/" + newLoc.getResourcePath());
			newFile.createNewFile();
			ImageIO.write(texture1, "png", newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
