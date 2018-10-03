package net.dark_roleplay.core.testing.drawing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.dark_roleplay.core_modules.guis.api.components.DRPGuiScreen;
import net.dark_roleplay.library.experimental.variables.wrappers.IntegerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class DrawingGui extends DRPGuiScreen {

	Canvas canvas;

	static ImageHelper simpleImage = null;

	private IDrawingCallback callback;

	public DrawingGui(int[] colors, int width, int height, int maxColors, IDrawingCallback callback) {
		int[] data = new int[width * height];
		Arrays.fill(data, 0xFFFFFFFF);

		IntegerWrapper leftColor = new IntegerWrapper(colors[0]);
		IntegerWrapper rightColor = new IntegerWrapper(colors[0]);

		this.canvas = new Canvas(this, width, height, data, leftColor, rightColor);
		this.canvas.setPos(150, 50);
		this.comps.add(this.canvas);

		ColorSelection sel = new ColorSelection(colors, maxColors, this, 0xFFFFFFFF, leftColor, rightColor);
		sel.setPos(50, 50);
		this.comps.add(sel);

		this.callback = callback;
	}

	public int getMaxWidth() {
		return this.width;
	}

	public int getMaxHeight() {
		return this.height;
	}

	@Override
	public void initGui() {
	}

	@Override
    public void onGuiClosed() {
		simpleImage = this.createImage();
		this.callback.processImage(simpleImage);
    }

	@Override
	public void drawForeground(int mouseX, int mouseY, float partialTicks) {
		if(simpleImage == null) return;

		Minecraft.getMinecraft().getTextureManager().bindTexture(simpleImage.getResource());
		GlStateManager.color(1f, 1f, 1f, 1.0f);
		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 64, 128, 64, 128);
	}

	public ImageHelper createImage() {
		int[] data = this.canvas.getData();

		List<Integer> newPallete = new ArrayList<Integer>();
		int[] data2 = new int[data.length];

		for (int x = 0; x < this.canvas.pixelX; x++) {
			for (int y = 0; y < this.canvas.pixelY; y++) {
				int color = data[x + (this.canvas.pixelX * y)];

				if (!newPallete.contains(color)) newPallete.add(color);
				data2[x + (this.canvas.pixelX * y)] = newPallete.indexOf(color);
			}
		}

		int[] finalPallete = newPallete.stream().mapToInt(Integer::intValue).toArray();

		return new ImageHelper(finalPallete, data2, this.canvas.pixelX, this.canvas.pixelY);
	}

	public static int getIndex(Set<? extends Object> set, Object value) {
		int result = 0;
		for (Object entry : set) {
			if (entry.equals(value))
				return result;
			result++;
		}
		return -1;
	}
}
