package net.geforcemods.securitycraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.blocks.BlockReinforcedDoor;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.main.Utils.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemUniversalOwnerChanger extends Item
{
	public ItemUniversalOwnerChanger(){}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	/**
	 * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
	 * hands.
	 */
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering()
	{
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float f1, float f2, float f3)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		String newOwner = stack.getDisplayName();

		if(!world.isRemote)
		{
			if(!stack.hasDisplayName())
			{
				PlayerUtils.sendMessageToPlayer(player, StatCollector.translateToLocal("item.universalOwnerChanger.name"), StatCollector.translateToLocal("messages.universalOwnerChanger.noName"), EnumChatFormatting.RED);
				return false;
			}

			if(!(te instanceof IOwnable))
			{
				PlayerUtils.sendMessageToPlayer(player, StatCollector.translateToLocal("item.universalOwnerChanger.name"), StatCollector.translateToLocal("messages.universalOwnerChanger.cantChange"), EnumChatFormatting.RED);
				return false;
			}

			if(!BlockUtils.isOwnerOfBlock((IOwnable)te, player))
			{
				PlayerUtils.sendMessageToPlayer(player, StatCollector.translateToLocal("item.universalOwnerChanger.name"), StatCollector.translateToLocal("messages.universalOwnerChanger.notOwned"), EnumChatFormatting.RED);
				return false;
			}

			if(world.getBlock(x, y, z) instanceof BlockReinforcedDoor)
			{
				if(world.getBlock(x, y + 1, z) instanceof BlockReinforcedDoor)
					((IOwnable)world.getTileEntity(x, y + 1, z)).setOwner(PlayerUtils.isPlayerOnline(newOwner) ? PlayerUtils.getPlayerFromName(newOwner).getUniqueID().toString() : "ownerUUID", newOwner);
				else
					((IOwnable)world.getTileEntity(x, y - 1, z)).setOwner(PlayerUtils.isPlayerOnline(newOwner) ? PlayerUtils.getPlayerFromName(newOwner).getUniqueID().toString() : "ownerUUID", newOwner);
			}

			if(te instanceof IOwnable)
				((IOwnable)te).setOwner(PlayerUtils.isPlayerOnline(newOwner) ? PlayerUtils.getPlayerFromName(newOwner).getUniqueID().toString() : "ownerUUID", newOwner);

			MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(te.getDescriptionPacket());
			PlayerUtils.sendMessageToPlayer(player, StatCollector.translateToLocal("item.universalOwnerChanger.name"), StatCollector.translateToLocal("messages.universalOwnerChanger.changed").replace("#", newOwner), EnumChatFormatting.GREEN);
			return true;
		}

		return false;
	}
}