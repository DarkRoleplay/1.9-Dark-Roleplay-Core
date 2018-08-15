package net.dark_roleplay.core.modules.blueprints;

import java.util.BitSet;
import java.util.List;

import net.dark_roleplay.core.References;
import net.dark_roleplay.library.blueprints.Blueprint;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class BlueprintPreview {

	private static BlueprintTessellator model = null;
	
	private static BlockPos position = null;
	
	private static boolean isActivated = false;
	

	public static void buildModel(World world, BlockPos pos, Blueprint bp) {

		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockModelRenderer renderer = blockrendererdispatcher.getBlockModelRenderer();

		IBakedModel[] modelCache = new IBakedModel[bp.getPalleteSize()];

		IBlockState[] pallete = bp.getPallete();

		short[][][] structure = bp.getStructure();

		for (int i = 0; i < bp.getPalleteSize(); i++) {
			try {
				modelCache[i] = blockrendererdispatcher.getModelForState(pallete[i]);
			}catch(Exception e) {
				References.LOGGER.info("Failed to obtain model for {}", pallete[i].toString());
			}
		}

		BufferBuilder buffer = new BufferBuilder(2097152);
		buffer.begin(7, DefaultVertexFormats.BLOCK);

		for (short y = 0; y < bp.getSizeY(); y++) {
			for (short z = 0; z < bp.getSizeZ(); z++) {
				for (short x = 0; x < bp.getSizeX(); x++) {
					Block b = pallete[structure[y][z][x]].getBlock();
					if (b != Blocks.AIR && !(b instanceof BlockLiquid) && !(b instanceof IFluidBlock) && modelCache[structure[y][z][x]] != null)
						renderModelFlat(renderer, world, modelCache[structure[y][z][x]], pallete[structure[y][z][x]], new BlockPos(x, y, z), buffer, getNonCulled(bp, pallete, structure, x, y, z), MathHelper.getPositionRandom(new BlockPos(x, y, z)));
				}
			}
		}

		model = new BlueprintTessellator(buffer);
	}
	
	public static BlueprintTessellator getBuffer() {
		if(model != null) {
			return model;
		}
		return null;
	}
	
	public static BlockPos getPos() {
		return position;
	}
	
	public static void setPos(BlockPos pos) {
		position = pos;
	}
	
	public static boolean isActivated() {
		return isActivated;
	}
	
	public static void activate() {
		isActivated = true;
	}
	
	public static void deactivate() {
		isActivated = false;
	}

	private static EnumFacing[] getNonCulled(Blueprint bp, IBlockState[] pallete, short[][][] structure, int x, int y, int z) {
		EnumFacing[] facings = new EnumFacing[6];
		
		if(y + 1 < bp.getSizeY()) {
			IBlockState state = pallete[structure[y + 1][z][x]];
			if(!state.isFullCube()) facings[0] = EnumFacing.UP;
		}else {
			facings[0] = EnumFacing.UP;
		}
		
		if(y - 1 >= 0) {
			IBlockState state = pallete[structure[y - 1][z][x]];
			if(!state.isFullCube()) facings[1] = EnumFacing.DOWN;
		}else {
			facings[1] = EnumFacing.DOWN;
		}
		
		if(x + 1 < bp.getSizeX()) {
			IBlockState state = pallete[structure[y][z][x + 1]];
			if(!state.isFullCube()) facings[2] = EnumFacing.EAST;
		}else {
			facings[2] = EnumFacing.EAST;
		}
		
		if(x - 1 >= 0) {
			IBlockState state = pallete[structure[y][z][x - 1]];
			if(!state.isFullCube()) facings[3] = EnumFacing.WEST;
		}else {
			facings[3] = EnumFacing.WEST;
		}
		
		if(z + 1 < bp.getSizeX()) {
			IBlockState state = pallete[structure[y][z + 1][x]];
			if(!state.isFullCube()) facings[4] = EnumFacing.SOUTH;
		}else {
			facings[4] = EnumFacing.SOUTH;
		}
		
		if(z - 1 >= 0) {
			IBlockState state = pallete[structure[y][z - 1][x]];
			if(!state.isFullCube()) facings[5] = EnumFacing.NORTH;
		}else {
			facings[5] = EnumFacing.NORTH;
		}
		
		
		
		return facings;
	}

	private static boolean renderModelFlat(BlockModelRenderer renderer, IBlockAccess world, IBakedModel model, IBlockState state, BlockPos pos, BufferBuilder buffer, EnumFacing[] facings, long rand) {
		boolean flag = false;
		BitSet bitset = new BitSet(3);

		for (EnumFacing enumfacing : facings) {
			if(enumfacing == null) continue;
			List<BakedQuad> list = model.getQuads(state, enumfacing, rand);

			if (!list.isEmpty()) {
				int i = state.getPackedLightmapCoords(world, pos.offset(enumfacing));
				renderer.renderQuadsFlat(world, state, pos, i, false, buffer, list, bitset);
				flag = true;
			}
		}

		List<BakedQuad> list1 = model.getQuads(state, (EnumFacing) null, rand);

		if (!list1.isEmpty()) {
			renderer.renderQuadsFlat(world, state, pos, -1, true, buffer, list1, bitset);
			flag = true;
		}

		return flag;
	}
}