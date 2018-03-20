package net.dark_roleplay.drpcore.testing.gui_testing.components;

import java.util.ArrayList;
import java.util.List;

public class RadioGroup {

	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	
	private RadioButton selected = null;
	
	public void addButton(RadioButton button) {
		this.buttons.add(button);
		button.setGroup(this);
	}
	
	public void addButtons(RadioButton... buttons) {
		for(RadioButton button : buttons) {
			this.buttons.add(button);
			button.setGroup(this);
		}
	}
	
	public void selectButton(RadioButton button) {
		if(buttons.contains(button)) {
			if(selected != null)
				selected.deselect();
			this.selected = button;
			this.selected.select();
		}
	}
	
	public RadioButton getSelectedButton() {
		return this.selected;
	}
	
	public List<RadioButton> getButtons() {
		return this.buttons;
	}
}
