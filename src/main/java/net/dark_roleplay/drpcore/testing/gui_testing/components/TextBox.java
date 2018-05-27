package net.dark_roleplay.drpcore.testing.gui_testing.components;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;

public class TextBox extends Component {

	private static final ResourceLocation texture = new ResourceLocation(References.MODID,
			"textures/guis/gui_elements.png");

	private int u = 48, v = 232;

	private String text = "";

	private FontRenderer fontRenderer;

	private float animation = 0;
	private boolean marker = false;
	private boolean isFocused = false;
	private int cursorPos = 0;
	private int selectionStart = -1;
	private int selectionEnd = -1;

	public TextBox(FontRenderer fontRenderer, int posX, int posY) {
		super(34, 12, -1, 12);
		this.fontRenderer = fontRenderer;
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());

		int remaining = this.width;
		int offset = 0;
		while (remaining > 0) {
			this.drawTexturedModalRect(this.posX + this.width - Math.min(remaining, 64) - (offset * 64), this.posY,
					this.u + 2, this.v, Math.min(remaining, 64), this.height);
			remaining -= Math.min(remaining, 64);
			offset++;
		}
		this.drawTexturedModalRect(this.posX, this.posY, this.u, this.v, 2, this.height);
		this.drawTexturedModalRect(this.posX + this.width - 2, this.posY, this.u + 66, this.v, 2, this.height);

		this.animation += partialTicks;
		if (animation >= 10F) {
			this.animation = 0F;
			this.marker = !this.marker;
		}

		this.fontRenderer.drawStringWithShadow(this.text, this.posX + 2, this.posY + 2, 0xFFFFFFFF);
		
		if(this.marker && this.isFocused)
			this.fontRenderer.drawString(this.cursorPos == this.text.length() ? "_" : "|", this.posX + 2 + this.fontRenderer.getStringWidth(this.text.substring(0, this.cursorPos)), this.posY + 2, 0xFFFFFFFF);
		
		if(this.selectionStart != this.selectionEnd) {
			this.drawSelectionBox(this.posX + 1 + (this.fontRenderer.getStringWidth(this.text.substring(0, this.selectionStart))), this.posY + 1,
					this.posX + 2 + (this.fontRenderer.getStringWidth(this.text.substring(0, this.selectionEnd))), this.posY + this.height - 1);
		}
		GlStateManager.color(1F, 1F, 1F);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (this.isHovered(mouseX, mouseY)) {
			if(this.isFocused) {
				int posX = mouseX - this.posX - 2;
				int charPos = 0;
				for(int i = 0; i < this.text.length(); i++) {
					System.out.println(i);
					int charWidth = this.fontRenderer.getCharWidth(this.text.charAt(i));
					if(posX >= charPos && posX < charPos + charWidth) {
						this.cursorPos = i;
						break;
					}
					charPos += charWidth;
				}
			}
		}

