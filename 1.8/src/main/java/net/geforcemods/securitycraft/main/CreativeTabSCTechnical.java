package net.geforcemods.securitycraft.main;

import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabSCTechnical extends CreativeTabs{
	
	public CreativeTabSCTechnical(){
		super(getNextID(), "tabSecurityCraft");
	}

	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem(){
		return BlockUtils.getItemFromBlock(mod_SecurityCraft.usernameLogger);
	}
	
	public String getTranslatedTabLabel(){
		return "SecurityCraft: Technical";
		
	}
}
