package net.dark_roleplay.core.api.old.modules.work_in_progress.premium;

import net.dark_roleplay.core.api.old.Modules;
import net.dark_roleplay.core.api.old.modules.gui.VerticalPanel;

public class Panel_Addons extends VerticalPanel{

	public Panel_Addons(int posX, int posY, int width, int height, int scrollHeight) {
		super(posX, posY, width, height, scrollHeight);
		int i = 0;
		int pos = 0;
//		for(PremiumAddon addon : Modules.PREMIUM.getAddons()){
//			Addon pAddon = new Addon(addon, 0, pos, this.width - 7);
//			this.addChild(pAddon);
//			pos += pAddon.getHeight();
//			i++;
//		}
	}
}
