package net.dark_roleplay.drpcore.common.network.packets.blocks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.blueprints.Blueprint;
import net.dark_roleplay.drpcore.api.blueprints.BlueprintUtil;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Packet_SaveBlueprint extends PacketBase.Server<Packet_SaveBlueprint>{

	private BlockPos pos;
	private BlockPos offset;
	private BlockPos size;
	private String name;
	private String architects;
	private TE_BlueprintController.Mode mode;
	
	public Packet_SaveBlueprint(){}
	
	public Packet_SaveBlueprint(TE_BlueprintController te){
		this.pos = te.getPos();
		this.offset = te.getOffset();
		this.size = te.getSize();
		this.name = te.getName();
		this.architects = te.getArchitects();
		this.mode = te.getMode();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.mode = TE_BlueprintController.Mode.getById(buf.readShort());
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.offset = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.size = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		this.name = ByteBufUtils.readUTF8String(buf);
		this.architects = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
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
		ByteBufUtils.writeUTF8String(buf, this.architects);
	}

	@Override
	public void handleServerSide(Packet_SaveBlueprint message, EntityPlayer player) {
		player.getServer().addScheduledTask(new Runnable(){
			@Override
			public void run() {
				World world = player.getEntityWorld();
				TileEntity tileEntity = world.getTileEntity(message.pos);
				if(!(tileEntity instanceof TE_BlueprintController))
					return;
				TE_BlueprintController te = (TE_BlueprintController) tileEntity;
				
				te.setName(message.name);
				te.setOffset(message.offset);
				te.setSize(message.size);
				te.setMode(message.mode);
				te.markDirty();
				
				DRPCoreInfo.DARK_ROLEPLAY_BLUEPRINTS_FOLDER.mkdirs();
				File structure = new File(DRPCoreInfo.DARK_ROLEPLAY_BLUEPRINTS_FOLDER.getPath() + "/" + message.name + ".blueprint");
				structure.getParentFile().mkdirs();
				
				Blueprint bp = BlueprintUtil.createBlueprint(te.getWorld(), te.getPos().add(message.offset.getX(), message.offset.getY(), message.offset.getZ()), (short) message.size.getX(), (short) message.size.getY(), (short) message.size.getZ(), message.name, message.architects);
				player.getServer().addScheduledTask(new Runnable(){
					@Override
					public void run() {
						try {
							structure.createNewFile();
							OutputStream os = new FileOutputStream(structure);
							BlueprintUtil.writeToFile(os, bp);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

}
