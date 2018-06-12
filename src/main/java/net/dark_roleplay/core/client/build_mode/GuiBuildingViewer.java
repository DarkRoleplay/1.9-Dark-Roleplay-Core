package net.dark_roleplay.core.client.build_mode;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.dark_roleplay.core.testing.gui_testing.Gui_Test;
import net.dark_roleplay.core.testing.gui_testing.components.CheckBox;
import net.dark_roleplay.core.testing.gui_testing.components.RadioButton;
import net.dark_roleplay.core.testing.gui_testing.components.RadioGroup;
import net.dark_roleplay.core.testing.gui_testing.components.Switch;
import net.dark_roleplay.core.testing.gui_testing.components.TextBox;
import net.dark_roleplay.library_old.wrapper.BooleanWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;

public class GuiBuildingViewer extends Gui_Test {

	private EntityViewer viewer;
	private TileEntity te;

	private boolean hasGrabbedMouse = false;
	
	public GuiBuildingViewer(EntityViewer viewer, TileEntity te) {
		if(!(te instanceof TileEntityBuilder)) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		this.viewer = viewer;
		this.te = te;
		this.doesGuiPauseGame();
	}

	@Override
	public void initGui() {
//		comps.clear();
//		
//		int id = 0;
//		
//		GuiLabel lblRB = new GuiLabel(this.fontRenderer, id++, 20, 20, 100, 24, 0xFFFFFFFF);
//		lblRB.addLine("This are a few Radio Buttons,");
//		lblRB.addLine("you can select just one of them");
//		this.labelList.add(lblRB);
//
//		RadioGroup group = new RadioGroup();
//		RadioButton btn1 = new RadioButton(20, 50, 0);
//		RadioButton btn2 = new RadioButton(34, 50, 1);
//		RadioButton btn3 = new RadioButton(48, 50, 2);
//		RadioButton btn4 = new RadioButton(62, 50, 1);
//		RadioButton btn5 = new RadioButton(76, 50, 2);
//		
//		group.addButtons(btn1, btn2, btn3, btn4, btn5);
//		group.selectButton(btn3);
//		
//		GuiLabel lblRB2 = new GuiLabel(this.fontRenderer, id++, 20, 70, 100, 24, 0xFFFFFFFF);
//		lblRB2.addLine("Of course you can have ");
//		lblRB2.addLine("multiple groups of them.");
//		this.labelList.add(lblRB2);
//
//		RadioGroup group2 = new RadioGroup();
//		RadioButton btn12 = new RadioButton(20, 95, 0);
//		RadioButton btn22 = new RadioButton(34, 95, 1);
//		RadioButton btn32 = new RadioButton(48, 95, 2);
//		RadioButton btn42 = new RadioButton(62, 95, 1);
//		RadioButton btn52 = new RadioButton(76, 95, 2);
//		
//		group2.addButtons(btn12, btn22, btn32, btn42, btn52);
//		group2.selectButton(btn12);
//		
//		comps.addAll(group.getButtons());
//		comps.addAll(group2.getButtons());
//		
//		comps.add(new Switch(250, 50, new BooleanWrapper(true)));
//		comps.add(new Switch(276, 50, new BooleanWrapper(false)));
//		GuiLabel lbl1 = new GuiLabel(this.fontRenderer, 0, 250, 20, 100, 24, 0xFFFFFFFF);
//		lbl1.addLine("This are switches, ");
//		lbl1.addLine("they work like... well.. like switches");
//		this.labelList.add(lbl1);
//		
//		
//		
//		GuiLabel lbl5 = new GuiLabel(this.fontRenderer, 0, 20, 120, 100, 24, 0xFFFFFFFF);
//		lbl5.addLine("And if you need to select multiple answers,");
//		lbl5.addLine("we've also got Check Boxes");
//		this.labelList.add(lbl5);
//		
//
//		comps.add(new CheckBox(20, 142, new BooleanWrapper(false)));
//		comps.add(new CheckBox(34, 142, new BooleanWrapper(false)));
//		comps.add(new CheckBox(48, 142, new BooleanWrapper(false)));
//		comps.add(new CheckBox(62, 142, new BooleanWrapper(false)));
//		comps.add(new CheckBox(76, 142, new BooleanWrapper(false)));
//		
//		TextBox tb = new TextBox(this.fontRenderer, 25, 200);
//		tb.setWidth(400);
//		comps.add(tb);
	}
	
	@Override
	public void onGuiClosed(){
		BuildingViewerHelper.exit();
		if(hasGrabbedMouse)
			Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();	
    }

	@Override
	public void handleMouseInput() throws IOException {
		int rotateX = Mouse.getEventDX();
		int rotateY = Mouse.getEventDY();
		
		if(this.isAltKeyDown()) {
			if(!hasGrabbedMouse) {
				Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
				hasGrabbedMouse = true;
			}
			this.viewer.turn(rotateX, rotateY);;
		}else {
			if(hasGrabbedMouse) {
				Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();				
				hasGrabbedMouse = false;
			}
			super.handleMouseInput();
		}
	}

	


	@Override
	public void handleKeyboardInput() throws IOException {
		super.handleKeyboardInput();
		
	}

	@Override
	public boolean doesGuiPauseGame(){ return false; }
}
