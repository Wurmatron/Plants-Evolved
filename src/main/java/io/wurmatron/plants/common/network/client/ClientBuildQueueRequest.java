package io.wurmatron.plants.common.network.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import io.wurmatron.plants.common.network.CustomMessage;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.server.BuildQueueUpdateMessage;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

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
		TileHabitatCore tile = (TileHabitatCore) player.world.getTileEntity (core);
		NetworkHandler.sendTo (new BuildQueueUpdateMessage (core,tile.getBuildQueue ()),(EntityPlayerMP) player);
	}
}
