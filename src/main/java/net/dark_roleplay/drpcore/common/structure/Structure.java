package net.dark_roleplay.drpcore.common.structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Structure {

	//TODO Schematics
	
	private int version;
	
	private String name;
	private String author;
	private String[] requiredMods;
	
	private short xSize;
	private short ySize;
	private short zSize;
	
	private int[] offset = new int[3];

	private IBlockState[] palette;
	
	int[] blockData;
	

	public void generate(World wolrd, BlockPos pos){
		
	}
	
	
	public boolean readStructure(String structurePath){
		try{
			InputStream stream = Structure.class.getResourceAsStream("/assets/drpcore/structures/"+structurePath);
			NBTTagCompound tag = CompressedStreamTools.readCompressed(stream);
			
			this.xSize = tag.getShort("Width");
			this.ySize = tag.getShort("Height");
			this.zSize = tag.getShort("Length");
			this.blockData = new int[this.xSize * this.ySize * this.zSize];
			this.palette = new IBlockState[tag.getInteger("PaletteMax")];
			
			NBTTagCompound palette = tag.getCompoundTag("Palette");
			
			for(String key : palette.getKeySet()){
				this.palette[palette.getInteger(key)] = Block.REGISTRY.getObject(new ResourceLocation(key)).getDefaultState();
			}
			
			//CompressedStreamTools.writeCompressed(compound, outputStream);
		}catch(Exception e){}
		return false;
	}
	
	public boolean writeStructure(String structureName, World world, BlockPos pos1, BlockPos pos2){
		NBTTagCompound tag = new NBTTagCompound();
		
		short width = (short) Math.abs(pos1.getX() - pos2.getX());
		short height = (short) Math.abs(pos1.getY() - pos2.getY());
		short length = (short) Math.abs(pos1.getZ() - pos2.getZ());
		
		pos1 = pos1.getX() > pos2.getX() ? new BlockPos(pos2.getX(), pos1.getY(), pos1.getZ()): pos1;
		pos1 = pos1.getY() > pos2.getY() ? new BlockPos(pos1.getX(), pos2.getY(), pos1.getZ()): pos1;
		pos1 = pos1.getZ() > pos2.getZ() ? new BlockPos(pos1.getX(), pos1.getY(), pos2.getZ()): pos1;
		
		tag.setShort("Width", width);
		tag.setShort("Height", height);
		tag.setShort("Length", length);
		//TODO FIx THAT Up
		
		List<String> blocks = new ArrayList<String>();
		int[] data = new int[width * height * length];
		
		NBTTagCompound palette = new NBTTagCompound();
		
		blocks.add(Blocks.AIR.getDefaultState().toString());
		palette.setInteger(Blocks.AIR.getDefaultState().toString(), 0);
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(int k = 0; k < length; k++){
					String state  = world.getBlockState(pos1.add(i,j,k)).toString();
					if(!blocks.contains(state)){
						blocks.add(state);
						palette.setInteger(state, blocks.indexOf(state));
					}
					data[i + k * width + j * width * length] = blocks.indexOf(state);
				}
			}
		}
		
		tag.setTag("Palette", palette);
		tag.setInteger("PaletteMax", blocks.size());
		
		tag.setIntArray("BlockData", data);
		
		String path = DarkRoleplayCore.configDir.getParentFile().getPath();
		File f = new File(path + "\\" + structureName + ".dat");

		f.getParentFile().mkdirs(); 

		FileOutputStream fop;
		try {
			f.createNewFile();
			fop = new FileOutputStream(f);
			CompressedStreamTools.writeCompressed(tag,fop);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
