package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockIronTrapDoor extends BlockTrapDoor implements ITileEntityProvider {

	public BlockIronTrapDoor(Material materialIn) {
		super(materialIn);
	}

	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		if(par5EntityLivingBase instanceof EntityPlayer){
			((TileEntityOwnable) par1World.getTileEntity(pos)).setOwner(((EntityPlayer) par5EntityLivingBase).getGameProfile().getId().toString(), ((EntityPlayer) par5EntityLivingBase).getName());
		}
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
		return false;
	}

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
    
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
    }

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityOwnable();
	}
    
}