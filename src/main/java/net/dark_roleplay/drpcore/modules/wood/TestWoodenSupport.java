package net.dark_roleplay.drpcore.modules.wood;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class TestWoodenSupport extends Block{

	public TestWoodenSupport(String registryName) {
		super(Material.WOOD);
		this.setRegistryName(registryName);
	}

}
