package net.dark_roleplay.drpcore.client.gui.skills;

import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.utility.DRPButton;

public class Button_UnlockSkill extends DRPButton{

	private Gui_SkillOverview parent;
	
	private boolean hovered = false;
	
	public Button_UnlockSkill(Gui_SkillOverview parent, int posX, int posY){
		super(posX, posY, 12, 21);
		this.parent = parent;	
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		this.hovered = mouseX >= this.posX && mouseY >= this.posY && mouseX < this.posX + this.width && mouseY < this.posY + this.height;
		int x = 231;
		int y = 214;
			
		if(!this.enabled) {
			y += 21;
//			if(!unlocked){
//				x += 13;
//			}
		}else{
			if(!this.hovered) {
				x += 13;
			}
		}
		this.drawTexturedModalRect(this.posX, this.posY, x, y, this.width, this.height);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(!parent.isUnlocked(parent.selectedSkill)){
			System.out.println(parent.tryUnlock(parent.selectedSkill));
		}
	}
}
