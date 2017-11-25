package wurmatron.spritesofthegalaxy.common.network.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.BuildQueueUpdateMessage;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

import java.io.IOException;

public class ClientBuildQueueRequest extends CustomMessage.CustomtServerMessage <ClientBuildQueueRequest> {

	private BlockPos core;

	public ClientBuildQueueRequest () {
	}

	public ClientBuildQueueRequest (BlockPos core) {
		this.core = core;
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		core = buff.readBlockPos ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeBlockPos (core);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		TileHabitatCore2 tile = (TileHabitatCore2) player.world.getTileEntity (core);
		NetworkHandler.sendTo (new BuildQueueUpdateMessage (core,tile.getBuildQueue ()),(EntityPlayerMP) player);
	}
}
