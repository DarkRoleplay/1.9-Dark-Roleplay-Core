package net.dark_roleplay.drpcore.common.tileentities;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.blocks.SchematicController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntity_StructureController extends TileEntity {
	private String name = "";
	private BlockPos position = new BlockPos(0, 1, 0);
	private BlockPos size = new BlockPos( 5, 5, 5);
	private TileEntity_StructureController.Mode mode = TileEntity_StructureController.Mode.SAVE;
	private boolean showAir = false;
	private boolean showBoundingBox = true;
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("posX", this.position.getX());
		compound.setInteger("posY", this.position.getY());
		compound.setInteger("posZ", this.position.getZ());
		compound.setInteger("sizeX", this.size.getX());
		compound.setInteger("sizeY", this.size.getY());
		compound.setInteger("sizeZ", this.size.getZ());
		compound.setString("mode", this.mode.toString());
		compound.setBoolean("showair", this.showAir);
		compound.setBoolean("showboundingbox", this.showBoundingBox);
		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int i = MathHelper.clamp(compound.getInteger("posX"), -32, 32);
		int j = MathHelper.clamp(compound.getInteger("posY"), -32, 32);
		int k = MathHelper.clamp(compound.getInteger("posZ"), -32, 32);
		this.position = new BlockPos(i, j, k);
		int l = MathHelper.clamp(compound.getInteger("sizeX"), 0, 32);
		int i1 = MathHelper.clamp(compound.getInteger("sizeY"), 0, 32);
		int j1 = MathHelper.clamp(compound.getInteger("sizeZ"), 0, 32);
		this.size = new BlockPos(l, i1, j1);

		try {
			this.mode = TileEntity_StructureController.Mode.valueOf(compound.getString("mode"));
		} catch (IllegalArgumentException var9) {
			this.mode = TileEntity_StructureController.Mode.LOAD;
		}

		this.showAir = compound.getBoolean("showair");
		this.showBoundingBox = compound.getBoolean("showboundingbox");

		this.updateBlockState();
	}

	private void updateBlockState() {
		if (this.world != null) {
			BlockPos blockpos = this.getPos();
			IBlockState iblockstate = this.world.getBlockState(blockpos);

			if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK) {
				this.world.setBlockState(blockpos, iblockstate.withProperty(SchematicController.MODE, this.mode), 2);
			}
		}
	}

	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 7, this.getUpdateTag());
	}

	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@SideOnly(Side.CLIENT)
	public BlockPos getPosition() {
		return this.position;
	}

	public void setPosition(BlockPos posIn) {
		this.position = posIn;
	}

	@SideOnly(Side.CLIENT)
	public BlockPos getStructureSize() {
		return this.size;
	}

	public void setSize(BlockPos sizeIn) {
		this.size = sizeIn;
	}
	
	public TileEntity_StructureController.Mode getMode() {
		return this.mode;
	}

	public void setMode(TileEntity_StructureController.Mode modeIn) {
		this.mode = modeIn;
		IBlockState iblockstate = this.world.getBlockState(this.getPos());

		if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK) {
			this.world.setBlockState(this.getPos(), iblockstate.withProperty(SchematicController.MODE, modeIn), 2);
		}
	}

	@SideOnly(Side.CLIENT)
	public void nextMode() {
		switch (this.getMode()) {
		case SAVE:
			this.setMode(TileEntity_StructureController.Mode.LOAD);
			break;
		case LOAD:
			this.setMode(TileEntity_StructureController.Mode.CORNER);
			break;
		case CORNER:
			this.setMode(TileEntity_StructureController.Mode.SAVE);
		}
	}

//	public boolean detectSize() {
//		if (this.mode != TileEntity_StructureController.Mode.SAVE) {
//			return false;
//		} else {
//			BlockPos blockpos = this.getPos();
//			int i = 80;
//			BlockPos blockpos1 = new BlockPos(blockpos.getX() - 80, 0, blockpos.getZ() - 80);
//			BlockPos blockpos2 = new BlockPos(blockpos.getX() + 80, 255, blockpos.getZ() + 80);
//			List<TileEntity_StructureController> list = this.getNearbyCornerBlocks(blockpos1, blockpos2);
//			List<TileEntity_StructureController> list1 = this.filterRelatedCornerBlocks(list);
//
//			if (list1.size() < 1) {
//				return false;
//			} else {
//				StructureBoundingBox structureboundingbox = this.calculateEnclosingBoundingBox(blockpos, list1);
//
//				if (structureboundingbox.maxX - structureboundingbox.minX > 1
//						&& structureboundingbox.maxY - structureboundingbox.minY > 1
//						&& structureboundingbox.maxZ - structureboundingbox.minZ > 1) {
//					this.position = new BlockPos(structureboundingbox.minX - blockpos.getX() + 1,
//							structureboundingbox.minY - blockpos.getY() + 1,
//							structureboundingbox.minZ - blockpos.getZ() + 1);
//					this.size = new BlockPos(structureboundingbox.maxX - structureboundingbox.minX - 1,
//							structureboundingbox.maxY - structureboundingbox.minY - 1,
//							structureboundingbox.maxZ - structureboundingbox.minZ - 1);
//					this.markDirty();
//					IBlockState iblockstate = this.world.getBlockState(blockpos);
//					this.world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
//					return true;
//				} else {
//					return false;
//				}
//			}
//		}
//	}

