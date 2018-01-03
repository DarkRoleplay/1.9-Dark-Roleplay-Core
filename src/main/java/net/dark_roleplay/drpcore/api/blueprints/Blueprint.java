package net.dark_roleplay.drpcore.api.blueprints;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author JTK222 
 */
public class Blueprint {

	private List<String> requiredMods;
	private short sizeX, sizeY, sizeZ;
	private short palleteSize;
	private IBlockState[] pallete;
	private String name;
	private String[] architects;
	private String[] missingMods;
	
	private short[][][] structure;
	private NBTTagCompound[] tileEntities;
	
	public Blueprint(short sizeX, short sizeY, short sizeZ, short palleteSize, IBlockState[] pallete, short[][][] structure, NBTTagCompound[] tileEntities, List<String> requiredMods){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.palleteSize = palleteSize;
		this.pallete = pallete;
		this.structure = structure;
		this.tileEntities = tileEntities;
		this.requiredMods = requiredMods;
	}
	
	public void build(World world, BlockPos pos){
		build(world, pos, Rotation.NONE);
	}

	public void build(World world, BlockPos pos, Rotation rotation){
		IBlockState[] pallete = new IBlockState[this.pallete.length];
		for(int i = 0; i < pallete.length; i++){
			pallete[i] = this.pallete[i].withRotation(rotation);
		}
		build(world, pos, pallete, this.structure, rotation);
	}

	public void build(World world, BlockPos pos, IBlockState[] pallete, short[][][] structure, Rotation rotation){
		for(short y = 0; y < structure.length; y ++){
			for(short z = 0; z < structure[0].length; z++){
				for(short x = 0; x < structure[0][0].length; x++){
					
					IBlockState state = pallete[structure[y][z][x] & 0xFFFF];
					if(state.getBlock() == Blocks.STRUCTURE_VOID)
						continue;
					if(state.isFullCube()){
						BlockPos rotated = transformedBlockPos(x, y, z, Mirror.NONE, rotation);
						world.setBlockState(pos.add(rotated.getX(), rotated.getY(), rotated.getZ()), state, 18);
					}
				}
			}
		}

		for(short y = 0; y < this.getSizeY(); y ++){
			for(short z = 0; z < this.getSizeZ(); z++){
				for(short x = 0; x < this.getSizeX(); x++){
					IBlockState state = pallete[structure[y][z][x]];
					if(state.getBlock() == Blocks.STRUCTURE_VOID)
						continue;
					if(!state.isFullCube()){
						BlockPos rotated = transformedBlockPos(x, y, z, Mirror.NONE, rotation);
						world.setBlockState(pos.add(rotated.getX(), rotated.getY(), rotated.getZ()), state, 18);
					}
				}
			}
		}

		if(this.getTileEntities() != null){
			for(NBTTagCompound tag : this.getTileEntities()){
				TileEntity te = world.getTileEntity(pos.add(tag.getShort("x"), tag.getShort("y"), tag.getShort("z")));
				tag.setInteger("x", pos.getX() + tag.getShort("x"));
				tag.setInteger("y", pos.getY() + tag.getShort("y"));
				tag.setInteger("z", pos.getZ() + tag.getShort("z"));
				te.deserializeNBT(tag);
			}
		}
	}
	
	public void remove(World world, BlockPos pos){
		remove(world, pos, Rotation.NONE);
	}

	public void remove(World world, BlockPos pos, Rotation rotation){
		remove(world, pos, this.pallete, this.structure, rotation);
	}
	
	public void remove(World world, BlockPos pos, IBlockState[] pallete, short[][][] structure, Rotation rotation){
		int indexVoid = -1;
		for(short y = 0; y < structure.length; y ++){
			for(short z = 0; z < structure[0].length; z++){
				for(short x = 0; x < structure[0][0].length; x++){
					
					if(indexVoid == -1){
						IBlockState state = pallete[structure[y][z][x] & 0xFFFF];
						if(state.getBlock() == Blocks.STRUCTURE_VOID){
							indexVoid = structure[y][z][x];
						}else{
							BlockPos rotated = transformedBlockPos(x, y, z, Mirror.NONE, rotation);
							world.setBlockToAir(pos.add(rotated.getX(), rotated.getY(), rotated.getZ()));
						}
					}else if(structure[y][z][x] == indexVoid){
						continue;
					}else{
						BlockPos rotated = transformedBlockPos(x, y, z, Mirror.NONE, rotation);
						world.setBlockToAir(pos.add(rotated.getX(), rotated.getY(), rotated.getZ()));
					}
				}
			}
		}
	}
	
	private static BlockPos transformedBlockPos(int x, int y, int z, Mirror mirror, Rotation rotation){
        boolean flag = true;

        switch (mirror){
            case LEFT_RIGHT:
                z = -z;
                break;
            case FRONT_BACK:
                x = -x;
                break;
            default:
                flag = false;
        }

        switch (rotation){
            case COUNTERCLOCKWISE_90:
                return new BlockPos(z, y, -x);
            case CLOCKWISE_90:
                return new BlockPos(-z, y, x);
            case CLOCKWISE_180:
                return new BlockPos(-x, y, -z);
            default:
                return flag ? new BlockPos(x, y, z) : new BlockPos(x, y, z);
        }
    }
	
	public boolean canBuild(World world, BlockPos pos){
		IBlockState[] pallete = this.getPallete();
		short[][][] structure = this.getStructure();
		for(short y = 0; y < this.getSizeY(); y ++){
			for(short z = 0; z < this.getSizeZ(); z++){
				for(short x = 0; x < this.getSizeX(); x++){
					IBlockState state = pallete[structure[y][z][x] & 0xFFFF];
					if(state.getBlock() == Blocks.STRUCTURE_VOID)
						continue;
					if(!world.getBlockState(pos.add(x,y,z)).getBlock().isReplaceable(world, pos.add(x,y,z)))
						return false;
				}
			}
		}
		return true;
	}

	public short getSizeX() {
		return sizeX;
	}

	public short getSizeY() {
		return sizeY;
	}

	public short getSizeZ() {
		return sizeZ;
	}

	public short getPalleteSize() {
		return palleteSize;
	}

	public IBlockState[] getPallete() {
		return pallete;
	}

	public short[][][] getStructure() {
		return structure;
	}

	public NBTTagCompound[] getTileEntities() {
		return tileEntities;
	}

	public List<String> getRequiredMods() {
		return requiredMods;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String[] getArchitects() {
		return architects;
	}


	public void setArchitects(String[] architects) {
		this.architects = architects;
	}
	
	public String[] getMissingMods(){
		return this.missingMods;
	}
	
	public void setMissingMods(String... missingMods){
		this.missingMods = missingMods;
	}
}
