package net.dark_roleplay.drpcore.client.build_mode;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiBuildingViewer extends GuiScreen {

	private EntityBuildingViewer viewer;

	public GuiBuildingViewer(EntityBuildingViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void initGui() {
		Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
	}
	
	@Override
	public void onGuiClosed(){
		BuildingViewerHelper.exit();
		System.out.println("exit");
		Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
    }

	@Override
	public void handleMouseInput() throws IOException {
		int rotateX = Mouse.getEventDX();
		int rotateY = Mouse.getEventDY();
		
		this.viewer.rotate(rotateX, -rotateY);
		
//		int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
//		int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
//		int k = Mouse.getEventButton();

		
	}

	


	@Override
	public void handleKeyboardInput() throws IOException {
		char c0 = Keyboard.getEventCharacter();
		int key = Keyboard.getEventKey();
		
		if (Keyboard.getEventKey() == 0 && c0 >= ' ' || Keyboard.getEventKeyState()) {
			this.keyTyped(c0, Keyboard.getEventKey());
		}
	}

}
