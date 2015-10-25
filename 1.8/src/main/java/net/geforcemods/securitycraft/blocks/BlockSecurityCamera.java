package net.geforcemods.securitycraft.blocks;

import java.util.Iterator;

import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.entity.EntitySecurityCamera;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.main.Utils.PlayerUtils;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.tileentity.TileEntitySecurityCamera;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSecurityCamera extends BlockContainer{

    public static final PropertyEnum FACING = PropertyEnum.create("facing", BlockLever.EnumOrientation.class);
    public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockSecurityCamera(Material par2Material) {
		super(par2Material);
	}
	
	public AxisAlignedBB getCollisionBoundingBox(World par1World, BlockPos pos, IBlockState state){
        return null;
    }
	
	public int getRenderType(){
		return -1;
	}
	
	public boolean isOpaqueCube(){
		return false;
	}

	public boolean isFullCube(){
		return false;
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
		BlockLever.EnumOrientation dir = BlockUtils.getBlockPropertyAsOrientation((World) world, pos, FACING);
        
    	if(dir == BlockLever.EnumOrientation.SOUTH){
    		this.setBlockBounds(0.275F, 0.250F, 0.000F, 0.700F, 0.800F, 0.850F);
    	}else if(dir == BlockLever.EnumOrientation.NORTH){
    		this.setBlockBounds(0.275F, 0.250F, 0.150F, 0.700F, 0.800F, 1.000F);
        }else if(dir == BlockLever.EnumOrientation.WEST){
    		this.setBlockBounds(0.125F, 0.250F, 0.275F, 1.000F, 0.800F, 0.725F);
        }else{
    		this.setBlockBounds(0.000F, 0.250F, 0.275F, 0.850F, 0.800F, 0.725F);
        }
	}
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        IBlockState iblockstate = this.getDefaultState().withProperty(POWERED, Boolean.valueOf(false));

        if(worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing)){
            return iblockstate.withProperty(FACING, BlockLever.EnumOrientation.forFacings(facing, placer.getHorizontalFacing())).withProperty(POWERED, false);
        }else{
            Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            EnumFacing enumfacing1;

            do{
                if(!iterator.hasNext()){               
                    return iblockstate;
                }

                enumfacing1 = (EnumFacing)iterator.next();
            }while (!worldIn.isSideSolid(pos.offset(enumfacing1.getOpposite()), enumfacing1));

            return iblockstate.withProperty(FACING, BlockLever.EnumOrientation.forFacings(enumfacing1, placer.getHorizontalFacing())).withProperty(POWERED, false);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack){
        if(par5EntityLivingBase instanceof EntityPlayer){
    		((TileEntityOwnable) par1World.getTileEntity(pos)).setOwner(((EntityPlayer) par5EntityLivingBase).getGameProfile().getId().toString(), ((EntityPlayer) par5EntityLivingBase).getName());
    	}
    }	
    
    public void onNeighborBlockChange(World par1World, BlockPos pos, IBlockState state, Block par5Block){    			
		if(BlockUtils.getBlockPropertyAsOrientation(par1World, pos, FACING) == BlockLever.EnumOrientation.NORTH){
			if(!par1World.isSideSolid(pos.south(), EnumFacing.NORTH)){
				BlockUtils.destroyBlock(par1World, pos, true);
			}
		}else if(BlockUtils.getBlockPropertyAsOrientation(par1World, pos, FACING) == BlockLever.EnumOrientation.SOUTH){
			if(!par1World.isSideSolid(pos.north(), EnumFacing.SOUTH)){
				BlockUtils.destroyBlock(par1World, pos, true);
			}
		}else if(BlockUtils.getBlockPropertyAsOrientation(par1World, pos, FACING) == BlockLever.EnumOrientation.EAST){
			if(!par1World.isSideSolid(pos.west(), EnumFacing.EAST)){
				BlockUtils.destroyBlock(par1World, pos, true);
			}
		}else if(BlockUtils.getBlockPropertyAsOrientation(par1World, pos, FACING) == BlockLever.EnumOrientation.WEST){
			if(!par1World.isSideSolid(pos.east(), EnumFacing.WEST)){
				BlockUtils.destroyBlock(par1World, pos, true);
			}
		}
	}
    
    public void mountCamera(World world, int par2, int par3, int par4, int par5, EntityPlayer player){
    	if(!world.isRemote && player.ridingEntity == null) {
    		PlayerUtils.sendMessageToPlayer(player, StatCollector.translateToLocal("tile.securityCamera.name"), StatCollector.translateToLocal("messages.securityCamera.mounted"), EnumChatFormatting.GREEN);
    	}
    	
    	if(player.ridingEntity != null && player.ridingEntity instanceof EntitySecurityCamera){
			EntitySecurityCamera dummyEntity = new EntitySecurityCamera(world, par2, par3, par4, par5, (EntitySecurityCamera) player.ridingEntity);
			world.spawnEntityInWorld(dummyEntity);
			player.mountEntity(dummyEntity);
			return;
		}

    	EntitySecurityCamera dummyEntity = new EntitySecurityCamera(world, par2, par3, par4, par5, player);
    	world.spawnEntityInWorld(dummyEntity);
    	player.mountEntity(dummyEntity);
    }
    
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side){
        return (side != EnumFacing.UP && side != EnumFacing.DOWN) ? super.canPlaceBlockOnSide(worldIn, pos, side) : false;
    }
    
    public boolean canPlaceBlockAt(World world, BlockPos pos){
        return !world.getBlockState(pos).getBlock().isReplaceable(world, pos) ^ //exclusive or
        	   (world.isSideSolid(pos.west(), EnumFacing.EAST, true) ||
               world.isSideSolid(pos.east(), EnumFacing.WEST, true) ||
               world.isSideSolid(pos.north(), EnumFacing.SOUTH, true) ||
               world.isSideSolid(pos.south(), EnumFacing.NORTH, true));
    }
    
    public boolean canProvidePower(){
        return true;
    }
    
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, BlockPos pos, IBlockState state, EnumFacing side){
    	if(((Boolean) state.getValue(POWERED)).booleanValue() && ((CustomizableSCTE) par1IBlockAccess.getTileEntity(pos)).hasModule(EnumCustomModules.REDSTONE)){
    		return 15;
    	}else{
    		return 0;
    	}
    }
    
    public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, BlockPos pos, IBlockState state, EnumFacing side){  	
    	if(((Boolean) state.getValue(POWERED)).booleanValue() && ((CustomizableSCTE) par1IBlockAccess.getTileEntity(pos)).hasModule(EnumCustomModules.REDSTONE)){
    		return 15;
    	}else{
    		return 0;
    	}
    }
  
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, BlockLever.EnumOrientation.SOUTH);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        if(meta <= 5){
        	return this.getDefaultState().withProperty(FACING, (BlockLever.EnumOrientation.values()[meta].getFacing() == EnumFacing.UP || BlockLever.EnumOrientation.values()[meta].getFacing() == EnumFacing.DOWN) ? BlockLever.EnumOrientation.NORTH : BlockLever.EnumOrientation.values()[meta]).withProperty(POWERED, false);
        }else{
        	return this.getDefaultState().withProperty(FACING, BlockLever.EnumOrientation.values()[meta - 6]).withProperty(POWERED, true);
        }
    }

    public int getMetaFromState(IBlockState state)
    {
    	if(((Boolean) state.getValue(POWERED)).booleanValue()){
    		return (((BlockLever.EnumOrientation) state.getValue(FACING)).getMetadata() + 6);
    	}else{
    		return ((BlockLever.EnumOrientation) state.getValue(FACING)).getMetadata();
    	}
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING, POWERED});
    }
    
    public TileEntity createNewTileEntity(World world, int par2){
    	return new TileEntitySecurityCamera();
    }

}
