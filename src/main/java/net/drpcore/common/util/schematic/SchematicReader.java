package net.drpcore.common.util.schematic;

import java.io.FileInputStream;
import java.io.InputStream;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class SchematicReader {

	
	public static Schematic readSchematic(String ModName, String StructureName){
		Schematic schematic = null;
		
		short width;
		short height;
		short length;
	
		BlockPos[] Positions;
		IBlockState[] States;
		
		try{
			InputStream is = new FileInputStream(DarkRoleplayCore.configManager.getConfigFolder() + "\\" + ModName + "\\" + StructureName);
	
				NBTTagCompound nbtdata = CompressedStreamTools.readCompressed(is);
				
				is.close();
				
				width = nbtdata.getShort("Width");
				height = nbtdata.getShort("Height");
				length = nbtdata.getShort("Length");
				
				Positions = new BlockPos[width * height * length];
				States = new IBlockState[width * height * length];
				
				byte[] blockIDs = nbtdata.getByteArray("Blocks");
				byte[] metadata = nbtdata.getByteArray("Data");
				
				int counter = 0;
				for(int y = 0; y < height; y++){
					for(int z = 0; z < length; z++){
						for(int x = 0; x < width; x++){
							Positions[counter] = new BlockPos(x,y,z);
							States[counter] = Block.getBlockById(blockIDs[counter]).getStateFromMeta(metadata[counter]);
							counter ++;
						}					
					}
				}
				schematic = new Schematic(Positions, States, width, height, length);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return schematic;
	}
	
}
