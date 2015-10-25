package net.geforcemods.securitycraft.network.packets;

import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.api.IPasswordProtected;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.tileentity.TileEntityKeypadChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSSetPassword implements IMessage{
	
	private String password;
	private int x, y, z;
	
	public PacketSSetPassword(){
		
	}
	
	public PacketSSetPassword(int x, int y, int z, String code){
		this.x = x;
		this.y = y;
		this.z = z;
		this.password = code;
	}

	public void toBytes(ByteBuf par1ByteBuf) {
		par1ByteBuf.writeInt(x);
		par1ByteBuf.writeInt(y);
		par1ByteBuf.writeInt(z);
		ByteBufUtils.writeUTF8String(par1ByteBuf, password);
	}

	public void fromBytes(ByteBuf par1ByteBuf) {
		this.x = par1ByteBuf.readInt();
		this.y = par1ByteBuf.readInt();
		this.z = par1ByteBuf.readInt();
		this.password = ByteBufUtils.readUTF8String(par1ByteBuf);
	}
	
public static class Handler extends PacketHelper implements IMessageHandler<PacketSSetPassword, IMessage> {

	public IMessage onMessage(PacketSSetPassword packet, MessageContext ctx) {
		BlockPos pos = BlockUtils.toPos(packet.x, packet.y, packet.z);
		String password = packet.password;
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		if(getWorld(player).getTileEntity(pos) != null && getWorld(player).getTileEntity(pos) instanceof IPasswordProtected){
			((IPasswordProtected) getWorld(player).getTileEntity(pos)).setPassword(password);
			this.checkForAdjecentChest(pos, password, player);
		}
		
		return null;
	}

	private void checkForAdjecentChest(BlockPos pos, String codeToSet, EntityPlayer player) {
		if(getWorld(player).getTileEntity(pos) != null && getWorld(player).getTileEntity(pos) instanceof TileEntityKeypadChest){
			if(getWorld(player).getTileEntity(pos.east()) != null && getWorld(player).getTileEntity(pos.east()) instanceof TileEntityKeypadChest){
				((IPasswordProtected) getWorld(player).getTileEntity(pos.east())).setPassword(codeToSet);
			}else if(getWorld(player).getTileEntity(pos.west()) != null && getWorld(player).getTileEntity(pos.west()) instanceof TileEntityKeypadChest){
				((IPasswordProtected) getWorld(player).getTileEntity(pos.west())).setPassword(codeToSet);
			}else if(getWorld(player).getTileEntity(pos.south()) != null && getWorld(player).getTileEntity(pos.south()) instanceof TileEntityKeypadChest){
				((IPasswordProtected) getWorld(player).getTileEntity(pos.south())).setPassword(codeToSet);
			}else if(getWorld(player).getTileEntity(pos.north()) != null && getWorld(player).getTileEntity(pos.north()) instanceof TileEntityKeypadChest){
				((IPasswordProtected) getWorld(player).getTileEntity(pos.north())).setPassword(codeToSet);
			}
		}
	}
}

}
