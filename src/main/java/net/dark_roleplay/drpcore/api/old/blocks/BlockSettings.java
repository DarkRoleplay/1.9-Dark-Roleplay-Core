package net.dark_roleplay.drpcore.api.old.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockSettings {
	
	private Material material;
	private MapColor mapColor;
	private SoundType soundType;
	private float hardness;
	private float blastResistance;
	
	public BlockSettings(Material material, SoundType soundType, float hardness, float blastResistance) {
		this(material, material.getMaterialMapColor(), soundType, hardness, blastResistance);
	}
	
	public BlockSettings(Material material, MapColor mapColor, SoundType soundType, float hardness, float blastResistance) {
		this.material = material;
		this.mapColor = mapColor;
		this.soundType = soundType;
		this.hardness = hardness;
		this.blastResistance = blastResistance;
	}

	public Material getMaterial() {
		return material;
	}

	public MapColor getMapColor() {
		return mapColor;
	}

	public float getHardness() {
		return hardness;
	}

	public float getBlastResistance() {
		return blastResistance;
	}

	public SoundType getSoundType() {
		return soundType;
	}
}
