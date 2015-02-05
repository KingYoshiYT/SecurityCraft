package org.freeforums.geforce.securitycraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.freeforums.geforce.securitycraft.main.HelpfulMethods;
import org.freeforums.geforce.securitycraft.main.Utils;
import org.freeforums.geforce.securitycraft.main.mod_SecurityCraft;
import org.freeforums.geforce.securitycraft.misc.EnumCustomModules;
import org.freeforums.geforce.securitycraft.tileentity.CustomizableSCTE;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityInventoryScanner;
import org.freeforums.geforce.securitycraft.tileentity.TileEntityInventoryScannerBlock;

public class BlockInventoryScannerBlock extends BlockContainer{
    
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockInventoryScannerBlock(Material par2Material) {
		super(par2Material);
		//this.setBlockBounds(0.250F, 0.300F, 0.300F, 0.750F, 0.700F, 0.700F);

	}

	public AxisAlignedBB getCollisionBoundingBox(World par1World, BlockPos pos, IBlockState state)
    {
        return null;
    }
	
	public boolean isOpaqueCube(){
		return false;	
	}
	
	/**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean isNormalCube()
    {
        return false;
    }
    
    public int getRenderType(){
    	return 3;
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World par1World, BlockPos pos, IBlockState state, Block block) {
    	if(par1World.isRemote){
    		return;
    	}else{
    		if(!HelpfulMethods.hasInventoryScannerFacingBlock(par1World, pos)){
        		par1World.destroyBlock(pos, false);
        	}
    	}
    }
      
    public static void checkForPlayer(World par1World, BlockPos pos, Entity par5Entity){
    	if(par5Entity instanceof EntityPlayer){   	        	
        	if(par1World.getTileEntity(pos.west()) != null && par1World.getTileEntity(pos.west()) instanceof TileEntityInventoryScanner){    
	        	if(HelpfulMethods.checkForModule(par1World, pos.west(), ((EntityPlayer) par5Entity), EnumCustomModules.WHITELIST)){ return; }
        		for(int i = 0; i < 10; i++){
        			for(int j = 0; j < ((EntityPlayer) par5Entity).inventory.mainInventory.length; j++){
        				if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.west())).getStackInSlotCopy(i) != null){       				
        					if(((EntityPlayer) par5Entity).inventory.mainInventory[j] != null){
        						checkInventory(((EntityPlayer) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.west())), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.west())).getStackInSlotCopy(i));
        					}       					
        				}
        			}
        		}
        	}else if(par1World.getTileEntity(pos.east()) != null && par1World.getTileEntity(pos.east()) instanceof TileEntityInventoryScanner){
	        	if(HelpfulMethods.checkForModule(par1World, pos.east(), ((EntityPlayer) par5Entity), EnumCustomModules.WHITELIST)){ return; }
        		for(int i = 0; i < 10; i++){
        			for(int j = 0; j < ((EntityPlayer) par5Entity).inventory.mainInventory.length; j++){
        				if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.east())).getStackInSlotCopy(i) != null){       				
        					if(((EntityPlayer) par5Entity).inventory.mainInventory[j] != null){
        						checkInventory(((EntityPlayer) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.east())), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.east())).getStackInSlotCopy(i));
        					}       					
        				}
        			}
        		}
        	}else if(par1World.getTileEntity(pos.north()) != null && par1World.getTileEntity(pos.north()) instanceof TileEntityInventoryScanner){
	        	if(HelpfulMethods.checkForModule(par1World, pos.north(), ((EntityPlayer) par5Entity), EnumCustomModules.WHITELIST)){ return; }
        		for(int i = 0; i < 10; i++){
        			for(int j = 0; j < ((EntityPlayer) par5Entity).inventory.mainInventory.length; j++){
        				if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.north())).getStackInSlotCopy(i) != null){       				
        					if(((EntityPlayer) par5Entity).inventory.mainInventory[j] != null){
        						checkInventory(((EntityPlayer) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.north())), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.north())).getStackInSlotCopy(i));
        					}       					
        				}
        			}
        		}
        	}else if(par1World.getTileEntity(pos.south()) != null && par1World.getTileEntity(pos.south()) instanceof TileEntityInventoryScanner){
	        	if(HelpfulMethods.checkForModule(par1World, pos.south(), ((EntityPlayer) par5Entity), EnumCustomModules.WHITELIST)){ return; }
        		for(int i = 0; i < 10; i++){
        			for(int j = 0; j < ((EntityPlayer) par5Entity).inventory.mainInventory.length; j++){
        				if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.south())).getStackInSlotCopy(i) != null){       				
        					if(((EntityPlayer) par5Entity).inventory.mainInventory[j] != null){
        						checkInventory(((EntityPlayer) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.south())), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.south())).getStackInSlotCopy(i));
        					}       					
        				}
        			}
        		}
        	}
        //******************************************
        }else if(par5Entity instanceof EntityItem){
        	if(par1World.getTileEntity(pos.west()) != null && par1World.getTileEntity(pos.west()) instanceof TileEntityInventoryScanner){
        		for(int i = 0; i < 10; i++){
        			if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.west())).getStackInSlotCopy(i) != null){       				
        				if(((EntityItem) par5Entity).getEntityItem() != null){
        					checkEntity(((EntityItem) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.west())).getStackInSlotCopy(i));
        				}       					
        			}
        		}
        	}else if(par1World.getTileEntity(pos.east()) != null && par1World.getTileEntity(pos.east()) instanceof TileEntityInventoryScanner){
        		for(int i = 0; i < 10; i++){
        			if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.east())).getStackInSlotCopy(i) != null){       				
        				if(((EntityItem) par5Entity).getEntityItem() != null){
        					checkEntity(((EntityItem) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.east())).getStackInSlotCopy(i));
        				}       					
        			}
        		}
        	}else if(par1World.getTileEntity(pos.north()) != null && par1World.getTileEntity(pos.north()) instanceof TileEntityInventoryScanner){
        		for(int i = 0; i < 10; i++){
        			if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.north())).getStackInSlotCopy(i) != null){       				
        				if(((EntityItem) par5Entity).getEntityItem() != null){
        					checkEntity(((EntityItem) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.north())).getStackInSlotCopy(i));
        				}       					
        			}
        		}
        	}else if(par1World.getTileEntity(pos.south()) != null && par1World.getTileEntity(pos.south()) instanceof TileEntityInventoryScanner){
        		for(int i = 0; i < 10; i++){
        			if(((TileEntityInventoryScanner)par1World.getTileEntity(pos.south())).getStackInSlotCopy(i) != null){       				
        				if(((EntityItem) par5Entity).getEntityItem() != null){
        					checkEntity(((EntityItem) par5Entity), ((TileEntityInventoryScanner)par1World.getTileEntity(pos.south())).getStackInSlotCopy(i));
        				}       					
        			}
        		}
        	}
        }
    }  
    
    public static void checkInventory(EntityPlayer par1EntityPlayer, TileEntityInventoryScanner par2TileEntity, ItemStack par3){
//    	Block block = null;
//		Item item = null;
//		boolean flag = false;
//		
//		if(hasMultipleItemStacks(par3)){
//			if (Item.itemRegistry.containsKey(par3))
//	        {
//				
//	            item = (Item)Item.itemRegistry.getObject(par3);
//	            flag = true;
//	        }
//		}
//		
//		if (Block.blockRegistry.containsKey(par3) && !flag)
//        {
//			
//            block = (Block)Block.blockRegistry.getObject(par3);
//        }
//		
//		if (Item.itemRegistry.containsKey(par3) && !flag)
//        {
//			
//            item = (Item)Item.itemRegistry.getObject(par3);
//        }
		System.out.println("Running");
		if(par2TileEntity.getType().matches("redstone")){
			for(int i = 1; i <= par1EntityPlayer.inventory.mainInventory.length; i++){
				if(par1EntityPlayer.inventory.mainInventory[i - 1] != null){
					//if(par1EntityPlayer.inventory.mainInventory[i - 1].getItem() == par3.getItem() || par1EntityPlayer.inventory.mainInventory[i - 1].getItem() == Item.getItemFromBlock(par3.getItem())){
					System.out.println("Running te update");
					if(par1EntityPlayer.inventory.mainInventory[i - 1].getItem() == par3.getItem()){
						if(!par2TileEntity.shouldProvidePower()){
							par2TileEntity.setShouldProvidePower(true);
						}
						
						mod_SecurityCraft.log("Running te update");
						par2TileEntity.setCooldown(60);
						checkAndUpdateTEAppropriately(par2TileEntity.getWorld(), par2TileEntity.getPos(), par2TileEntity);
						HelpfulMethods.updateAndNotify(par2TileEntity.getWorld(), par2TileEntity.getPos(), par2TileEntity.getWorld().getBlockState(par2TileEntity.getPos()).getBlock(), 1, true);
						mod_SecurityCraft.log("Emitting redstone on the " + FMLCommonHandler.instance().getEffectiveSide() + " side. (te coords: " + Utils.getFormattedCoordinates(par2TileEntity.getPos()));
					}
				}
			}
		}else if(par2TileEntity.getType().matches("check")){
			for(int i = 1; i <= par1EntityPlayer.inventory.mainInventory.length; i++){
				if(par1EntityPlayer.inventory.mainInventory[i - 1] != null){
					if(((CustomizableSCTE) par2TileEntity).hasModule(EnumCustomModules.SMART) && ItemStack.areItemStacksEqual(par1EntityPlayer.inventory.mainInventory[i - 1], par3) && ItemStack.areItemStackTagsEqual(par1EntityPlayer.inventory.mainInventory[i - 1], par3)){
						par1EntityPlayer.inventory.mainInventory[i - 1] = null;
						continue;
					}
					
					if(!((CustomizableSCTE) par2TileEntity).hasModule(EnumCustomModules.SMART) && par1EntityPlayer.inventory.mainInventory[i - 1].getItem() == par3.getItem()){
						par1EntityPlayer.inventory.mainInventory[i - 1] = null;
					}
				}
			}
		}
    }
    
    public static void checkEntity(EntityItem par1EntityItem, ItemStack par2){
		if(par1EntityItem.getEntityItem().getItem() == par2.getItem()){
			par1EntityItem.setDead();
		}
		
    }
    
    private static void checkAndUpdateTEAppropriately(World par1World, BlockPos pos, TileEntityInventoryScanner par5TileEntityIS) {
    	mod_SecurityCraft.log("Updating te");
		if((EnumFacing) par1World.getBlockState(pos).getValue(FACING) == EnumFacing.WEST && Utils.getBlock(par1World, pos.west(2)) == mod_SecurityCraft.inventoryScanner && Utils.getBlock(par1World, pos.west()) == Blocks.air && (EnumFacing) par1World.getBlockState(pos.west(2)).getValue(FACING) == EnumFacing.EAST){
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.west(2))).setShouldProvidePower(true);
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.west(2))).setCooldown(60);
			HelpfulMethods.updateAndNotify(par1World, pos.west(2), Utils.getBlock(par1World, pos), 1, true);
		}
		else if((EnumFacing) par1World.getBlockState(pos).getValue(FACING) == EnumFacing.EAST && Utils.getBlock(par1World, pos.east(2)) == mod_SecurityCraft.inventoryScanner && Utils.getBlock(par1World, pos.east()) == Blocks.air && (EnumFacing) par1World.getBlockState(pos.east(2)).getValue(FACING) == EnumFacing.WEST){
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.east(2))).setShouldProvidePower(true);
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.east(2))).setCooldown(60);
			HelpfulMethods.updateAndNotify(par1World, pos.east(2), Utils.getBlock(par1World, pos), 1, true);

		}
		else if((EnumFacing) par1World.getBlockState(pos).getValue(FACING) == EnumFacing.NORTH && Utils.getBlock(par1World, pos.north(2)) == mod_SecurityCraft.inventoryScanner && Utils.getBlock(par1World, pos.north()) == Blocks.air && (EnumFacing) par1World.getBlockState(pos.north(2)).getValue(FACING) == EnumFacing.SOUTH){
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.north(2))).setShouldProvidePower(true);
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.north(2))).setCooldown(60);
			HelpfulMethods.updateAndNotify(par1World, pos.north(2), Utils.getBlock(par1World, pos), 1, true);

		}
		else if((EnumFacing) par1World.getBlockState(pos).getValue(FACING) == EnumFacing.SOUTH && Utils.getBlock(par1World, pos.south(2)) == mod_SecurityCraft.inventoryScanner && Utils.getBlock(par1World, pos.south()) == Blocks.air && (EnumFacing) par1World.getBlockState(pos.south(2)).getValue(FACING) == EnumFacing.NORTH){
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.south(2))).setShouldProvidePower(true);
			((TileEntityInventoryScanner) par1World.getTileEntity(pos.south(2))).setCooldown(60);
			HelpfulMethods.updateAndNotify(par1World, pos.south(2), Utils.getBlock(par1World, pos), 1, true);

		}
	}
    
    private boolean hasMultipleItemStacks(String string) {
    	boolean flag1 = false, flag2 = false;
    	
    	if (Block.blockRegistry.containsKey(string))
        {
            flag1 = true;
        }
		
		if (Item.itemRegistry.containsKey(string))
        {
            flag2 = true;
        }
		
		return flag1 && flag2 ? true : false;
	}

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, BlockPos pos)
    {
        if (par1IBlockAccess.getBlockState(pos).getValue(FACING) == EnumFacing.EAST || par1IBlockAccess.getBlockState(pos).getValue(FACING) == EnumFacing.WEST)
        {
    		this.setBlockBounds(0.000F, 0.000F, 0.400F, 1.000F, 1.000F, 0.600F); //ew
        }
        else if (par1IBlockAccess.getBlockState(pos).getValue(FACING) == EnumFacing.NORTH || par1IBlockAccess.getBlockState(pos).getValue(FACING) == EnumFacing.SOUTH)
        {
    		this.setBlockBounds(0.400F, 0.000F, 0.000F, 0.600F, 1.000F, 1.000F); //ns
        }
    }
    
    public IBlockState getStateFromMeta(int meta)
    {    
        return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);     
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public Item getItem(World par1World, BlockPos pos)
    {
        return null;
    }

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityInventoryScannerBlock();
	}
   
}
