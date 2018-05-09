package net.dark_roleplay.drpcore.client.build_mode;

import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

public class TileEntityBuilder extends TileEntity{

	protected BlockPos offset = new BlockPos(0, 1, 0);
	protected BlockPos size = new BlockPos(5, 5, 5);
	
	public BlockPos getOffset() {
		return this.offset;
	}

	public BlockPos getSize() {
		return this.size;
	}
	
	public void setOffset(BlockPos offset) {
		this.offset = offset;
	}

	public void setSize(BlockPos size) {
		this.size = size;
	}
	
	public static enum Mode implements IStringSerializable {
		SAVE("save", 0), LOAD("load", 1), CORNER("corner", 2);

		private static final TE_BlueprintController.Mode[] MODES = new TE_BlueprintController.Mode[values().length];
		private final String modeName;
		private final int modeId;

		private Mode(String modeName, int modeId) {
			this.modeName = modeName;
			this.modeId = modeId;
		}

		public String getName() {
			return this.modeName;
		}

		public int getModeId() {
			return this.modeId;
		}

		public static TE_BlueprintController.Mode getById(int id) {
			return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
		}

		static {
			for (TE_BlueprintController.Mode TileEntity_Structure$mode : values()) {
				MODES[TileEntity_Structure$mode.getModeId()] = TileEntity_Structure$mode;
			}
		}
	}

	public static enum RenderMode implements IStringSerializable {
		NONE("none", 0), BOUNDING_BOX("bounding_box", 1), INVISIBLE("all", 2);

		private static final TE_BlueprintController.RenderMode[] MODES = new TE_BlueprintController.RenderMode[values().length];
		private final String modeName;
		private final int modeId;

		private RenderMode(String modeName, int modeId) {
			this.modeName = modeName;
			this.modeId = modeId;
		}

		public String getName() {
			return this.modeName;
		}

		public int getModeId() {
			return this.modeId;
		}

		public static TE_BlueprintController.RenderMode getById(int id) {
			return id >= 0 && id < MODES.length ? MODES[id] : MODES[0];
		}

		static {
			for (TE_BlueprintController.RenderMode TileEntity_Structure$mode : values()) {
				MODES[TileEntity_Structure$mode.getModeId()] = TileEntity_Structure$mode;
			}
		}
	}
}
