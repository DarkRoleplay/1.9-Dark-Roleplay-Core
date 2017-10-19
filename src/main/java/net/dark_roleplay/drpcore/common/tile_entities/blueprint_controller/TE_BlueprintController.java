package net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.blocks.blueprint_controller.BlueprintController;
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
import net.minecraft.util.math.AxisAlignedBB;
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

public class TE_BlueprintController extends TileEntity {
	
	private String name = "";
	private String architects = "";
//	private String[] authors = new String[32]; TODO ADD ARCHITECTS
	private BlockPos offset = new BlockPos(0, 1, 0);
	private BlockPos size = new BlockPos( 5, 5, 5);
	
	private TE_BlueprintController.Mode mode = TE_BlueprintController.Mode.SAVE;
	
	private boolean showAir = false;
	private boolean showBoundingBox = true;
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("posX", this.offset.getX());
		compound.setInteger("posY", this.offset.getY());
		compound.setInteger("posZ", this.offset.getZ());
		compound.setInteger("sizeX", this.size.getX());
		compound.setInteger("sizeY", this.size.getY());
		compound.setInteger("sizeZ", this.size.getZ());
		compound.setString("mode", this.mode.toString());
		compound.setBoolean("showair", this.showAir);
		compound.setBoolean("showboundingbox", this.showBoundingBox);
		compound.setString("name", this.name);
		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int i = MathHelper.clamp(compound.getInteger("posX"), -32, 32);
		int j = MathHelper.clamp(compound.getInteger("posY"), -32, 32);
		int k = MathHelper.clamp(compound.getInteger("posZ"), -32, 32);
		this.offset = new BlockPos(i, j, k);
		int l = MathHelper.clamp(compound.getInteger("sizeX"), 0, 32);
		int i1 = MathHelper.clamp(compound.getInteger("sizeY"), 0, 32);
		int j1 = MathHelper.clamp(compound.getInteger("sizeZ"), 0, 32);
		this.size = new BlockPos(l, i1, j1);

		try {
			this.mode = TE_BlueprintController.Mode.valueOf(compound.getString("mode"));
		} catch (IllegalArgumentException var9) {
			this.mode = TE_BlueprintController.Mode.LOAD;
		}

		this.showAir = compound.getBoolean("showair");
		this.showBoundingBox = compound.getBoolean("showboundingbox");

		this.name = compound.hasKey("name") ? compound.getString("name") : "";
		//TODO UPDATE BLOCK MODEL FOR MODE
	}

	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 7, this.getUpdateTag());
	}

	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	public BlockPos getOffset() {
		return this.offset;
	}

	public BlockPos getSize(){
		return this.size;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getArchitects(){
		return this.architects;
	}
	
	public Mode getMode(){
		return this.mode;
	}

	public boolean showAir(){
		return this.showAir;
	}
	
	public boolean showBoundingBox(){
		return this.showBoundingBox;
	}
	
	public void setOffset(BlockPos offset){
		this.offset = offset;
	}
	
	public void setSize(BlockPos size){
		this.size = size;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void setArchitects(String architects){
		this.architects = architects;
	}
	
	public void setMode(Mode mode){
		this.mode = mode;
	}
	
	public void setShowAir(boolean showAir){
		this.showAir = showAir;
	}
	
	public void setShowBoundingBox(boolean showBoundingBox){
		this.showBoundingBox = showBoundingBox;
	}
	
	public static enum Mode implements IStringSerializable{
        SAVE("save", 0),
        LOAD("load", 1),
        CORNER("corner", 2);
       // DATA("data", 3);

        private static final TE_BlueprintController.Mode[] MODES = new TE_BlueprintController.Mode[values().length];
        private final String modeName;
        private final int modeId;

        private Mode(String modeName, int modeId){
            this.modeName = modeName;
            this.modeId = modeId;
        }

        public String getName(){
            return this.modeName;
        }

        public int getModeId(){
            return this.modeId;
        }

        public static TE_BlueprintController.Mode getById(int id){
            return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
        }

        static{
            for (TE_BlueprintController.Mode TileEntity_Structure$mode : values()){
                MODES[TileEntity_Structure$mode.getModeId()] = TileEntity_Structure$mode;
            }
        }
    }
	
	public static enum RenderMode implements IStringSerializable{
        NONE("none", 0),
        BOUNDING_BOX("bounding box", 1),
        INVISIBLE("everything", 2);
       // DATA("data", 3);

        private static final TE_BlueprintController.RenderMode[] MODES = new TE_BlueprintController.RenderMode[values().length];
        private final String modeName;
        private final int modeId;

        private RenderMode(String modeName, int modeId){
            this.modeName = modeName;
            this.modeId = modeId;
        }

        public String getName(){
            return this.modeName;
        }

        public int getModeId(){
            return this.modeId;
        }

        public static TE_BlueprintController.RenderMode getById(int id){
            return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
        }

        static{
            for (TE_BlueprintController.RenderMode TileEntity_Structure$mode : values()){
                MODES[TileEntity_Structure$mode.getModeId()] = TileEntity_Structure$mode;
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox(){
        return INFINITE_EXTENT_AABB;
    }
}