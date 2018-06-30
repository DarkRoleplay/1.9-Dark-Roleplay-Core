package net.dark_roleplay.core.api.old.modules.crafting;

import net.dark_roleplay.core.api.old.modules.gui.HorizontalPanel;
import net.dark_roleplay.core.api.old.modules.gui.HorizontalScrollBar;
import net.dark_roleplay.core.api.old.modules.gui.IntegerWrapper;
import net.minecraft.util.math.MathHelper;

public class Panel_Categories extends HorizontalPanel{

	public Panel_Categories(int posX, int posY, int width, int height) {
		super(posX, posY, width, height, 300);
		this.bgColor = 0x88000000;
		
		int categoryAmount = 20;
		for(int i = 0; i < categoryAmount; i ++){
		}
		
		scrollAmount = new IntegerWrapper(0, 0, MathHelper.clamp(categoryAmount * 30 - this.width + 2 , 1, Integer.MAX_VALUE));
		scrollBar = new HorizontalScrollBar(0, height -7, width, 7, scrollAmount);
	}

}
