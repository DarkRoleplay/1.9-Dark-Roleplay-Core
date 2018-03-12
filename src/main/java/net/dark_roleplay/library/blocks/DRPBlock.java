package net.dark_roleplay.library.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * @author JTK222
 */
public class DRPBlock extends Block{

	public DRPBlock(String name, BlockSettings settings) {
		super(settings.getMaterial(), settings.getMapColor());
		this.setSoundType(settings.getSoundType());
		this.setHardness(settings.getHardness());
		this.setResistance(settings.getBlastResistance());
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}

}
