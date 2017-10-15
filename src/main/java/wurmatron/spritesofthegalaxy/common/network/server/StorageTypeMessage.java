package wurmatron.spritesofthegalaxy.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

import java.io.IOException;

public class StorageTypeMessage extends CustomMessage.CustomtServerMessage <StorageTypeMessage> {

	private NBTTagCompound data;

	public StorageTypeMessage () {
	}

	public StorageTypeMessage (StorageType type,int level,TileHabitatCore2 core,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.STORAGE,type.name ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {core.getPos ().getX (),core.getPos ().getY (),core.getPos ().getZ ()});
		data.setBoolean (NBT.TYPE,remove);
	}

	public StorageTypeMessage (StorageType type,int level,BlockPos coreLoc,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.STORAGE,type.name ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {coreLoc.getX (),coreLoc.getY (),coreLoc.getZ ()});
		data.setBoolean (NBT.TYPE,remove);
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
		boolean remove = data.getBoolean (NBT.TYPE);
		BlockPos coreLocation = new BlockPos (coreLoc[0],coreLoc[1],coreLoc[2]);
		if (player.world.getTileEntity (coreLocation) != null && player.world.getTileEntity (coreLocation) instanceof TileHabitatCore2) {
			TileHabitatCore2 core = (TileHabitatCore2) player.world.getTileEntity (coreLocation);
			if (core != null) {
				if (remove) {
					core.addColonyValue (NBT.MINERALS,type.getCost () * tier);
					core.setStorage (type,0);
				} else {
					core.consumeColonyValue (NBT.MINERALS,type.getCost () * tier);
					core.setStorage (type,tier);
				}
			}
		}
	}
}
