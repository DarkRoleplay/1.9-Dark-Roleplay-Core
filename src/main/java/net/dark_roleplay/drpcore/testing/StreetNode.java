package net.dark_roleplay.drpcore.testing;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StreetNode {

	static Set<BlockPos> overlaps = new HashSet<BlockPos>();
	
	int parent = 0;
	BlockPos parentPos;
	
	BlockPos pos;
	
	StreetNode linkedNodeN;
	StreetNode linkedNodeE;
	StreetNode linkedNodeS;
	StreetNode linkedNodeW;
	
	public StreetNode(BlockPos pos, BlockPos parentPos, int parent){
		this.pos = pos;
		this.parent = parent;
		this.parentPos = parentPos;
	}
	
	public void clear(){
		BlockPos posNode = parentPos;

		int distanceX = posNode.getX() - pos.getX();
		int distanceZ = posNode.getZ() - pos.getZ();
		int steps = distanceX > 0 ? 1 : distanceX < 0 ? -1 : distanceZ > 0 ? 1 : distanceZ < 0 ? -1 : 0;
		if(distanceX != 0){
			for(int i = 0; i != distanceX; i += steps){
				if(overlaps.contains(new BlockPos(pos.getX() + i + steps, 0, pos.getZ())))
					overlaps.remove(new BlockPos(pos.getX() + i, 0, pos.getZ()));
				else
					return;
			}
		}else if(distanceZ != 0){
			for(int i = 0; i != distanceZ; i += steps){
				if(overlaps.contains(new BlockPos(pos.getX(), 0, pos.getZ() + i + steps)))
					overlaps.remove(new BlockPos(pos.getX(), 0, pos.getZ() + i));
				else
					return;	
			}
		}
	}
	
	public boolean intersects(){
		BlockPos posNode = parentPos;

		int distanceX = posNode.getX() - pos.getX();
		int distanceZ = posNode.getZ() - pos.getZ();
		int steps = distanceX > 0 ? 1 : distanceX < 0 ? -1 : distanceZ > 0 ? 1 : distanceZ < 0 ? -1 : 0;
		if(distanceX != 0){
			for(int i = 0; i != distanceX; i += steps){
				if(overlaps.contains(new BlockPos(pos.getX() + i, 0, pos.getZ())))
					return true;
				else
					overlaps.add(new BlockPos(pos.getX() + i, 0, pos.getZ()));
			}
		}else if(distanceZ != 0){
			for(int i = 0; i != distanceZ; i += steps){
				if(overlaps.contains(new BlockPos(pos.getX(), 0, pos.getZ() + i)))
					return true;
				else
					overlaps.add(new BlockPos(pos.getX(), 0, pos.getZ() + i));			
			}
		}
		return false;
	}
	
	public void generateAndConnectLinks(Random rnd, World world, int minDistance, int maxDistance, int depth){
		if(depth < 0)
			return;
		
		world.setBlockState(pos, Blocks.GOLD_ORE.getDefaultState());
		
		int roads = rnd.nextInt(4) + 1;
		int offset = rnd.nextInt(maxDistance - minDistance) + minDistance;
		
		for(int i = 0; i < roads; i++){
			switch(rnd.nextInt(4)){
			case 0:
				if(linkedNodeN == null)
					linkedNodeN = new StreetNode(pos.add(offset, 1, 0), pos, 3);
				if(linkedNodeN.intersects()){
					linkedNodeN.clear();
					linkedNodeN = null;
				}
				break;
			case 1:
				if(linkedNodeE == null)
					linkedNodeE = new StreetNode(pos.add(0, 1, offset), pos, 4);
				if(linkedNodeE.intersects()){
					linkedNodeE.clear();
					linkedNodeE = null;
				}
				break;
			case 2:
				if(linkedNodeS == null)
					linkedNodeS = new StreetNode(pos.add(-offset, 1, 0), pos, 1);
				if(linkedNodeS.intersects()){
					linkedNodeS.clear();
					linkedNodeS = null;
				}
				break;
			case 3:
				if(linkedNodeW == null)
					linkedNodeW = new StreetNode(pos.add(0, 1, -offset), pos, 2);
				if(linkedNodeW.intersects()){
					linkedNodeW.clear();
					linkedNodeW = null;
				}
				break;
			}
		}
		
		if(linkedNodeN != null && parent != 1){
			generateConnection(linkedNodeN, world);
			linkedNodeN.generateAndConnectLinks(rnd, world, minDistance, maxDistance, depth - 1);
		}else{
			linkedNodeN = null;
		}
		if(linkedNodeE != null && parent != 2){
			generateConnection(linkedNodeE, world);
			linkedNodeE.generateAndConnectLinks(rnd, world, minDistance, maxDistance, depth - 1);
		}else{
			linkedNodeE = null;
		}
		if(linkedNodeS != null && parent != 3){
			generateConnection(linkedNodeS, world);
			linkedNodeS.generateAndConnectLinks(rnd, world, minDistance, maxDistance, depth - 1);
		}else{
			linkedNodeS = null;
		}
		if(linkedNodeW != null && parent != 4){
			generateConnection(linkedNodeW, world);
			linkedNodeW.generateAndConnectLinks(rnd, world, minDistance, maxDistance, depth - 1);
		}else{
			linkedNodeW = null;
		}
	}
	
	public void generateConnection(StreetNode node, World world){
		boolean clean = false;
		BlockPos posNode = node.pos;
		
		if(!clean){
			world.setBlockState(posNode, Blocks.GOLD_ORE.getDefaultState());
		}else{
			world.setBlockState(posNode, Blocks.AIR.getDefaultState());
		}
		
		int distanceX = posNode.getX() - pos.getX();
		int distanceZ = posNode.getZ() - pos.getZ();
		int steps = distanceX > 0 ? 1 : distanceX < 0 ? -1 : distanceZ > 0 ? 1 : distanceZ < 0 ? -1 : 0;
		if(!clean){
			if(distanceX != 0){
				for(int i = 0; i != distanceX; i += steps){
					if(world.isAirBlock(pos.add(i, 0, 0)))
					world.setBlockState(pos.add(i, 0, 0), Blocks.WOOL.getDefaultState());
				}
			}else if(distanceZ != 0){
				for(int i = 0; i != distanceZ; i += steps){
					if(world.isAirBlock(pos.add(0, 0, i)))
					world.setBlockState(pos.add(0, 0, i), Blocks.WOOL.getDefaultState());
				}
			}
		}else{
			if(distanceX != 0){
				for(int i = 0; i != distanceX; i += steps)
					world.setBlockState(pos.add(i, 0, 0), Blocks.AIR.getDefaultState());
			}else if(distanceZ != 0){
				for(int i = 0; i != distanceZ; i += steps)
					world.setBlockState(pos.add(0, 0, i), Blocks.AIR.getDefaultState());
			}
		}
	}
}
