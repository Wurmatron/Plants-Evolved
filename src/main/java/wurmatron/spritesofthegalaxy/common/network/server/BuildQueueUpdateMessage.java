package wurmatron.spritesofthegalaxy.common.network.server;

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
import java.util.ArrayList;
import java.util.List;

public class BuildQueueUpdateMessage extends CustomMessage.CustomClientMessage <BuildQueueUpdateMessage> {

	private NBTTagCompound data = new NBTTagCompound ();

	public BuildQueueUpdateMessage () {
	}

	public BuildQueueUpdateMessage (BlockPos pos,List <Object[]> buildQueue) {
		data.setIntArray (NBT.POSITION,new int[] {pos.getX (),pos.getY (),pos.getZ ()});
		for (int index = 0; index < buildQueue.size (); index++) {
			NBTTagCompound temp = new NBTTagCompound ();
			if (buildQueue.get (index)[0] instanceof IStructure) {
				temp.setString (NBT.STRUCTURE,((IStructure) buildQueue.get (index)[0]).getName ());
				temp.setInteger (NBT.LEVEL,(int) buildQueue.get (index)[1]);
				temp.setInteger (NBT.TIME,(int) buildQueue.get (index)[2]);
				data.setTag (Integer.toString (index),temp);
			} else if (buildQueue.get (index)[0] instanceof StorageType) {
				temp.setString (NBT.STORAGE,((StorageType) buildQueue.get (index)[0]).name ());
				temp.setInteger (NBT.LEVEL,(int) buildQueue.get (index)[1]);
				temp.setInteger (NBT.TIME,(int) buildQueue.get (index)[2]);
				data.setTag (Integer.toString (index),temp);
			}
		}
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
		BlockPos tilePos = new BlockPos (data.getIntArray (NBT.POSITION)[0],data.getIntArray (NBT.POSITION)[1],data.getIntArray (NBT.POSITION)[2]);
		TileHabitatCore tile = (TileHabitatCore) player.world.getTileEntity (tilePos);
		if (tile != null) {
			List <Object[]> buildQueue = new ArrayList <> ();
			for (int index = 0; index < data.getSize () - 1; index++) {
				NBTTagCompound temp = data.getCompoundTag (Integer.toString (index));
				Object[] tempBuild = null;
				if (temp.hasKey (NBT.STRUCTURE))
					tempBuild = new Object[] {SpritesOfTheGalaxyAPI.getStructureFromName (temp.getString (NBT.STRUCTURE)),temp.getInteger (NBT.LEVEL),temp.getInteger (NBT.TIME)};
				else if (temp.hasKey (NBT.STORAGE))
					tempBuild = new Object[] {StorageType.valueOf (temp.getString (NBT.STORAGE)),temp.getInteger (NBT.LEVEL),temp.getInteger (NBT.TIME)};
				buildQueue.add (tempBuild);
			}
			tile.updateBuildQueue (buildQueue);
		}
	}
}
