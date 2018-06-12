package net.dark_roleplay.core.api.old.gui.advanced;

import net.dark_roleplay.core.api.old.gui.utility.wrappers.Variable_String;
import net.dark_roleplay.core.api.old.modules.gui.IGuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;

public class Gui_Textfield extends IGuiElement.IMPL{
	
	private Variable_String text;
	
	private int cursorPos;
	private int cursorTick;
	
	private int selectionStart;
	private int selectionEnd;
	
	public Gui_Textfield(Variable_String text, int posX, int posY, int width, int height, int maxLength){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateCursorCounter(){
        ++this.cursorTick;
    }
	
	public boolean keyTyped(char typedChar, int keyCode){
		if(Minecraft.getMinecraft().currentScreen.isKeyComboCtrlA(keyCode)){
			this.selectionStart = 0;
			this.selectionEnd = this.text.get().length();
			return true;
		}
		return false;
	}
//		if (!this.isVisible()) {
//			return false;
//		} else if (GuiScreen.isKeyComboCtrlA(keyCode)) {
//			this.setCursorPositionEnd();
//			this.setSelectionPos(0);
//			return true;
//		} else if (GuiScreen.isKeyComboCtrlC(keyCode)) {
//			GuiScreen.setClipboardString(this.getSelectedText());
//			return true;
//		} else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
//			if (this.isEnabled) {
//				this.writeText(GuiScreen.getClipboardString());
//			}
//
//			return true;
//		} else if (GuiScreen.isKeyComboCtrlX(keyCode)) {
//			GuiScreen.setClipboardString(this.getSelectedText());
//
//			if (this.isEnabled) {
//				this.writeText("");
//			}
//
//			return true;
//		} else {
//			switch (keyCode) {
//			case 14:
//
//				if (GuiScreen.isCtrlKeyDown()) {
//					if (this.isEnabled) {
//						this.deleteWords(-1);
//					}
//				} else if (this.isEnabled) {
//					this.deleteFromCursor(-1);
//				}
//
//				return true;
//			case 199:
//
//				if (GuiScreen.isShiftKeyDown()) {
//					this.setSelectionPos(0);
//				} else {
//					this.setCursorPositionZero();
//				}
//
//				return true;
//			case 203:
//
//				if (GuiScreen.isShiftKeyDown()) {
//					if (GuiScreen.isCtrlKeyDown()) {
//						this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
//					} else {
//						this.setSelectionPos(this.getSelectionEnd() - 1);
//					}
//				} else if (GuiScreen.isCtrlKeyDown()) {
//					this.setCursorPosition(this.getNthWordFromCursor(-1));
//				} else {
//					this.moveCursorBy(-1);
//				}
//
//				return true;
//			case 205:
//
//				if (GuiScreen.isShiftKeyDown()) {
//					if (GuiScreen.isCtrlKeyDown()) {
//						this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
//					} else {
//						this.setSelectionPos(this.getSelectionEnd() + 1);
//					}
//				} else if (GuiScreen.isCtrlKeyDown()) {
//					this.setCursorPosition(this.getNthWordFromCursor(1));
//				} else {
//					this.moveCursorBy(1);
//				}
//
//				return true;
//			case 207:
//
//				if (GuiScreen.isShiftKeyDown()) {
//					this.setSelectionPos(this.text.length());
//				} else {
//					this.setCursorPositionEnd();
//				}
//
//				return true;
//			case 211:
//
//				if (GuiScreen.isCtrlKeyDown()) {
//					if (this.isEnabled) {
//						this.deleteWords(1);
//					}
//				} else if (this.isEnabled) {
//					this.deleteFromCursor(1);
//				}
//
//				return true;
//			default:
//
//				if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
//					if (this.isEnabled) {
//						this.writeText(Character.toString(typedChar));
//					}
//
//					return true;
//				} else {
//					return false;
//				}
//			}
//		}
//	}
}
