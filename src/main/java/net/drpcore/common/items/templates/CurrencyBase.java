package net.drpcore.common.items.templates;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CurrencyBase extends Item {

	private ArrayList<CurrencyBase> Replacements;
	private int Value;

	/**
	 * @param Value
	 *        Wouldn't Recommend to use values bellow 1 as it can be interpret
	 *        as 1 cent
	 * @param Replacements
	 *        Coins that will be give the player when he trades but doesn't uses
	 *        the full value The first should be the most valuable
	 */

	public CurrencyBase(int Value, ArrayList<CurrencyBase> Replacements) {
		this.Replacements = Replacements;
		this.Value = Value;
	}

	public void setValue(int Value) {

		this.Value = Value;
	}

	public void setReplacements(ArrayList<CurrencyBase> Replacements) {

		this.Replacements = Replacements;
	}

	public int getValue() {

		return this.Value;
	}

	public ArrayList<CurrencyBase> getReplacements() {

		return this.Replacements;
	}
}
