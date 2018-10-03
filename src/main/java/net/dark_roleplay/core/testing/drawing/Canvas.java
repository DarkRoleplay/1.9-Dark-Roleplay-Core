package net.dark_roleplay.core.testing.drawing;

import java.io.IOException;

import net.dark_roleplay.core_modules.guis.api.components.Component;
import net.dark_roleplay.library.experimental.variables.wrappers.IntegerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Canvas extends Component<Canvas> {

	private static int headerOffset = 11 ;

	protected int pixelX = 0, pixelY = 0;
	protected int bgColor = 0xAA222222;

	protected int[] data;

	protected IntegerWrapper leftColor;
	protected IntegerWrapper rightColor;

	protected int scalar = 6;
	protected DrawingGui parent;

	public Canvas(DrawingGui parent, int width, int height, int[] data, IntegerWrapper leftColor, IntegerWrapper rightColor) {
		this.pixelX = width;
		this.pixelY = height;
		this.data = data;
		this.parent = parent;
		this.width = width * this.scalar;
		this.height = height * this.scalar + headerOffset;
		this.leftColor = leftColor;
		this.rightColor = rightColor;
	}

	@Override
	public ResourceLocation getTexture() { return null; }


	int offsetX = 0;
	int offsetY = 0;
	boolean canMove = false;

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.canMove = false;
		if(mouseY > this.posY && mouseY < this.posY + headerOffset) {
			if(mouseX > this.posX && mouseX < this.posX + this.width) {
				this.offsetX = this.posX - mouseX;
				this.offsetY = this.posY - mouseY;
				this.canMove = true;
				return true;
			}
		}

		mouseX -= this.posX;
		mouseY -= this.posY + headerOffset;
		int posX = mouseX / this.scalar;
		int posY = mouseY / this.scalar;

		this.data[posX + (this.pixelX * posY)] = mouseButton == 0 ? this.leftColor.get() : this.rightColor.get();

		return true;
	}

	@Override
	public boolean mouseHold(int mouseX, int mouseY, int mouseButton, long ticksHold) {
		if(this.canMove) {
			this.setPos(Math.max(0, Math.min(mouseX + this.offsetX, this.parent.getMaxWidth() - this.width)), Math.max(0,  Math.min(mouseY + this.offsetY, this.parent.getMaxHeight() - this.height)));
			return true;
		}

		mouseX -= this.posX;
		mouseY -= this.posY + headerOffset;
		int posX = Math.max(0, Math.min(mouseX / this.scalar, this.pixelX - 1));
		int posY = Math.max(0, Math.min(mouseY / this.scalar, this.pixelY - 1));

		this.data[posX + (this.pixelX * posY)] = mouseButton == 0 ? this.leftColor.get() : this.rightColor.get();

		return true;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
	    drawRect(this.posX, this.posY, this.posX + this.width, this.posY + headerOffset, 0xDD222222);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Canvas: " + this.pixelX + "x" + this.pixelY, this.posX + 2, this.posY + 2, 0xFFFFFFFF);

		GlStateManager.pushMatrix();
        GlStateManager.translate((this.posX), (float)(this.posY) + headerOffset, -400.0F);

        GlStateManager.enableDepth();
		GlStateManager.depthFunc(518);
	    drawRect(0, headerOffset, this.width, this.height, this.bgColor);
	    GlStateManager.depthFunc(515);

	    GlStateManager.color(1F, 1F, 1F);

	    this.drawBackground(mouseX - this.posX, mouseY - this.posY - headerOffset, partialTicks);

	    GlStateManager.disableDepth();
		GlStateManager.popMatrix();

	}

	public void drawBackground(int mouseX, int mouseY, float partialTick){
	    drawRect(0, 0, this.width, this.height - headerOffset, this.bgColor);

	    int posX = Math.max(0, Math.min(mouseX / this.scalar, this.pixelX - 1));
		int posY = Math.max(0, Math.min(mouseY / this.scalar, this.pixelY - 1));

		for(int y = 0; y < this.pixelY; y++) {
			for(int x = 0; x < this.pixelX; x++) {
				drawRect(x * this.scalar, y * this.scalar, x * this.scalar + this.scalar, y * this.scalar + this.scalar, this.data[x + (this.pixelX * y)]);
			}
		}

		int highlighter = (0xFFFFFFFF - this.data[posX + (posY * this.pixelX)]) | 0xFF000000;

		drawRect(posX * this.scalar, posY * this.scalar, posX * this.scalar + 1, posY * this.scalar + this.scalar, highlighter);
		drawRect(posX * this.scalar + this.scalar - 1, posY * this.scalar, posX * this.scalar + this.scalar, posY * this.scalar + this.scalar, highlighter);
		drawRect(posX * this.scalar, posY * this.scalar, posX * this.scalar + this.scalar, posY * this.scalar + 1, highlighter);
		drawRect(posX * this.scalar, posY * this.scalar + this.scalar - 1, posX * this.scalar + this.scalar, posY * this.scalar + this.scalar, highlighter);

//		drawRect(posX * this.scalar + (this.scalar / 2), 0, posX * this.scalar + (this.scalar / 2) + 1, posY * this.scalar, 0xFFFFFFFF);
//		drawRect(posX * this.scalar + (this.scalar / 2), posY * this.scalar + this.scalar, posX * this.scalar + (this.scalar / 2) + 1, this.height - headerOffset, 0xFFFFFFFF);

//		drawRect(0, posY * this.scalar + (this.scalar / 2), posX * this.scalar + 1, posY * this.scalar + 1 + (this.scalar / 2), 0xFFFFFFFF);
//		drawRect(posX * this.scalar + this.scalar, posY * this.scalar + (this.scalar / 2),this.width, posY * this.scalar + 1 + (this.scalar / 2), 0xFFFFFFFF);


	}

	public int[] getData() {
		return this.data;
	}
}
