package net.dark_roleplay.drpcore.common.network.packets.blocks;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SyncPacket_BlueprintBlock extends PacketBase.Server<SyncPacket_BlueprintBlock>{

	private BlockPos pos;
	private BlockPos offset;
	private BlockPos size;
	private String name;
	private TE_BlueprintController.Mode mode;
	private boolean showAir;
	private boolean showBoundingBox;
	
	public SyncPacket_BlueprintBlock(){
		
	}
	
	public SyncPacket_BlueprintBlock(TE_BlueprintController te){
		this.pos = te.getPos();
		this.offset = te.getOffset();
		this.size = te.getSize();
		this.name = te.getName();
		this.mode = te.getMode();
		this.showAir = te.showAir();
		this.showBoundingBox = te.showBoundingBox();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.showAir = buf.readBoolean();
		this.showBoundingBox = buf.readBoolean();
		this.mode = TE_BlueprintController.Mode.getById(buf.readShort());
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.offset = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.size = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.name = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.showAir);
		buf.writeBoolean(this.showBoundingBox);
		buf.writeShort(this.mode.getModeId());
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
		buf.writeInt(this.offset.getX());
		buf.writeInt(this.offset.getY());
		buf.writeInt(this.offset.getZ());
		buf.writeInt(this.size.getX());
		buf.writeInt(this.size.getY());
		buf.writeInt(this.size.getZ());
		ByteBufUtils.writeUTF8String(buf, this.name);
	}

	@Override
	public void handleServerSide(SyncPacket_BlueprintBlock message, EntityPlayer player) {
		player.getServer().addScheduledTask(new Runnable(){
			@Override
			public void run() {
				World world = player.getEntityWorld();
				TileEntity tileEntity = world.getTileEntity(message.pos);
				if(!(tileEntity instanceof TE_BlueprintController))
					return;
				TE_BlueprintController te = (TE_BlueprintController) tileEntity;
				
				te.setShowAir(message.showAir);
				te.setShowBoundingBox(message.showBoundingBox);
				te.setName(message.name);
				te.setOffset(message.offset);
				te.setSize(message.size);
				te.setMode(message.mode);
				te.markDirty();
				
				System.out.println(message.name);
			}
		});
	}

}
