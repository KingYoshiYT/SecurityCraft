package net.geforcemods.securitycraft.items;

import java.util.List;

import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModule extends Item{
	
	private final EnumCustomModules module;
	private final boolean nbtCanBeModified;
	
	public ItemModule(EnumCustomModules module, boolean nbtCanBeModified){
		this.module = module;
		this.nbtCanBeModified = nbtCanBeModified;
		
		this.setMaxStackSize(1);
		this.setCreativeTab(mod_SecurityCraft.tabSCTechnical);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(nbtCanBeModified){
			par3List.add("Module is modifiable!");
			par3List.add("Use /module to add players.");
		}else{
			par3List.add("Module is not modifiable!");
		}
				
		if(module == EnumCustomModules.WHITELIST || module == EnumCustomModules.BLACKLIST){
			par3List.add(" ");
			par3List.add("Players:");
			
			if(par1ItemStack.getTagCompound() != null){
				for(int i = 1; i <= 10; i++){
					if(!par1ItemStack.getTagCompound().getString("Player" + i).isEmpty()){
						par3List.add(par1ItemStack.getTagCompound().getString("Player" + i));
					}
				}
			}
		}
	}

	public EnumCustomModules getModule() {
		return module;
	}
	
	public boolean canBeModified(){
		return this.nbtCanBeModified;
	}

}
