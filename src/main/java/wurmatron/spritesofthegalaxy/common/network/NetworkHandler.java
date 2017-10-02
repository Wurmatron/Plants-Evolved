package wurmatron.spritesofthegalaxy.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.common.network.server.BuildStructureMessage;
import wurmatron.spritesofthegalaxy.common.network.server.DestroyStructureMessage;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.network.server.ResearchUpdateMessage;
import wurmatron.spritesofthegalaxy.common.reference.Global;

public class NetworkHandler {

	private static byte packetID = 0;

	private static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel (Global.MODID);

	public static void registerPackets () {
		// Server
		registerMessage (OpenGuiMessage.class);
		registerMessage (ResearchUpdateMessage.class);
		registerMessage (BuildStructureMessage.class);
		registerMessage (DestroyStructureMessage.class);
	}

	private static final <T extends CustomMessage <T> & IMessageHandler <T, IMessage>> void registerMessage (Class <T> clazz) {
		if (CustomMessage.CustomClientMessage.class.isAssignableFrom (clazz))
			NetworkHandler.network.registerMessage (clazz,clazz,packetID++,Side.CLIENT);
		else if (CustomMessage.CustomtServerMessage.class.isAssignableFrom (clazz))
			NetworkHandler.network.registerMessage (clazz,clazz,packetID++,Side.SERVER);
		else {
			NetworkHandler.network.registerMessage (clazz,clazz,packetID,Side.CLIENT);
			NetworkHandler.network.registerMessage (clazz,clazz,packetID++,Side.SERVER);
		}
	}

	public static void sendTo (IMessage message,EntityPlayerMP player) {
		NetworkHandler.network.sendTo (message,player);
	}

	public static void sendToAll (IMessage message) {
		NetworkHandler.network.sendToAll (message);
	}

	public static void sendToAllAround (IMessage message,NetworkRegistry.TargetPoint point) {
		NetworkHandler.network.sendToAllAround (message,point);
	}

	public static void sendToAllAround (IMessage message,int dimension,double x,double y,double z,double range) {
		NetworkHandler.sendToAllAround (message,new NetworkRegistry.TargetPoint (dimension,x,y,z,range));
	}

	public static void sendToAllAround (IMessage message,EntityPlayer player,double range) {
		NetworkHandler.sendToAllAround (message,player.world.provider.getDimension (),player.posX,player.posY,player.posZ,range);
	}

	public static void sendToDimension (IMessage message,int dimensionId) {
		NetworkHandler.network.sendToDimension (message,dimensionId);
	}

	public static void sendToServer (IMessage message) {
		NetworkHandler.network.sendToServer (message);
	}
}
