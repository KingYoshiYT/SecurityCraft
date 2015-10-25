package net.geforcemods.securitycraft.main;

import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabSCDecoration extends CreativeTabs{
		
	public CreativeTabSCDecoration(){
		super(getNextID(), "tabSecurityCraft");
	}
	
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem(){
		return BlockUtils.getItemFromBlock(mod_SecurityCraft.reinforcedStairsOak);
	}
	
	public String getTranslatedTabLabel(){
		return "SecurityCraft: Decoration";	
	}

}
