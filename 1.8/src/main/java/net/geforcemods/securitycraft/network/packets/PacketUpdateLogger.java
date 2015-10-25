package net.geforcemods.securitycraft.network.packets;

import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.main.Utils.BlockUtils;
import net.geforcemods.securitycraft.tileentity.TileEntityLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateLogger implements IMessage{
	
	private int x, y, z, i;
	private String username;
	
	public PacketUpdateLogger(){
		
	}

	public PacketUpdateLogger(int x, int y, int z, int i, String username){
		this.x = x;
		this.y = y;
		this.z = z;
		this.i = i;
		this.username = username;
	}

	public void toBytes(ByteBuf par2ByteBuf) {
		par2ByteBuf.writeInt(x);
		par2ByteBuf.writeInt(y);
		par2ByteBuf.writeInt(z);
		par2ByteBuf.writeInt(i);
		ByteBufUtils.writeUTF8String(par2ByteBuf, username);
	}

	public void fromBytes(ByteBuf par2ByteBuf) {
		this.x = par2ByteBuf.readInt();
		this.y = par2ByteBuf.readInt();
		this.z = par2ByteBuf.readInt();
		this.i = par2ByteBuf.readInt();
		this.username = ByteBufUtils.readUTF8String(par2ByteBuf);
	}

public static class Handler extends PacketHelper implements IMessageHandler<PacketUpdateLogger, IMessage> { 
	
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(PacketUpdateLogger packet, MessageContext context) {
		BlockPos pos = BlockUtils.toPos(packet.x, packet.y, packet.z);
		int i = packet.i;
		String username = packet.username;
		EntityPlayer par1EntityPlayer = Minecraft.getMinecraft().thePlayer;

		TileEntityLogger te = (TileEntityLogger) getClientWorld(par1EntityPlayer).getTileEntity(pos); 
		
		if(te != null){
			te.players[i] = username;
		}
		
		return null;
	}
}

}
