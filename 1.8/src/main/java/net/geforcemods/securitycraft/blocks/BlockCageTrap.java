package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.api.IIntersectable;
import net.geforcemods.securitycraft.main.Utils;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCageTrap extends BlockOwnable implements IIntersectable {

	public static final PropertyBool DEACTIVATED = PropertyBool.create("deactivated");

	public BlockCageTrap(Material par2Material) {
		super(par2Material);
	}
	
	public boolean isOpaqueCube(){
        return false;
    }
	
	public int getRenderType(){
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }

	public AxisAlignedBB getCollisionBoundingBox(World par1World, BlockPos pos, IBlockState state){
		if(BlockUtils.getBlock(par1World, pos) == mod_SecurityCraft.cageTrap && !BlockUtils.getBlockPropertyAsBoolean(par1World, pos, DEACTIVATED)){
			return null;
		}else{
			return AxisAlignedBB.fromBounds((double) pos.getX() + this.minX, (double) pos.getY() + this.minY, (double) pos.getZ() + this.minZ, (double) pos.getX() + this.maxX, (double) pos.getY() + this.maxY, (double) pos.getZ() + this.maxZ);
		}
	}
	
	public void onEntityIntersected(World world, BlockPos pos, Entity entity) {
		if(!world.isRemote){
			if(entity instanceof EntityPlayer && !BlockUtils.getBlockPropertyAsBoolean(world, pos, DEACTIVATED)){
				BlockUtils.setBlockProperty(world, pos, DEACTIVATED, true);
				BlockUtils.setBlock(world, pos.up(4), mod_SecurityCraft.unbreakableIronBars);
				BlockUtils.setBlock(world, pos.getX() + 1, pos.getY() + 4, pos.getZ(), mod_SecurityCraft.unbreakableIronBars);	
				BlockUtils.setBlock(world, pos.getX() - 1, pos.getY() + 4, pos.getZ(), mod_SecurityCraft.unbreakableIronBars);	
				BlockUtils.setBlock(world, pos.getX(), pos.getY() + 4, pos.getZ() + 1, mod_SecurityCraft.unbreakableIronBars);	
				BlockUtils.setBlock(world, pos.getX(), pos.getY() + 4, pos.getZ() - 1, mod_SecurityCraft.unbreakableIronBars);	

				BlockUtils.setBlockInBox(world, pos.getX(), pos.getY(), pos.getZ(), mod_SecurityCraft.unbreakableIronBars);

				world.playSoundAtEntity(entity, "random.anvil_use", 3.0F, 1.0F);
				MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation(((EntityPlayer) entity).getName() + " was captured in a trap at " + Utils.getFormattedCoordinates(pos)));
			}
		}
	}
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(DEACTIVATED, false);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(DEACTIVATED, (meta == 1 ? true : false));
    }

    public int getMetaFromState(IBlockState state)
    {
    	return ((Boolean) state.getValue(DEACTIVATED)).booleanValue() ? 1 : 0;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {DEACTIVATED});
    }

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityOwnable().intersectsEntities();
	}
    
}
