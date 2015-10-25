package net.geforcemods.securitycraft.blocks.mines;

import net.geforcemods.securitycraft.api.IExplosive;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.minecraft.block.BlockRail;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockTrackMine extends BlockRail implements IExplosive, ITileEntityProvider {
	
	public BlockTrackMine() {
		super();
	}

	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		if(par5EntityLivingBase instanceof EntityPlayer){
			((TileEntityOwnable) par1World.getTileEntity(pos)).setOwner(((EntityPlayer) par5EntityLivingBase).getGameProfile().getId().toString(), ((EntityPlayer) par5EntityLivingBase).getName());
		}
	}

	public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos){
		BlockUtils.destroyBlock(world, pos, false);

		world.createExplosion(cart, pos.getX(), pos.getY() + 1, pos.getZ(), mod_SecurityCraft.configHandler.smallerMineExplosion ? 4.0F : 8.0F, true);

		cart.setDead();
    }
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

	public void explode(World world, BlockPos pos) {
		BlockUtils.destroyBlock(world, pos, false);
		world.createExplosion((Entity) null, pos.getX(), pos.up().getY(), pos.getZ(), mod_SecurityCraft.configHandler.smallerMineExplosion ? 4.0F : 8.0F, true);
	}

	public void activateMine(World world, BlockPos pos) {}

	public void defuseMine(World world, BlockPos pos) {}

	public boolean isActive(World world, BlockPos pos) {
		return true;
	}

	public boolean isDefusable() {
		return false;
	}

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityOwnable();
	}

}
