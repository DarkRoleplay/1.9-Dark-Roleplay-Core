package net.dark_roleplay.drpcore.api.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class HandlerBlock extends Block{

	public HandlerBlock(String name, BlockSettings settings) {
		super(settings.getMaterial(), settings.getMapColor());
		this.setSoundType(settings.getSoundType());
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setHardness(settings.getHardness());
		this.setResistance(settings.getBlastResistance());
	}

}
