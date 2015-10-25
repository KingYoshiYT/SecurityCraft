package net.geforcemods.securitycraft.renderers;

import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class ItemKeypadChestRenderer extends TileEntityItemStackRenderer {

	public void renderByItem(ItemStack item) {
		Block block = Block.getBlockFromItem(item.getItem());
		 
		if (block == mod_SecurityCraft.keypadChest)
        {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityKeypadChest(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else
        {
            super.renderByItem(item);
        }
		
	}
	
}
