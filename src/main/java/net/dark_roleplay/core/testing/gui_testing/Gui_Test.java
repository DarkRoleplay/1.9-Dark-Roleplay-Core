package net.dark_roleplay.core.testing.gui_testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.core.testing.gui_testing.components.CheckBox;
import net.dark_roleplay.core.testing.gui_testing.components.Component;
import net.dark_roleplay.core.testing.gui_testing.components.DoubleSliderBox;
import net.dark_roleplay.core.testing.gui_testing.components.Mover;
import net.dark_roleplay.core.testing.gui_testing.components.RadioButton;
import net.dark_roleplay.core.testing.gui_testing.components.RadioGroup;
import net.dark_roleplay.core.testing.gui_testing.components.SliderBox;
import net.dark_roleplay.core.testing.gui_testing.components.Switch;
import net.dark_roleplay.core.testing.gui_testing.components.TextBox;
import net.dark_roleplay.library_old.wrapper.BooleanWrapper;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

public class Gui_Test extends GuiScreen {

	public List<Component> comps = new ArrayList<Component>();

	public Component focused = null;

	@Override
	public void initGui() {
		comps.clear();
		
		int id = 0;
		
		GuiLabel lblRB = new GuiLabel(this.fontRenderer, id++, 20, 20, 100, 24, 0xFFFFFFFF);
		lblRB.addLine("This are a few Radio Buttons,");
		lblRB.addLine("you can select just one of them");
		this.labelList.add(lblRB);

		RadioGroup group = new RadioGroup();
		RadioButton btn1 = new RadioButton(20, 50, 0);
		RadioButton btn2 = new RadioButton(34, 50, 1);
		RadioButton btn3 = new RadioButton(48, 50, 2);
		RadioButton btn4 = new RadioButton(62, 50, 1);
		RadioButton btn5 = new RadioButton(76, 50, 2);
		
		group.addButtons(btn1, btn2, btn3, btn4, btn5);
		group.selectButton(btn3);
		
		GuiLabel lblRB2 = new GuiLabel(this.fontRenderer, id++, 20, 70, 100, 24, 0xFFFFFFFF);
		lblRB2.addLine("Of course you can have ");
		lblRB2.addLine("multiple groups of them.");
		this.labelList.add(lblRB2);

		RadioGroup group2 = new RadioGroup();
		RadioButton btn12 = new RadioButton(20, 95, 0);
		RadioButton btn22 = new RadioButton(34, 95, 1);
		RadioButton btn32 = new RadioButton(48, 95, 2);
		RadioButton btn42 = new RadioButton(62, 95, 1);
		RadioButton btn52 = new RadioButton(76, 95, 2);
		
		group2.addButtons(btn12, btn22, btn32, btn42, btn52);
		group2.selectButton(btn12);
		
		comps.addAll(group.getButtons());
		comps.addAll(group2.getButtons());
		
		comps.add(new Switch(250, 50, new BooleanWrapper(true)));
		comps.add(new Switch(276, 50, new BooleanWrapper(false)));
		GuiLabel lbl1 = new GuiLabel(this.fontRenderer, 0, 250, 20, 100, 24, 0xFFFFFFFF);
		lbl1.addLine("This are switches, ");
		lbl1.addLine("they work like... well.. like switches");
		this.labelList.add(lbl1);
		
		
		
		GuiLabel lbl5 = new GuiLabel(this.fontRenderer, 0, 20, 120, 100, 24, 0xFFFFFFFF);
		lbl5.addLine("And if you need to select multiple answers,");
		lbl5.addLine("we've also got Check Boxes");
		this.labelList.add(lbl5);
		

		comps.add(new CheckBox(20, 142, new BooleanWrapper(false)));
		comps.add(new CheckBox(34, 142, new BooleanWrapper(false)));
		comps.add(new CheckBox(48, 142, new BooleanWrapper(false)));
		comps.add(new CheckBox(62, 142, new BooleanWrapper(false)));
		comps.add(new CheckBox(76, 142, new BooleanWrapper(false)));
		
		TextBox tb = new TextBox(this.fontRenderer, 25, 200);
		tb.setWidth(400);
		comps.add(tb);
		

		comps.add(new Mover(250, 70));
		comps.add(new SliderBox(250, 90));
		comps.add(new DoubleSliderBox(250, 110, true));
		comps.add(new DoubleSliderBox(250, 130, false));
		

		
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (Component comp : comps) {
			comp.render(mouseX, mouseY, partialTicks);
		}

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (mouseButton == 0) {
			boolean success = false;
			for (Component comp : comps) {
				if (comp.isHovered(mouseX, mouseY)) {
					success = comp.mouseClicked(mouseX, mouseY, mouseButton);
					if (success && this.focused != comp) {
						if(this.focused != null)
							this.focused.onFocusLost();
						this.focused = comp;
						this.focused.onFocusGain();
						break;
					}
				}
			}
			if(!success && this.focused != null) {
				this.focused.onFocusLost();
				this.focused = null;
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
		if (clickedMouseButton == 0) {
			if(this.focused != null) {
				this.focused.mouseHold(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
			}else {
				boolean success = false;
				for (Component comp : comps) {
					if (comp.isHovered(mouseX, mouseY)) {
						success = comp.mouseHold(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
						if (success && this.focused != comp) {
							if(this.focused != null)
								this.focused.onFocusLost();
							this.focused = comp;
							this.focused.onFocusGain();
							break;
						}
					}
				}
			}
		}
    }

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			if(this.focused == null)
				this.mc.displayGuiScreen((GuiScreen) null);
			else {
				if(this.focused != null)
					this.focused.onFocusLost();
				this.focused = null;
			}
		}else if(this.focused != null) {
			this.focused.keyTyped(typedChar, keyCode);
		}
	}
}
