package net.dark_roleplay.core.testing.drawing;

import java.io.IOException;

import net.dark_roleplay.core_modules.guis.api.components.Component;
import net.dark_roleplay.library.experimental.variables.wrappers.IntegerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class ColorSelection extends Component<ColorSelection> {

	protected static int colorWidth = 9;
	protected static int headerOffset = 11 ;
	protected static int spacing = 2;
	protected boolean isMinimized = false;

	private FontRenderer fontRenderer;

	protected int[] palette;
	protected int colorsWidth, colorsHeight;

	protected ColorField[] fields;

	protected int maxWidth = 4;
	protected int maxColors = 0;

	protected ColorField leftField;
	protected ColorField rightField;

	protected DrawingGui parent;

	protected IntegerWrapper leftColor;
	protected IntegerWrapper rightColor;

	public int filler;
	protected int[] pallete;

	public ColorSelection(int[] palette, int maxColors, DrawingGui parent, int filler, IntegerWrapper leftColor, IntegerWrapper rightColor) {
		this.parent = parent;
		this.palette = palette;
		this.colorsWidth = Math.min(maxColors, this.maxWidth);
		this.colorsHeight = (int) Math.ceil(palette.length / (this.maxWidth * 1D));
		this.maxColors = maxColors;
		this.fields = new ColorField[maxColors];

		this.width = (colorWidth * this.colorsWidth + this.colorsWidth * spacing) + 1;
		this.height = headerOffset + 1 + (this.colorsHeight * colorWidth + this.colorsHeight * spacing);

		this.palette = palette;
		for(int i = 0, x = 0, y = 0; i < palette.length; i++) {
			this.fields[i] = new ColorField(this.posX + 1 + (x * colorWidth + x * spacing), this.posY + 1 + (y * colorWidth + y * spacing) + headerOffset, x, y, this.palette[i]);
			x++;
			if(x >= this.colorsWidth) {
				x = 0;
				y++;
			}
		}
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
		this.leftColor = leftColor;
		this.rightColor = rightColor;
		this.filler = filler;
	}


	@Override
	public ColorSelection setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;

		for(ColorField field : this.fields) {
			if(field != null) {
				field.posX = this.posX + 1 + (field.x * colorWidth + field.x * spacing);
				field.posY = this.posY + 1 + (field.y * colorWidth + field.y * spacing) + headerOffset;
			}
		}
		return this;
	}

	int offsetX = 0;
	int offsetY = 0;
	boolean canMove = false;

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.canMove = false;
		if(mouseY > this.posY && mouseY < this.posY + headerOffset) {
			if(mouseX > this.posX && mouseX < this.posX + this.width - 11) {
				this.offsetX = this.posX - mouseX;
				this.offsetY = this.posY - mouseY;
				this.canMove = true;
			}else if(mouseX > this.posX + this.width - 11 && mouseX < this.posX + this.width) {
				this.isMinimized = !this.isMinimized;
				if(this.isMinimized)
					this.setSize(this.width, this.headerOffset);
				else
					this.setSize(this.width, headerOffset + 1 + (this.colorsHeight * colorWidth + this.colorsWidth * spacing));
			}
		}

		for(ColorField field : this.fields) {
			if(field != null)
				if(field.wasClicked(mouseX, mouseY)) {
					if(mouseButton == 0) {
						if(this.leftField != null) this.leftField.isKey = 0;
						this.leftField = field;
						field.setKey(-1);
						this.leftColor.set(field.color);
					}else {
						if(this.rightField != null) this.rightField.setKey(0);
						this.rightField = field;
						field.setKey(1);
						this.rightColor.set(field.color);
					}
					break;
				}
		}

		return true;
	}


	@Override
	public boolean mouseHold(int mouseX, int mouseY, int mouseButton, long ticksHold) {
		if(this.canMove) {
			this.setPos(Math.max(0, Math.min(mouseX + this.offsetX, this.parent.getMaxWidth() - this.width)), Math.max(0,  Math.min(mouseY + this.offsetY, this.parent.getMaxHeight() - this.height)));
		}

		return true;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {

		drawRect(this.posX, this.posY, this.posX + this.width - 11, this.posY + headerOffset, 0xBB222222);
		drawRect(this.posX + this.width - 11, this.posY, this.posX + this.width, this.posY + headerOffset, 0xAA555555);

		if(this.leftField != null)
			drawRect(this.posX + 1, this.posY + 1, this.posX + headerOffset - 1, this.posY + headerOffset - 1, this.leftField.color);


		if(this.rightField != null)
			drawRect(this.posX + 1 + headerOffset, this.posY + 1, this.posX + headerOffset + headerOffset - 1, this.posY + headerOffset - 1, this.rightField.color);


		if(this.isMinimized) return;

		drawRect(this.posX, this.posY + headerOffset, this.posX + this.width, this.posY + this.height, 0x66222222);

		for(ColorField field : this.fields) {
			if(field != null)
			field.render(mouseX, mouseY);
		}
	}

	public static boolean isHovered(int mouseX, int mouseY, int minX, int minY, int width, int height) {
		return mouseX > minX && mouseY > minY && mouseX < minX + width && mouseY < minY + height;
	}

	@Override
	public ResourceLocation getTexture() { return null; }

	public class ColorField{
		protected int x, y;
		protected int posX, posY;

		protected int color;
		int isKey = 0;

		public ColorField(int posX, int posY, int x, int y, int color) {
			this.posX = posX;
			this.posY = posY;
			this.color = color;
			this.x = x;
			this.y = y;
		}

		public void render(int mouseX, int mouseY) {
			drawRect(this.posX, this.posY, this.posX + colorWidth + 1, this.posY + colorWidth + 1, this.isKey == -1 ? 0xFF0000DD : this.isKey == 1 ? 0xFF00DD00 : isHovered(mouseX, mouseY, this.posX, this.posY, colorWidth, colorWidth) ? 0x88FFFFFF : 0x88222222);

			drawRect(this.posX + 1, this.posY + 1, this.posX + colorWidth, this.posY + colorWidth, this.color);

			if(this.color == ColorSelection.this.filler) {
				drawRect(this.posX + 1, this.posY + 1, this.posX + colorWidth, this.posY + 2, 0xFFFF0000);
				drawRect(this.posX + 1, this.posY + colorWidth - 1, this.posX + colorWidth, this.posY + colorWidth, 0xFFFF0000);
				drawRect(this.posX + 1, this.posY + 1, this.posX + 2, this.posY + colorWidth, 0xFFFF0000);
				drawRect(this.posX + colorWidth - 1, this.posY + 1, this.posX + colorWidth, this.posY + colorWidth, 0xFFFF0000);
			}
		}

		public boolean wasClicked(int mouseX, int mouseY) {
			return isHovered(mouseX, mouseY, this.posX, this.posY, colorWidth, colorWidth);
		}

		public void setKey(int key) {
			this.isKey = key;
		}
	}
}
