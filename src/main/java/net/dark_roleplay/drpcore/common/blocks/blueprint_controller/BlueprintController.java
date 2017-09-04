package net.dark_roleplay.drpcore.common.blocks.blueprint_controller;

import java.util.Random;

import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlueprintController extends Block implements ITileEntityProvider{
    public static final PropertyEnum<TE_BlueprintController.Mode> MODE = PropertyEnum.<TE_BlueprintController.Mode>create("mode", TE_BlueprintController.Mode.class);

    public BlueprintController(){
        super(Material.IRON, MapColor.SILVER);
        this.setDefaultState(this.blockState.getBaseState());
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TE_BlueprintController();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
    	TE_BlueprintController te = (TE_BlueprintController) world.getTileEntity(pos);

    	player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_STRUCTURE_CONTROLLER, world, pos.getX(), pos.getY(), pos.getZ());
//        return tileentity instanceof TileEntity_StructureController ? ((TileEntity_StructureController)tileentity).usedBy(playerIn) : false;
    	return false;
    }
    
    @Override
    public int quantityDropped(Random random){
        return 0;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(MODE, TE_BlueprintController.Mode.LOAD);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(MODE, TE_BlueprintController.Mode.getById(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return ((TE_BlueprintController.Mode)state.getValue(MODE)).getModeId();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {MODE});
    }

    private void trigger(TE_BlueprintController te){
//        switch (te.getMode()){
//            case SAVE:
//                te.save(false);
//                break;
//            case LOAD:
//                te.load(false);
//                break;
//            case CORNER:
//                te.unloadStructure();
//        }
    }
}
