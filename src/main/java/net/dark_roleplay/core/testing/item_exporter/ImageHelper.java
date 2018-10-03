package net.dark_roleplay.core.testing.item_exporter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ImageHelper {

    /** A buffer to hold pixel values returned by OpenGL. */
    private static IntBuffer pixelBuffer;
    /** The built-up array that contains all the pixel values returned by OpenGL. */
    private static int[] pixelValues;

    public static ITextComponent saveScreenshot(int posX, int posY, int sizeX, int sizeY, File gameDirectory, @Nullable String screenshotName, int width, int height, Framebuffer buffer) {
        try {
            File file1 = new File(gameDirectory, "screenshots");
            file1.mkdir();
            BufferedImage bufferedimage = createScreenshot(posX, posY, sizeX, sizeY, width, height, buffer);
            File file2;

            if (screenshotName == null) {
                file2 = getTimestampedPNGFileForDirectory(file1);
            } else {
                file2 = new File(file1, screenshotName);
            }

            file2 = file2.getCanonicalFile(); // FORGE: Fix errors on Windows with paths that include \.\
//            net.minecraftforge.client.event.ScreenshotEvent event = net.minecraftforge.client.ForgeHooksClient.onScreenshot(bufferedimage, file2);
//            if (event.isCanceled()) return event.getCancelMessage(); else file2 = event.getScreenshotFile();
            ImageIO.write(bufferedimage, "png", file2);
//            ITextComponent itextcomponent = new TextComponentString(file2.getName());
//            itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
//            itextcomponent.getStyle().setUnderlined(Boolean.valueOf(true));
//            if (event.getResultMessage() != null) return event.getResultMessage();
            return null;
        } catch (Exception exception) {
//            LOGGER.warn("Couldn't save screenshot", (Throwable)exception);
            return new TextComponentTranslation("screenshot.failure", new Object[] {exception.getMessage()});
        }
    }

	public static BufferedImage createScreenshot(int posX, int posY, int sizeX, int sizeY, int width, int height, Framebuffer framebufferIn) {
		if (OpenGlHelper.isFramebufferEnabled()) {
			width = framebufferIn.framebufferTextureWidth;
			height = framebufferIn.framebufferTextureHeight;
		}

		int i = sizeX * sizeY;

		if (pixelBuffer == null || pixelBuffer.capacity() < i) {
			pixelBuffer = BufferUtils.createIntBuffer(i);
			pixelValues = new int[i];
		}

		GlStateManager.glPixelStorei(3333, 1);
		GlStateManager.glPixelStorei(3317, 1);
		pixelBuffer.clear();

		if (OpenGlHelper.isFramebufferEnabled()) {
			GlStateManager.bindTexture(framebufferIn.framebufferTexture);
			GlStateManager.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
		} else {
			GlStateManager.glReadPixels(posX, posY, sizeX, sizeY, 32993, 33639, pixelBuffer);
		}

		pixelBuffer.get(pixelValues);
		TextureUtil.processPixelValues(pixelValues, sizeX, sizeY);
		BufferedImage bufferedimage = new BufferedImage(sizeX, sizeY, 1);
		bufferedimage.setRGB(0, 0, sizeX, sizeY, pixelValues, 0, sizeX);
		return bufferedimage;
	}

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");


	  private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
	        String s = DATE_FORMAT.format(new Date()).toString();
	        int i = 1;

	        while (true)
	        {
	            File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");

	            if (!file1.exists())
	            {
	                return file1;
	            }

	            ++i;
	        }
	    }
}
