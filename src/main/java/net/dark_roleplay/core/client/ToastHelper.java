package net.dark_roleplay.core.client;

import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentKeybind;
import net.minecraft.util.text.TextComponentTranslation;

public class ToastHelper {
	private static TutorialToast CRAFTING_TUT = null;

	public static TutorialToast getCraftingTutorial() {
		if(CRAFTING_TUT == null) {
			TextComponentBase title = new TextComponentTranslation("drpcore.tutorial.crafting.title");
			System.out.println(title.getFormattedText());
			CRAFTING_TUT = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, title, new TextComponentTranslation("drpcore.tutorial.crafting.desc", new TextComponentKeybind("drpcore.keyBinding.openCrafting").getFormattedText()), false);
		}

		return CRAFTING_TUT;
	}
}
