package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.gui.GuiHandler;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.main.Utils.PlayerUtils;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKeypadFurnace extends BlockOwnable {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool OPEN = PropertyBool.create("open");
	
	public BlockKeypadFurnace(Material materialIn) {
		super(materialIn);
	}
	
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean isNormalCube()
    {
        return false;
    }
	
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumFacing side, float par7, float par8, float par9){
        if(!par1World.isRemote){     
			if(par5EntityPlayer.getCurrentEquippedItem() == null || (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().getItem() != mod_SecurityCraft.Codebreaker)){
		        TileEntityKeypadFurnace TE = (TileEntityKeypadFurnace) par1World.getTileEntity(pos);
		        if(TE.getPassword() != null && !TE.getPassword().isEmpty()){
					par5EntityPlayer.openGui(mod_SecurityCraft.instance, GuiHandler.INSERT_PASSWORD_ID, par1World, pos.getX(), pos.getY(), pos.getZ());
				}else{
					par5EntityPlayer.openGui(mod_SecurityCraft.instance, GuiHandler.SETUP_PASSWORD_ID, par1World, pos.getX(), pos.getY(), pos.getZ());
				}
			}else{
				if(mod_SecurityCraft.instance.configHandler.allowCodebreakerItem){
					if(((IPasswordProtected) par1World.getTileEntity(pos)).getPassword() != null ){
						activate(par1World, pos, par5EntityPlayer);
					}
				}else{	
					PlayerUtils.sendMessageToPlayer(par5EntityPlayer, StatCollector.translateToLocal("tile.keypadFurnace.name"), StatCollector.translateToLocal("messages.codebreakerDisabled"), EnumChatFormatting.RED);  				
				}	
			}
        }
        
        return true;
    }
	
	public static void activate(World par1World, BlockPos pos, EntityPlayer player){
		if(!BlockUtils.getBlockPropertyAsBoolean(par1World, pos, BlockKeypadFurnace.OPEN)){
			BlockUtils.setBlockProperty(par1World, pos, BlockKeypadFurnace.OPEN, true, false);
		}
		
		par1World.playAuxSFXAtEntity((EntityPlayer)null, 1006, pos, 0);
		player.openGui(mod_SecurityCraft.instance, 16, par1World, pos.getX(), pos.getY(), pos.getZ());
	}
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(OPEN, false);
    }
    
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        if(meta <= 5){
        	return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta].getAxis() == EnumFacing.Axis.Y ? EnumFacing.NORTH : EnumFacing.values()[meta]).withProperty(OPEN, false);
        }else{
        	return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta - 6]).withProperty(OPEN, true);
        }
    }

    public int getMetaFromState(IBlockState state)
    {
    	if(((Boolean) state.getValue(OPEN)).booleanValue()){
    		return (((EnumFacing) state.getValue(FACING)).getIndex() + 6);
    	}else{
    		return ((EnumFacing) state.getValue(FACING)).getIndex();
    	}
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING, OPEN});
    }
	
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityKeypadFurnace();
	}

}
