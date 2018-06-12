package net.dark_roleplay.core.api.old.modules.work_in_progress.music;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Note {

	protected SoundEvent instrument;
	protected byte note;
	
	public Note(SoundEvent instrument, int note){
		this.instrument = instrument;
		this.note = (byte) note;
	}
	
	public void playSound(World world, BlockPos pos){
        float f = (float)Math.pow(2.0D, (double)((double)(note) - 12d) / 12.0D);
        world.playSound(Minecraft.getMinecraft().player, pos, instrument, SoundCategory.RECORDS, 30.0F, f);
	}
	
	
}
