package wurmatron.spritesofthegalaxy.common.network.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

public class CancelQueueRequest extends CustomMessage.CustomtServerMessage <CancelQueueRequest> {

	private NBTTagCompound data = new NBTTagCompound ();

	public CancelQueueRequest () {
	}

	public CancelQueueRequest (BlockPos core,Object structure) {
		data.setIntArray (NBT.POSITION,new int[] {core.getX (),core.getY (),core.getZ ()});
		if (structure instanceof IStructure)
			data.setString (NBT.STRUCTURE,((IStructure) structure).getName ());
		else if (structure instanceof StorageType)
			data.setString (NBT.STORAGE,((StorageType) structure).name ());
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		data = buff.readCompoundTag ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeCompoundTag (data);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		TileHabitatCore tile = (TileHabitatCore) player.world.getTileEntity (new BlockPos (data.getIntArray (NBT.POSITION)[0],data.getIntArray (NBT.POSITION)[1],data.getIntArray (NBT.POSITION)[2]));
		if (tile != null)
			if (data.hasKey (NBT.STRUCTURES))
				tile.removeFromBuildQueue (SpritesOfTheGalaxyAPI.getStructureFromName (data.getString (NBT.STRUCTURE)));
			else if (data.hasKey (NBT.STORAGE))
				tile.removeFromBuildQueue (StorageType.valueOf (data.getString (NBT.STORAGE)));
	}
}
