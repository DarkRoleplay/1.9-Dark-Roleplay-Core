package net.drpcore.common.blocks;

import java.util.Random;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.util.schematic.Schematic;
import net.drpcore.common.util.schematic.SchematicController;
import net.drpcore.common.util.schematic.SchematicReader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureManager extends Block {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 2);

	public StructureManager() {
		super(Material.barrier);
		this.setUnlocalizedName("blockStructureManager");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			Random rnd = new Random();
			Schematic tempSchematic = SchematicReader.readSchematic(DarkRoleplayCore.NAME,
					SchematicController.Schematics.get(rnd.nextInt(SchematicController.Schematics.size())));
			tempSchematic.generate(worldIn, pos);
		}
		return true;
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, meta);
	}

	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(TYPE)).intValue();
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

}