		return this.isHovered(mouseX, mouseY);
	}

	@Override
	public void onFocusGain() {
		Keyboard.enableRepeatEvents(true);
		this.isFocused = true;
		this.cursorPos = this.text.length();
	}

	@Override
	public void onFocusLost() {
		Keyboard.enableRepeatEvents(false);
		this.isFocused = false;
		this.selectionStart = -1;
		this.selectionEnd = -1;
	}

	public void keyTyped(char typedChar, int keyCode) throws IOException {
//		System.out.println(keyCode);
		// else if (keyCode == 203) {
		// this.cursorPos = this.cursorPos - 1 >= 0 ? this.cursorPos - 1 : 0;
		// } else {
		// if (Character.isBmpCodePoint(typedChar) && keyCode != 42) {
		// this.text += typedChar;
		// this.cursorPos++;
		// }
		// }
		try {

			if (keyCode == 14) {
				this.delete();				
				this.contentChanged();
				return;
			} else if (GuiScreen.isKeyComboCtrlA(keyCode)) {
				this.selectionStart = 0;
				this.selectionEnd = this.text.length();
				return;
			} else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
				this.writeText(GuiScreen.getClipboardString());
				this.contentChanged();
				return;
			} else if (GuiScreen.isKeyComboCtrlC(keyCode) && this.selectionStart != -1) {
	            GuiScreen.setClipboardString(this.text.substring(this.selectionStart, this.selectionEnd)); 
				return;
			} else if (GuiScreen.isKeyComboCtrlX(keyCode) && this.selectionStart != -1) {
				GuiScreen.setClipboardString(this.text.substring(this.selectionStart, this.selectionEnd));
				this.text = this.text.substring(0, this.selectionStart) + this.text.substring(this.selectionEnd, this.text.length());
				this.cursorPos = this.selectionStart;
				this.clearSelection();
				this.contentChanged();
				return;
			} else if(keyCode == 203) {
				this.cursorLeft();
			} else if(keyCode == 205) {
				this.cursorRight();
			} else {
				if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
					if(this.selectionStart != -1) {
						this.text = this.text.substring(0, this.selectionStart) + this.text.substring(this.selectionEnd, this.text.length());
						this.cursorPos = this.selectionStart;
						this.clearSelection();
					}
					this.writeText(Character.toString(typedChar));
					this.contentChanged();
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void delete() {
		if(this.text.length() <= 0)
			return;
		
		if (this.selectionStart != -1 && this.selectionStart != this.selectionEnd) {

		}else{
			this.text = this.text.substring(0, this.cursorPos -1) + this.text.substring(this.cursorPos, this.text.length());
			this.cursorPos -= 1;
		}
	}

	public void writeText(String text) {
		if (this.selectionStart != this.selectionEnd) {
			this.text = this.text.substring(0, this.selectionStart) + text
					+ this.text.substring(this.selectionEnd, this.text.length());
		} else {
			this.text = this.text.substring(0, this.cursorPos) + text
					+ this.text.substring(this.cursorPos, this.text.length());
		}
		this.cursorPos += text.length();
	}

	public void cursorRight() {
		if(this.cursorPos + 1 <= this.text.length()) {
			if(GuiScreen.isShiftKeyDown()) {
				if(this.selectionStart == -1) {
					this.selectionStart = this.cursorPos;
					this.selectionEnd = this.cursorPos + 1;
				}else if(this.selectionEnd == this.cursorPos) {
					this.selectionEnd = this.selectionEnd + 1 < this.text.length() ? this.selectionEnd + 1 : this.text.length();
				}else if(this.selectionEnd < this.cursorPos) {
					this.selectionEnd = this.cursorPos;
				}else if(this.selectionStart < this.cursorPos + 1) {
					this.selectionStart = this.cursorPos + 1;
				}
			}else {
				this.clearSelection();
			}
			this.cursorPos++;
		}
	}

	public void cursorLeft() {
		if(this.cursorPos - 1 >= 0) {
			if(GuiScreen.isShiftKeyDown()) {
				if(this.selectionStart == -1) {
					this.selectionEnd = this.cursorPos;
					this.selectionStart = this.cursorPos - 1;
				}else if(this.selectionStart == this.cursorPos){
					this.selectionStart = this.selectionStart - 1 > 0 ? this.selectionStart -1 : 0;
				}else if(this.selectionStart > this.cursorPos) {
					this.selectionStart = this.cursorPos;
				}else if(this.selectionEnd > this.cursorPos - 1) {
					this.selectionEnd = this.cursorPos - 1;
				}
			}else {
				this.clearSelection();
			}
			this.cursorPos--;
		}
	}
	
	public void clearSelection() {
		this.selectionStart = -1;
		this.selectionEnd = -1;
	}

	protected void drawSelectionBox(int startX, int startY, int endX, int endY) {
		if (startX < endX) {
			int i = startX;
			startX = endX;
			endX = i;
		}

		if (startY < endY) {
			int j = startY;
			startY = endY;
			endY = j;
		}

		if (endX > this.posX + this.width) {
			endX = this.posX + this.width;
		}

		if (startX > this.posX + this.width) {
			startX = this.posX + this.width;
		}

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.enableColorLogic();
		GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos((double) startX, (double) endY, 0.0D).endVertex();
		bufferbuilder.pos((double) endX, (double) endY, 0.0D).endVertex();
		bufferbuilder.pos((double) endX, (double) startY, 0.0D).endVertex();
		bufferbuilder.pos((double) startX, (double) startY, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.disableColorLogic();
		GlStateManager.enableTexture2D();
	}
	
	public String getText() {
		return this.text;
	}
	
	public void contentChanged() {
		
	}
}
