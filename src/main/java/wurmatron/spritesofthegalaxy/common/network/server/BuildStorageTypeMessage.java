package wurmatron.spritesofthegalaxy.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.io.IOException;
import java.util.HashMap;

public class BuildStorageTypeMessage extends CustomMessage.CustomtServerMessage <BuildStorageTypeMessage> {

	private NBTTagCompound data;

	public BuildStorageTypeMessage () {
	}

	public BuildStorageTypeMessage (StorageType type,int level,BlockPos coreLoc) {
		data = new NBTTagCompound ();
		data.setString (NBT.STORAGE,type.name ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {coreLoc.getX (),coreLoc.getY (),coreLoc.getZ ()});
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
		int[] coreLoc = data.getIntArray (NBT.POSITION);
		StorageType type = Enum.valueOf (StorageType.class,data.getString (NBT.STORAGE));
		int tier = data.getInteger (NBT.LEVEL);
		BlockPos coreLocation = new BlockPos (coreLoc[0],coreLoc[1],coreLoc[2]);
		if (player.world.getTileEntity (coreLocation) != null && player.world.getTileEntity (coreLocation) instanceof TileHabitatCore) {
			TileHabitatCore core = (TileHabitatCore) player.world.getTileEntity (coreLocation);
			if (core != null) {
				core.eatMinerals (type.getMineral () * tier);
				core.removeStorageType (type,getStorageLevel (core,type));
				core.addStorageType (type,tier);
			}
		}
	}

	private int getStorageLevel (TileHabitatCore tile,StorageType type) {
		if (type != null && tile != null) {
			HashMap <StorageType, Integer> currentStorage = tile.getStorageData ();
			if (currentStorage != null && currentStorage.size () > 0 && currentStorage.containsKey (type))
				return currentStorage.get (type);
		}
		return 0;
	}
}