//	private List<TileEntity_StructureController> filterRelatedCornerBlocks(List<TileEntity_StructureController> p_184415_1_) {
//		Iterable<TileEntity_StructureController> iterable = Iterables.filter(p_184415_1_, new Predicate<TileEntity_StructureController>() {
//			public boolean apply(@Nullable TileEntity_StructureController p_apply_1_) {
//				return p_apply_1_.mode == TileEntity_StructureController.Mode.CORNER;
//			}
//		});
//		return Lists.newArrayList(iterable);
//	}

//	private List<TileEntity_StructureController> getNearbyCornerBlocks(BlockPos p_184418_1_, BlockPos p_184418_2_) {
//		List<TileEntity_StructureController> list = Lists.<TileEntity_StructureController>newArrayList();
//
//		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(p_184418_1_,
//				p_184418_2_)) {
//			IBlockState iblockstate = this.world.getBlockState(blockpos$mutableblockpos);
//
//			if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK) {
//				TileEntity tileentity = this.world.getTileEntity(blockpos$mutableblockpos);
//
//				if (tileentity != null && tileentity instanceof TileEntity_StructureController) {
//					list.add((TileEntity_StructureController) tileentity);
//				}
//			}
//		}
//
//		return list;
//	}

//	private StructureBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<TileEntity_StructureController> p_184416_2_) {
//		StructureBoundingBox structureboundingbox;
//
//		if (p_184416_2_.size() > 1) {
//			BlockPos blockpos = ((TileEntity_StructureController) p_184416_2_.get(0)).getPos();
//			structureboundingbox = new StructureBoundingBox(blockpos, blockpos);
//		} else {
//			structureboundingbox = new StructureBoundingBox(p_184416_1_, p_184416_1_);
//		}
//
//		for (TileEntity_StructureController TileEntity_Structure : p_184416_2_) {
//			BlockPos blockpos1 = TileEntity_Structure.getPos();
//
//			if (blockpos1.getX() < structureboundingbox.minX) {
//				structureboundingbox.minX = blockpos1.getX();
//			} else if (blockpos1.getX() > structureboundingbox.maxX) {
//				structureboundingbox.maxX = blockpos1.getX();
//			}
//
//			if (blockpos1.getY() < structureboundingbox.minY) {
//				structureboundingbox.minY = blockpos1.getY();
//			} else if (blockpos1.getY() > structureboundingbox.maxY) {
//				structureboundingbox.maxY = blockpos1.getY();
//			}
//
//			if (blockpos1.getZ() < structureboundingbox.minZ) {
//				structureboundingbox.minZ = blockpos1.getZ();
//			} else if (blockpos1.getZ() > structureboundingbox.maxZ) {
//				structureboundingbox.maxZ = blockpos1.getZ();
//			}
//		}
//
//		return structureboundingbox;
//	}

	@SideOnly(Side.CLIENT)
	public void writeCoordinates(ByteBuf buf) {
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
	}

	@SideOnly(Side.CLIENT)
	public boolean showsAir() {
		return this.showAir;
	}

	public void setShowAir(boolean showAirIn) {
		this.showAir = showAirIn;
	}

	@SideOnly(Side.CLIENT)
	public boolean showsBoundingBox() {
		return this.showBoundingBox;
	}

	public void setShowBoundingBox(boolean showBoundingBoxIn) {
		this.showBoundingBox = showBoundingBoxIn;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Nullable
	public ITextComponent getDisplayName() {
		return new TextComponentString("Structure Controller");
	}

	public static enum Mode implements IStringSerializable{
        SAVE("save", 0),
        LOAD("load", 1),
        CORNER("corner", 2);
       // DATA("data", 3);

        private static final TileEntity_StructureController.Mode[] MODES = new TileEntity_StructureController.Mode[values().length];
        private final String modeName;
        private final int modeId;

        private Mode(String modeName, int modeId)
        {
            this.modeName = modeName;
            this.modeId = modeId;
        }

        public String getName()
        {
            return this.modeName;
        }

        public int getModeId()
        {
            return this.modeId;
        }

        public static TileEntity_StructureController.Mode getById(int id)
        {
            return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
        }

        static
        {
            for (TileEntity_StructureController.Mode TileEntity_Structure$mode : values())
            {
                MODES[TileEntity_Structure$mode.getModeId()] = TileEntity_Structure$mode;
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
    public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox(){
        net.minecraft.util.math.AxisAlignedBB bb = INFINITE_EXTENT_AABB;
            bb = INFINITE_EXTENT_AABB;
        return bb;
    }
}