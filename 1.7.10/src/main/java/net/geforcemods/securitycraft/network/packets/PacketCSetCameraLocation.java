package net.geforcemods.securitycraft.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.main.mod_SecurityCraft;
import net.geforcemods.securitycraft.tileentity.TileEntityFrame;
import net.minecraft.client.Minecraft;

public class PacketCSetCameraLocation implements IMessage{
	
	private int x, y, z, camX, camY, camZ;
	
	public PacketCSetCameraLocation(){
		
	}
	
	public PacketCSetCameraLocation(int x, int y, int z, int camX, int camY, int camZ){
		this.x = x;
		this.y = y;
		this.z = z;
		this.camX = camX;
		this.camY = camY;
		this.camZ = camZ;
	}
	
	public void toBytes(ByteBuf par1ByteBuf) {
		par1ByteBuf.writeInt(x);
		par1ByteBuf.writeInt(y);
		par1ByteBuf.writeInt(z);
		par1ByteBuf.writeInt(camX);
		par1ByteBuf.writeInt(camY);
		par1ByteBuf.writeInt(camZ);
	}

	public void fromBytes(ByteBuf par1ByteBuf) {
		this.x = par1ByteBuf.readInt();
		this.y = par1ByteBuf.readInt();
		this.z = par1ByteBuf.readInt();
		this.camX = par1ByteBuf.readInt();
		this.camY = par1ByteBuf.readInt();
		this.camZ = par1ByteBuf.readInt();
	}
	
public static class Handler extends PacketHelper implements IMessageHandler<PacketCSetCameraLocation, IMessage> {

	@SideOnly(Side.CLIENT)
	public IMessage onMessage(PacketCSetCameraLocation packet, MessageContext context) {
		int x = packet.x;
		int y = packet.y;
		int z = packet.z;
		int camX = packet.camX;
		int camY = packet.camY;
		int camZ = packet.camZ;
	
		if(Minecraft.getMinecraft().theWorld.getBlock(x, y, z) == mod_SecurityCraft.frame){
			((TileEntityFrame) Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z)).setCameraLocation(camX, camY, camZ);
			((TileEntityFrame) Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z)).enableView();
		}
		
		return null;
	}
}
	
}
