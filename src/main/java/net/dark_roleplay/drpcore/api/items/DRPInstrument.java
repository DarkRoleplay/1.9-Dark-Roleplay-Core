package net.dark_roleplay.drpcore.api.items;

import net.dark_roleplay.drpcore.modules.work_in_progress.music.Song;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class DRPInstrument extends DRPItem{
	
	String instrumentName;

	public Song song;
	
	public DRPInstrument(String name, String instrumentName, int stackSize, String... subNames){
		this(name, instrumentName, null, stackSize, subNames);
	}
	
	public DRPInstrument(String name, String instrumentName, String itemFolder, int stackSize, String... subNames){
		super(name, itemFolder, stackSize, subNames);
		this.instrumentName = instrumentName;
		this.song = new Song(Song.musicString);
	}
	
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
		if(hand == EnumHand.OFF_HAND) return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));

		int note = 0;
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound tag = stack.getTagCompound();
		if(tag == null){
			tag = new NBTTagCompound();
			tag.setInteger("note", 0);
		}else if(tag.hasKey("note")){
			note = tag.getInteger("note");
			if(note + 1 < 26) tag.setInteger("note", note + 1);
			else tag.setInteger("note", 0);
		}else{
			tag.setInteger("note", 0);
		}
		stack.setTagCompound(tag);
		
		song.play(instrumentName, note, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player.getPosition());
		
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }
	
}
